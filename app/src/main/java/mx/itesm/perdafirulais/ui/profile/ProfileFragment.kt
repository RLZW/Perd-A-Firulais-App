package mx.itesm.perdafirulais.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itesm.perdafirulais.autenticacion.LoginActivity
import mx.itesm.perdafirulais.R
import mx.itesm.perdafirulais.autenticacion.Register

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        profileViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val btnCerrar: Button = root.findViewById(R.id.btnCerrarSesion)
        btnCerrar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.startActivity(intent)
        }

        val tvUsuario: TextView = root.findViewById(R.id.tvUsuario)
        val tvTelefono: TextView = root.findViewById(R.id.tvTeléfono)
        val tvEmail: TextView = root.findViewById(R.id.tvCorreo)

        fetchUsuario(tvUsuario, tvTelefono, tvEmail)
        return root
    }

    private fun fetchUsuario(usuariot: TextView, telefono: TextView, tvEmail: TextView) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/usuarios/${uid}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val usuario = p0.getValue(Register.User::class.java)
                    Log.d("Usuarion", "username ${usuario?.username}")
                    Log.d("Usuarion", "mail ${usuario?.mail}")
                    Log.d("Usuarion", "phone ${usuario?.phone}")
                    usuariot.text = usuario?.username
                    telefono.text = usuario?.phone
                    tvEmail.text = usuario?.mail
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })
    }
}