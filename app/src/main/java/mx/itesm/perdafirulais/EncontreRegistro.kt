package mx.itesm.perdafirulais

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_encontre_registro.*
import java.io.ByteArrayOutputStream

class EncontreRegistro : AppCompatActivity() {

    lateinit var raza: String
    lateinit var imagen: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encontre_registro)
        raza = intent.getStringExtra("raza")
        imagen = intent.getParcelableExtra("bitmap")
        imgPerroRegistro.setImageBitmap(imagen)

    }
}
