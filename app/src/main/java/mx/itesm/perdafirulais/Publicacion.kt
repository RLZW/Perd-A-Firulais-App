package mx.itesm.perdafirulais

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_publicacion.*
import mx.itesm.perdafirulais.models.Publicacion
import org.jetbrains.anko.makeCall

class Publicacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)
        val id_publicacion = intent.getStringExtra("PUBLICACION_KEY")
        val identificador = intent.getStringExtra("IDENTIFICADOR")
        if (id_publicacion.isNullOrBlank()) {
            Toast.makeText(
                this, "Lo sentimos, tenemos problemas para mostrarte la información.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()

        } else {
            if (identificador == "ENCONTRAR") {
                fetch_publicacion(id_publicacion, "encontrados")

            } else {
                fetch_publicacion(id_publicacion, "perdidos")
            }

        }

    }

    private fun fetch_publicacion(id_publicacion: String, referencia: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val ref = FirebaseDatabase.getInstance()
            .getReference("/publicaciones/${referencia}/${id_publicacion}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(pub: DataSnapshot) {
                if (pub.exists()) {
                    val publicacion = pub.getValue(Publicacion::class.java)
                    if (publicacion != null) {
                        etTitulo.text = publicacion.titulo
                        etFecha.text = publicacion.fecha
                        etRaza.text = publicacion.raza
                        etComentarios.text = publicacion.comentarios
                        etUbicacion.text = publicacion.ubicacion
                        Picasso.get().load(publicacion.uri).into(imPublicacion)
                        if(uid != publicacion.id_creador){

                        }
                        btnLlamar.setOnClickListener {
                            val intent = Intent(Intent.ACTION_DIAL);
                            intent.data = Uri.parse("tel:${publicacion.telefono}")
                            startActivity(intent)
                        }
                    }

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })
    }




}
