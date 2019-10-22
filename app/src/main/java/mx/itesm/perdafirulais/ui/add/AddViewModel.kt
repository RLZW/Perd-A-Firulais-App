package mx.itesm.perdafirulais.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aquí puedes añadir al firlais que encontraste a nuestra lista."
    }
    val text: LiveData<String> = _text

}