package mx.itesm.perdafirulais.PerdiEncontre.perdi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_encontre_seleccion.btnGaleria
import mx.itesm.perdafirulais.PerdiEncontre.BrowsePicture
import mx.itesm.perdafirulais.R

class PerdiSeleccionBotonesFotos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perdi_seleccion)


        btnGaleria.setOnClickListener {
            val intent = Intent(this, BrowsePicture::class.java)
            intent.putExtra("identificador", "perdi")
            startActivity(intent)
        }
    }
}
