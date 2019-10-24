package mx.itesm.perdafirulais.PerdiEncontre.encontre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_encontre_seleccion.*
import mx.itesm.perdafirulais.R
import mx.itesm.perdafirulais.PerdiEncontre.BrowsePicture
import mx.itesm.perdafirulais.PerdiEncontre.TakePicture

class EncontreSeleccionBotonesFotos : AppCompatActivity() {

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
