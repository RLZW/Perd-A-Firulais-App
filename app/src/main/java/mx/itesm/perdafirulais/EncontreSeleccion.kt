package mx.itesm.perdafirulais

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_encontre_seleccion.*
import mx.itesm.perdafirulais.Camera.BrowsePicture
import mx.itesm.perdafirulais.Camera.TakePicture

class EncontreSeleccion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encontre_seleccion)


        btnCamara.setOnClickListener {
            val intent = Intent(this, TakePicture::class.java)
            intent.putExtra("identificador", "encontre")
            startActivity(intent)
        }
        btnGaleria.setOnClickListener {
            val intent = Intent(this, BrowsePicture::class.java)
            intent.putExtra("identificador", "encontre")
            startActivity(intent)
        }


    }
}
