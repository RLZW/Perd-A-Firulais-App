package mx.itesm.perdafirulais.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "¿Encontraste ó perdiste un Firulais? Aquí puedes reportarlo."
    }
    val text: LiveData<String> = _text

}