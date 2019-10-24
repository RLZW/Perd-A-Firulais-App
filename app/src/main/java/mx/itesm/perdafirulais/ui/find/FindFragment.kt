package mx.itesm.perdafirulais.ui.find

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.dog_row_rv.view.*
import kotlinx.android.synthetic.main.fragment_find.*
import mx.itesm.perdafirulais.R
import mx.itesm.perdafirulais.models.Publicacion

class FindFragment : Fragment() {

    private lateinit var findViewModel: FindViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findViewModel =
            ViewModelProviders.of(this).get(FindViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_find, container, false)
        val textView: TextView = root.findViewById(R.id.text_find)
        findViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val rvDog: RecyclerView = root.findViewById(R.id.rvDogs)
        /*val adapter = GroupAdapter<GroupieViewHolder>()

        rvDog.adapter = adapter*/

        fetchPublicaciones()
        return root
    }

    private fun fetchPublicaciones() {
        val ref = FirebaseDatabase.getInstance().getReference("/publicaciones/encontrados")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(w0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()

                w0.children.forEach {
                    Log.d("Wakanda", it.toString())
                    val publicacion = it.getValue(Publicacion::class.java)

                    if (publicacion != null) {
                        adapter.add(DogItem(publicacion))

                    }
                }
                rvDogs.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })

    }


    class DogItem(val publicacion: Publicacion) : Item<GroupieViewHolder>() {
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.tvTitulo.text = publicacion.titulo
            viewHolder.itemView.tvFecha.text = publicacion.fecha
            viewHolder.itemView.tvRaza.text = publicacion.raza
            Picasso.get().load(publicacion.uri).into(viewHolder.itemView.imPerro)

            //To change body of created functions use File | Settings | File Templates.
        }

        override fun getLayout(): Int {
            return R.layout.dog_row_rv

        }

    }
}
