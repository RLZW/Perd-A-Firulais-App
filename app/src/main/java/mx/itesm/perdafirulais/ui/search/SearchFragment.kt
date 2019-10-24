package mx.itesm.perdafirulais.ui.search

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
import com.squareup.picasso.RequestCreator
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.dog_row_rv.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import mx.itesm.perdafirulais.R
import mx.itesm.perdafirulais.models.Publicacion
import mx.itesm.perdafirulais.ui.search.SearchFragment

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val textView: TextView = root.findViewById(R.id.text_search)

        searchViewModel.text.observe(this, Observer {
            textView.text = it
        })
        val rvDog: RecyclerView = root.findViewById(R.id.rvDogs)
        val adapter = GroupAdapter<GroupieViewHolder>()
        rvDog.adapter = adapter

        fetchPublicaciones()
        return root
    }

    private fun fetchPublicaciones() {
        val ref = FirebaseDatabase.getInstance().getReference("/publicaciones/perdidos")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(w0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                w0.children.forEach {
                    Log.d("Wakanda", it.toString())
                    val publicacion = it.getValue(Publicacion::class.java)
                    if (publicacion != null) {
                        adapter.add(SearchFragment.DogItem(publicacion))

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

private fun RequestCreator.into(imPerro: View?) {
    //No es necesario implementar es para eliminar un error con la libreria de circle view y la interaccion con Picasso
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

