package edu.syr.assignment5
import java.io.Serializable

data class perServingData(
    var id:Int,
    var date:String,
    var food_type: String,
    var serving_number: Int,
    var photo: ByteArray?,
    var latitude: String?,
    var longitude: String?,
    var status: Boolean)
: Serializable