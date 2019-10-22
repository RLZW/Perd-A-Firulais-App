package mx.itesm.perdafirulais.Camera

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import mx.itesm.perdafirulais.R
import java.io.IOException

class BrowsePicture : AppCompatActivity() {

    val GALLERY = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_picture)
        choosePhotoFromGallary()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    runDetector(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }


    fun runDetector(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val options = FirebaseVisionCloudImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.8f)
            .build()

        val detector = FirebaseVision.getInstance()
            .getCloudImageLabeler(options)
        detector.processImage(image)
            .addOnSuccessListener {
                processImageLabelingFromCloud(it)
            }
            .addOnFailureListener {
                Log.d("Fallo", "No funciona")
            }

    }

    fun processImageLabelingFromCloud(labels: MutableList<FirebaseVisionImageLabel>) {
        val labelsSb = StringBuilder()
        for (label in labels) {
            labelsSb.append(label.text).appendln()
            val text = label.text
            //val entityId = label.entityId
            //val confidence = label.confidence
            Log.d("Cualidad", text)
        }

        AlertDialog.Builder(this)
            .setTitle("Labels from cloud")
            .setMessage(labelsSb.toString())
            .create()
            .show()
    }


}
