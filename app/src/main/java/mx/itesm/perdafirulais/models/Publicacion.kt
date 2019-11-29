package mx.itesm.perdafirulais.models

data class Publicacion(
    var id: String, var uri: String, var raza: String, var fecha: String,
    var id_creador: String, var telefono: String, var estado: String,
    var titulo: String, var comentarios: String,
    var ubicacion: String
) {

    constructor() : this("", "", "", "", "", "", "", "", "", "")
}