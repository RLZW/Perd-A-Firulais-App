package mx.itesm.perdafirulais.PerdiEncontre

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import mx.itesm.perdafirulais.*
import mx.itesm.perdafirulais.PerdiEncontre.encontre.EncontreRegistro
import mx.itesm.perdafirulais.PerdiEncontre.perdi.PerdiRegistro
import java.io.ByteArrayOutputStream
import java.io.IOException

class BrowsePicture : AppCompatActivity() {
    val listRazaPerros = mutableMapOf(
        "Affenpinscher" to "Affenpinscher",
        "Afghan Hound" to "Sabueso afgano",
        "Airedale Terrier" to "Airedale Terrier",
        "Akita" to "Akita",
        "Alaskan Malamute" to "Malamute de Alaska",
        "American Cocker Spaniel" to "Cocker Spaniel Americano",
        "American Eskimo" to "Esquimal americano",
        "American Foxhound" to "Raposero americano",
        "American Staffordshire Terrier" to "American Staffordshire Terrier",
        "American Water Spaniel" to "American Water Spaniel",
        "Anatolian Shepherd" to "Pastor de Anatolia",
        "Australian Cattle" to "Ganado australiano",
        "Australian Shepherd" to "Pastor australiano",
        "Australian Terrier" to "Terrier australiano",
        "Basenji" to "Basenji",
        "Basset Hound" to "Basset Hound",
        "Beagle" to "Beagle",
        "Bearded Collie" to "Bearded Collie",
        "Beauceron" to "Beauceron",
        "Bedlington Terrier" to "Bedlington Terrier",
        "Belgian Malinois" to "Malinois belga",
        "Belgian Sheep" to "Ovejero",
        "Belgian Tervuren" to "Tervuren belga",
        "Bernese Mountain" to "Bernese Mountain",
        "Bichon Frise" to "Bichon Frise",
        "Black and Tan Coonhound" to "Coonhound",
        "Black Russian Terrier" to "Terrier ruso negro",
        "Bloodhound" to "Sabueso",
        "Border Collie" to "Border Collie",
        "Border Terrier" to "Border Terrier",
        "Borzoi" to "Borzoi",
        "Boston Terrier" to "Boston Terrier",
        "Bouvier des Flandres" to "Bouvier des Flandres",
        "Boxer" to "Boxer",
        "Briard" to "Briard",
        "Brittany" to "Bretaña",
        "Brussels Griffon" to "Brussels Griffon",
        "Bull Terrier" to "Bull Terrier",
        "Bull" to "Bull",
        "Bullmastiff" to "Bullmastif",
        "Cairn Terrier" to "Terrier de mojón",
        "Canaan" to "Canaan",
        "Cardigan Welsh Corgi" to "Cárdigan Corgi Galés",
        "Cavalier King Charles Spaniel" to "Cavalier King Charles Spaniel",
        "Chesapeake Bay Retriever" to "Chesapeake Bay Retriever",
        "Chihuahua" to "Chihuahua",
        "Chinese Crested" to "Crestado chino",
        "Chinese Shar-Pei" to "Shar-Pei chino",
        "Chow Chow" to "Chow Chow",
        "Clumber Spaniel" to "Clumber Spaniel",
        "Collie" to "Collie",
        "Curly-Coated Retriever" to "Perro perdiguero rizado",
        "Dachshund" to "Dachshund",
        "Dalmatian" to "Dálmata",
        "Dandie Dinmont Terrier" to "Dandie Dinmont Terrier",
        "Doberman Pinscher" to "Doberman Pinscher",
        "English Cocker Spaniel" to "Cocker Spaniel Inglés",
        "English Foxhound" to "English Foxhound",
        "English Setter" to "English Setter",
        "English Springer Spaniel" to "Springer Spaniel Inglés",
        "English Toy Spaniel" to "English Toy Spaniel",
        "Field Spaniel" to "Spaniel de campo",
        "Finnish Spitz" to "Spitz finlandés",
        "Flat-Coated Retriever" to "Flat-Coated Retriever",
        "French Bull" to "French Bull",
        "German Pinscher" to "Pinscher alemán",
        "German Shepherd" to "Pastor alemán",
        "German Shorthaired Pointer" to "German Shorthaired Pointer",
        "German Wirehaired Pointer" to "German Wirehaired Pointer",
        "Giant Schnauzer" to "Schnauzer gigante",
        "Glen of Imaal Terrier" to "Glen of Imaal Terrier",
        "Golden retriever" to "Golden Retriever",
        "Retriever" to "Retriever",
        "Gordon Setter" to "Gordon Setter",
        "Great Dane" to "Gran Danés",
        "Great Pyrenees" to "Los grandes pirineos",
        "Greater Swiss Mountain" to "Greater Swiss Mountain",
        "Greyhound" to "Galgo",
        "Harrier" to "Aguilucho",
        "Havanese" to "Havanese",
        "Ibizan Hound" to "Sabueso ibicenco",
        "Irish Setter" to "Setter irlandés",
        "Irish Terrier" to "Terrier irlandés",
        "Irish Water Spaniel" to "Perro de agua irlandés",
        "Irish Wolfhound" to "Perro lobo irlandés",
        "Italian Greyhound" to "Galgo Italiano",
        "Japanese Chin" to "Japanese Chin",
        "Keeshond" to "Keeshond",
        "Kerry Blue Terrier" to "Kerry Blue Terrier",
        "Komondor" to "Komondor",
        "Kuvasz" to "Kuvasz",
        "Labrador Retriever" to "Labrador Retriever",
        "Lakeland Terrier" to "Lakeland terrier",
        "Lhasa Apso" to "Lhasa Apso",
        "Lowchen" to "Lowchen",
        "Maltese" to "Maltés",
        "Manchester Terrier" to "Manchester Terrier",
        "Mastiff" to "Mastín",
        "Miniature Bull Terrier" to "Bull Terrier miniatura",
        "Miniature Pinscher" to "Pinscher miniatura",
        "Miniature Schnauzer" to "Schnauzer miniatura",
        "Neapolitan Mastiff" to "Mastín Napolitano",
        "Newfoundland" to "Terranova",
        "Norfolk Terrier" to "Terrier de Norfolk",
        "Norwegian Elkhound" to "Elkhound Noruego",
        "Norwich Terrier" to "Norwich Terrier",
        "Nova Scotia Duck Tolling Retriever" to "Nova Scotia Duck Tolling Retriever",
        "Old English Sheep" to "Ovejas inglesas viejas",
        "Otterhound" to "Otterhound",
        "Papillon" to "Papillon",
        "Parson Russell Terrier" to "Parson Russell Terrier",
        "Pekingese" to "Pekinés",
        "Pembroke Welsh Corgi" to "Pembroke Welsh Corgi",
        "Petit Basset Griffon Vendeen" to "Petit Basset Griffon Vender",
        "Pharaoh Hound" to "Sabueso del faraón",
        "Plott" to "Plott",
        "Pointer" to "Pointer",
        "Polish Lowland Sheep" to "Ovejas de las tierras bajas polacas",
        "Pomeranian" to "Pomeranian",
        "Poodle" to "Poodle",
        "Portuguese Water" to "Portuguese Water",
        "Pug" to "Pug",
        "Puli" to "Puli",
        "Redbone Coonhound" to "Redbone Coonhound",
        "Rhodesian Ridgeback" to "Ridgeback de Rodesia",
        "Rottweiler" to "Rottweiler",
        "Saint Bernard" to "San Bernardo",
        "Saluki" to "Saluki",
        "Samoyed" to "Samoyedo",
        "Schipperke" to "Schipperke",
        "Scottish Deerhound" to "Deerhound escocés",
        "Scottish Terrier" to "Terrier escocés",
        "Sealyham Terrier" to "Sealyham Terrier",
        "Shetland Sheep" to "Pastor de Shetland",
        "Shiba Inu" to "Shiba Inu",
        "Shih Tzu" to "Shih Tzu",
        "Siberian Husky" to "Husky siberiano",
        "Silky Terrier" to "Terrier sedoso",
        "Skye Terrier" to "Skye terrier",
        "Smooth Fox Terrier" to "Smooth Fox Terrier",
        "Soft Coated Wheaten Terrier" to "Soft Coated Wheaten Terrier",
        "Spinone Italiano" to "Spinone Italiano",
        "Staffordshire Bull Terrier" to "Staffordshire Bull Terrier",
        "Standard Schnauzer" to "Schnauzer estándar",
        "Sussex Spaniel" to "Sussex Spaniel",
        "Tibetan Mastiff" to "Mastín tibetano",
        "Tibetan Spaniel" to "Spaniel tibetano",
        "Tibetan Terrier" to "Terrier Tibetano",
        "Toy Fox Terrier" to "Toy Fox Terrier",
        "Vizsla" to "Vizsla",
        "Weimaraner" to "Weimaraner",
        "Welsh Springer Spaniel" to "Springer Spaniel Galés",
        "Welsh Terrier" to "Terrier galés",
        "West Highland White Terrier" to "West Highland White Terrier",
        "Whippet" to "Lebrel",
        "Wire Fox Terrier" to "Wire Fox Terrier",
        "Wirehaired Pointing Griffon" to "Grifón señalador de pelo",
        "Yorkshire Terrier" to "Yorkshire Terrier",
        "Street dog" to "Perro callejero"
    )
    val GALLERY = 1000
    var razaActual: String = "Desconocida"//Guardamos la raza del perro.
    lateinit var bitmap: Bitmap// Guardamos la imagen para mandarla a la otra actividad
    var contienePerro = false //Con esto checamos is hay un perro.
    var encontroRaza = false // Con esto comprobamos si encontró raza.
    lateinit var identificador: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_picture)
        identificador = intent.getStringExtra("identificador")
        choosePhotoFromGallary()
        Log.d("_Browse", "El identificador es $identificador")


    }

    private fun manejarValidez(identificador: String) {

        if (contienePerro and encontroRaza) {

            Toast.makeText(
                this, "La raza detectada es $razaActual.",
                Toast.LENGTH_SHORT
            ).show()

            val intent: Intent

            if (identificador == "encontre") {
                intent = Intent(this, EncontreRegistro::class.java)
            } else {
                intent = Intent(this, PerdiRegistro::class.java)
            }
            val uri = getImageUri(this, bitmap)
            intent.putExtra("raza", razaActual)
            intent.putExtra("uri", uri.toString())
            startActivity(intent)
            finish()
        } else if (contienePerro) {
            Toast.makeText(
                this, "La raza es $razaActual.",
                Toast.LENGTH_SHORT
            ).show()
            val intent: Intent
            if (identificador == "encontre") {
                intent = Intent(this, EncontreRegistro::class.java)
            } else {
                intent = Intent(this, PerdiRegistro::class.java)
            }
            val uri = getImageUri(this, bitmap)
            intent.putExtra("raza", razaActual)
            intent.putExtra("uri", uri.toString())

            startActivity(intent)
            finish()


        } else {
            Toast.makeText(
                this, "Lo sentimos, no encontramos nigún perro en tu imagen.",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()

        }

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
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
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
            .setConfidenceThreshold(0.75f)
            .build()

        val detector = FirebaseVision.getInstance()
            .getCloudImageLabeler(options)
        detector.processImage(image)
            .addOnSuccessListener {
                processImageLabelingFromCloud(it)
                manejarValidez(identificador)
            }
            .addOnFailureListener {
                Log.d("Fallo", "No funciona")
                Toast.makeText(
                    this, "Lo sentimos, no encontramos nigún perro en tu imagen.",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, MainMenu::class.java)

                startActivity(intent)
                finish()

            }

    }

    fun processImageLabelingFromCloud(labels: MutableList<FirebaseVisionImageLabel>) {
        val labelsSb = StringBuilder()
        for (label in labels) {
            labelsSb.append(label.text).appendln()
            val text = label.text
            if (text in listRazaPerros) {
                Log.d("_Browse", "El perro es un ${listRazaPerros[text]}")
                razaActual = listRazaPerros[text].toString()
                contienePerro = true
                encontroRaza = true
            }
            if (text == "Dog") {
                Log.d("_Browse", "La imagen tiene un perro.")
                contienePerro = true
            }

            //val entityId = label.entityId
            //val confidence = label.confidence
            Log.d("Cualidad", text)
        }

        AlertDialog.Builder(this)
            .setTitle("Etiquetas de la nube")
            .setMessage(labelsSb.toString())
            .create()
            .show()
    }


    private fun getImageUri(context: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

}
