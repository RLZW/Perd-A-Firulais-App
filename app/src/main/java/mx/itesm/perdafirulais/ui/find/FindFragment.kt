package mx.itesm.perdafirulais.ui.find

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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

        val adaptar = ArrayAdapter(
            activity!!, android.R.layout.simple_spinner_dropdown_item, listOf(
                "Todos",
                "Perro callejero",
                "Affenpinscher",
                "Aguilucho",
                "Akita",
                "American Staffordshire Terrier",
                "American Water Spaniel",
                "Basenji",
                "Basset Hound",
                "Beagle",
                "Bearded Collie",
                "Bedlington Terrier",
                "Bernese Mountain",
                "Bichon Frise",
                "Border Collie",
                "Border Terrier",
                "Borzoi",
                "Boston Terrier",
                "Bouvier des Flandres",
                "Boxer",
                "Bretaña",
                "Briard",
                "Brussels Griffon",
                "Bull Terrier miniatura",
                "Bull Terrier",
                "Bull",
                "Bullmastif",
                "Canaan",
                "Cárdigan Corgi Galés",
                "Cavalier King Charles Spaniel",
                "Chesapeake Bay Retriever",
                "Chihuahua",
                "Chow Chow",
                "Clumber Spaniel",
                "Cocker Spaniel Americano",
                "Cocker Spaniel Inglés",
                "Collie",
                "Coonhound",
                "Crestado chino",
                "Dachshund",
                "Dálmata",
                "Dandie Dinmont Terrier",
                "Deerhound escocés",
                "Doberman Pinscher",
                "Elkhound Noruego",
                "English Foxhound",
                "English Setter",
                "English Toy Spaniel",
                "Esquimal americano",
                "Flat-Coated Retriever",
                "French Bull",
                "Galgo",
                "Ganado australiano",
                "German Wirehaired Pointer",
                "Glen of Imaal Terrier",
                "Golden Retriever",
                "Gordon Setter",
                "Gran Danés",
                "Greater Swiss Mountain",
                "Grifón señalador de pelo",
                "Havanese",
                "Husky siberiano",
                "Japanese Chin",
                "Keeshond",
                "Kerry Blue Terrier",
                "Komondor",
                "Kuvasz",
                "Labrador Retriever",
                "Lakeland terrier",
                "Lebrel",
                "Lhasa Apso",
                "Los grandes pirineos",
                "Lowchen",
                "Malamute de Alaska",
                "Malinois belga",
                "Maltés",
                "Manchester Terrier",
                "Mastín Napolitano",
                "Mastín tibetano",
                "Mastín",
                "Nova Scotia Duck Tolling Retriever",
                "Otterhound",
                "Ovejas inglesas viejas",
                "Ovejero",
                "Ovejeros de las tierras bajas polacas",
                "Papillon",
                "Parson Russell Terrier",
                "Pastor australiano",
                "Pastor de Anatolia",
                "Pastor de Shetland",
                "Pekinés",
                "Pembroke Welsh Corgi",
                "Perro de agua irlandés",
                "Perro lobo irlandés",
                "Perro perdiguero rizado",
                "Petit Basset Griffon Vender",
                "Pinscher miniatura",
                "Plott",
                "Pointer",
                "Pomeranian",
                "Poodle",
                "Portuguese Water",
                "Pug",
                "Puli",
                "Raposero americano",
                "Redbone Coonhound",
                "Retriever",
                "Ridgeback de Rodesia",
                "Rottweiler",
                "Sabueso afgano",
                "Sabueso del faraón",
                "Sabueso ibicenco",
                "Sabueso",
                "Saluki",
                "Samoyedo",
                "San Bernardo",
                "Schipperke",
                "Schnauzer estándar",
                "Schnauzer gigante",
                "Schnauzer miniatura",
                "Sealyham Terrier",
                "Setter irlandés",
                "Shar-Pei chino",
                "Shiba Inu",
                "Shih Tzu",
                "Skye terrier",
                "Smooth Fox Terrier",
                "Soft Coated Wheaten Terrier",
                "Spaniel de campo",
                "Spaniel tibetano",
                "Spinone Italiano",
                "Spitz finlandés",
                "Springer Spaniel Galés",
                "Springer Spaniel Inglés",
                "Staffordshire Bull Terrier",
                "Sussex Spaniel",
                "Terranova",
                "Terrier australiano",
                "Terrier de mojón",
                "Terrier de Norfolk",
                "Terrier escocés",
                "Terrier galés",
                "Terrier irlandés",
                "Terrier ruso negro",
                "Terrier sedoso",
                "Tervuren belga",
                "Toy Fox Terrier",
                "Vizsla",
                "Weimaraner",
                "West Highland White Terrier",
                "Wire Fox Terrier",
                "Yorkshire Terrier",
                "Airedale Terrier",
                "Beauceron",
                "Norwich Terrier",
                "Galgo Italiano",
                "German Shorthaired Pointer",
                "Pastor alemán",
                "Pinscher alemán",
                "Terrier Tibetano"

            )
        )
        adaptar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var vie = root.findViewById<Spinner>(R.id.spinnerFind)
        val spinner = vie.findViewById<Spinner>(R.id.spinnerFind)
        spinner!!.adapter = adaptar
        val adapter = GroupAdapter<GroupieViewHolder>()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var perrito_selecionado = spinnerFind.selectedItem.toString()
                fetchPublicaciones(perrito_selecionado)
            }
        }
        return root
    }

    companion object {
        val PUBLICACION_KEY = "PUBLICACION_KEY"
    }

    private fun fetchPublicaciones(perrito_selecionado: String) {
        val ref = FirebaseDatabase.getInstance().getReference("/publicaciones/encontrados")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(w0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()


                w0.children.forEach {
                    Log.d("Wakanda", it.toString())
                    val publicacion = it.getValue(Publicacion::class.java)

                    if (publicacion != null) {
                        if (perrito_selecionado == "Todos") {
                            adapter.add(DogItem(publicacion))
                        } else if (publicacion.raza == perrito_selecionado) {
                            adapter.add(DogItem(publicacion))
                        }
                    }
                }
                adapter.setOnItemClickListener { item, view ->
                    val dogItem = item as DogItem
                    val intent =
                        Intent(view.context, mx.itesm.perdafirulais.Publicacion::class.java)
                    val id = dogItem.publicacion.id.toString()
                    intent.putExtra(PUBLICACION_KEY, id)
                    intent.putExtra("IDENTIFICADOR", "ENCONTRAR")
                    startActivity(intent)
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
