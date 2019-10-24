package mx.itesm.perdafirulais.PerdiEncontre.perdi

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_perdi_registro.*
import mx.itesm.perdafirulais.R
import mx.itesm.perdafirulais.autenticacion.Register
import mx.itesm.perdafirulais.models.Publicacion
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PerdiRegistro : AppCompatActivity() {

    val tag = "_EncontreRegistro"
    var raza: String? = null
    lateinit var imagen: Uri
    var uriString: String? = null
    var uid_usuario: String? = null
    var telefono_usuario: String? = null
    var selectedPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perdi_registro)
        raza = intent.getStringExtra("raza")
        uriString = intent.getStringExtra("uri")
        Log.d(tag, "Raza ${raza.toString()}")
        Log.d(tag, "uriString ${uriString.toString()}")

        if (uriString != null && raza != null) {
            var uri: Uri = Uri.parse(uriString)
            var bitmap = MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                uri
            )//Se obtiene el bitmap del intent
            imgPerroRegistro.setImageBitmap(bitmap)
            imagen = uri
            tvRaza.text = raza
        }


        btnRegistrar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val comentarios = etComentarios.text.toString()
            val ubicacion = etUbicacion.text.toString()

            if (titulo.isNullOrBlank() or comentarios.isNullOrBlank() or ubicacion.isNullOrBlank()) {
                Toast.makeText(
                    this, "No has llenado alguno de los campos.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.d(tag, "Tratando de subir imagen")
                subirImageFileStorage(imagen) //
            }

        }
    }

    private fun subirImageFileStorage(uri: Uri) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        if (uri == null) {
            Toast.makeText(
                this, "Fallo al subir la imagen.",
                Toast.LENGTH_SHORT
            ).show()
        }
        ref.putFile(uri)
            .addOnSuccessListener {
                Log.d(tag, "Imagen subidad satisfactoria: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    fetchUsuario(it)
                }
                ref.downloadUrl.addOnFailureListener {
                    Log.d(tag, "Fallo al subir la imagen.")

                    Toast.makeText(
                        this, "No se pudo registrar el perrito. ${it.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            .addOnFailureListener {
                Log.d(tag, "Fallo al subir la imagen.")
                Toast.makeText(
                    this, "No se pudo registrar el perrito. ${it.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun fetchUsuario(uri: Uri) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/usuarios/${uid}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val respuesta = p0.getValue(Register.User::class.java)
                    Log.d(tag, "username ${respuesta?.username}")
                    Log.d(tag, "mail ${respuesta?.mail}")
                    Log.d(tag, "phone ${respuesta?.phone}")
                    uid_usuario = respuesta?.uid
                    telefono_usuario = respuesta?.phone
                    subirPublicacionPerritoEncontrado(uri, uid_usuario!!, telefono_usuario!!)


                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO()
            }
        })
    }

    private fun subirPublicacionPerritoEncontrado(uri: Uri, idusuario: String, telusuario: String) {
        val publicationid = UUID.randomUUID().toString()

        //Elementos sacados del layout
        val titulo = etTitulo.text.toString()
        val comentarios = etComentarios.text.toString()
        val ubicacion = etUbicacion.text.toString()
        //Fecha
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val fecha_Actual = sdf.format(Date())


        if (titulo.isNullOrBlank() or comentarios.isNullOrBlank() or ubicacion.isNullOrBlank()) {
            Toast.makeText(
                this, "No has llenado alguno de los campos.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val ref = FirebaseDatabase.getInstance()
                .getReference("/publicaciones/perdidos/$publicationid")
            val publicacion = Publicacion(
                publicationid,
                uri.toString(),
                raza!!,
                fecha_Actual,
                idusuario,
                telusuario!!,
                "Encontrado",
                titulo,
                comentarios,
                ubicacion
            )

            ref.setValue(publicacion)
                .addOnSuccessListener {
                    Toast.makeText(
                        this, "Registro Exitoso.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, mx.itesm.perdafirulais.Publicacion::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d(tag, it.toString())
                }
        }

    }


    private fun getImageUri(context: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}
