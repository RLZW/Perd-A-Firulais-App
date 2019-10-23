package mx.itesm.perdafirulais.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import mx.itesm.perdafirulais.R

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

        val rvDog: RecyclerView = root.findViewById(R.id.rvDogs)
        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(DogItem())
        adapter.add(DogItem())
        adapter.add(DogItem())
        rvDog.adapter = adapter


        return root
    }

}

class DogItem : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayout(): Int {
        return R.layout.dog_row_rv

    }

}