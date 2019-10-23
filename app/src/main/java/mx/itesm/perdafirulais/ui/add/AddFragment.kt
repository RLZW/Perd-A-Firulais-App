package mx.itesm.perdafirulais.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import mx.itesm.perdafirulais.PerdiSeleccion
import mx.itesm.perdafirulais.EncontreSeleccion
import mx.itesm.perdafirulais.R

class AddFragment : Fragment() {

    private lateinit var addViewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addViewModel =
            ViewModelProviders.of(this).get(AddViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_add, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        addViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val buttonEncontre: Button = root.findViewById(R.id.btnEncontre)
        buttonEncontre.setOnClickListener {
            val intent = Intent(activity, EncontreSeleccion::class.java)
            activity?.startActivity(intent)
        }

        val buttonPerdi: Button = root.findViewById(R.id.btnPerdi)
        buttonPerdi.setOnClickListener {
            val intent = Intent(activity, PerdiSeleccion::class.java)
            activity?.startActivity(intent)
        }
        return root
    }
}