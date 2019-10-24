package mx.itesm.perdafirulais.PerdiEncontre

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import kotlinx.android.synthetic.main.activity_take_picture.*
import mx.itesm.perdafirulais.R

class TakePicture : AppCompatActivity() {

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera_view.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        btn_detect.setOnClickListener {
            camera_view.captureImage { cameraKitView, byteArray ->
                camera_view.onStop()
                //alertDialog.show()
                var bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size ?: 0)
                bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    camera_view?.width ?: 0,
                    camera_view?.height ?: 0,
                    false
                )
                runDetector(bitmap)
            }
            //graphic_overlay.clear()
        }
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

    override fun onResume() {
        super.onResume()
        camera_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        camera_view.onPause()
    }

    override fun onStart() {
        super.onStart()
        camera_view.onStart()
    }

    override fun onStop() {
        super.onStop()
        camera_view.onStop()
    }

}
