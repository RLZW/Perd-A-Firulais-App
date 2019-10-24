package mx.itesm.perdafirulais.ui.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FindViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Firulais encontrados"
    }
    val text: LiveData<String> = _text
}