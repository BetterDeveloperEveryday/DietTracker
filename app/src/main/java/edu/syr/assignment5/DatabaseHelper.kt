package edu.syr.assignment5

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues  //Creates an empty set of values using the default initial size
import android.database.Cursor
import android.location.Location

//DatabaseHelper.kt extending SQLiteOpenHelper
//define all required variables to create database:
// DB name, DB version, table names, table column names, SQL execute  statements, and so on
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    companion object {
        private val DB_NAME = "MyDailyDietPlan.db"
        private val DB_VER = 1
        private val COL_ID = "id"
        private val COL_DATE = "date"
        private val COL_FOOD_TYPE = "food_type"
        private val COL_SERVING_NUM = "serving_number"
        private val COL_PHOTO = "photo"
        private val COL_LATITUDE = "latitude"
        private val COL_LONGITUDE = "longtitude"
        private val COL_STATUS = "status"

        // create table dietrecords
        private val CREATE_TABLE_DIETRECORDS = "CREATE TABLE IF NOT EXISTS dietrecords " +
                "( $COL_ID INTEGER PRIMARY KEY, $COL_DATE DATE, $COL_FOOD_TYPE TEXT, " +
                "$COL_SERVING_NUM INTEGER, $COL_PHOTO BLOB, $COL_LATITUDE DOUBLE, $COL_LONGITUDE DOUBLE, $COL_STATUS BOOL)"

        private val DROP_TABLE_DIETRECORDS = "DROP TABLE IF EXISTS dietrecords"

    }

    //Close Database, this will be called when activity/fragment is destroyed
    fun closeDB(){
        val db = this.readableDatabase
        if(db != null && db.isOpen){
            db.close()
        }
    }

    //table create statements in onCreate
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE_DIETRECORDS)
    }

    //table drop statements in onUpgrade()
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DROP_TABLE_DIETRECORDS)
    }

    fun initializeTables(){
        val db = this.readableDatabase
    }

    //Insert one day's goal into the table diet_records
    fun addData(date: String, food_type: String, serving_number: Int): Long{

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_DATE, date)
        values.put(COL_FOOD_TYPE, food_type)
        values.put(COL_SERVING_NUM, serving_number)
        values.put(COL_STATUS, false)

        return db.insert("dietrecords", null, values)
    }

    fun setDietStatus(id:Int,stat:Boolean): Int
    {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_STATUS, stat)


        return db.update("dietrecords", values, "$COL_ID = ?", arrayOf(id.toString()))
    }



    @SuppressLint("Range")
    fun checkFinish (date:String):Int{
        val query = "SELECT * FROM dietrecords " +
                "Where $COL_DATE='$date'"

        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        if (!c.moveToFirst())
            return 0

        do {
            val result = convertIntToBoolean(c.getInt(c.getColumnIndex(COL_STATUS)))
            if(!result) return 2
        }
        while(c.moveToNext())

        return 1
    }

    fun addDataSet(data: wholeData){

        val grains = data.member_serving_number_Grains
        for (i in 1..grains){
            addData(data.member_date!!, "Grains", i)
        }

        val Vegetable = data.member_serving_number_Vegetable
        for (i in 1..Vegetable){
            addData(data.member_date!!, "Vegetables", i)
        }

        val Fruit = data.member_serving_number_Fruit
        for (i in 1..Fruit){
            addData(data.member_date!!, "Fruits", i)
        }

        val Dairy = data.member_serving_number_Dairy
        for (i in 1..Dairy){
            addData(data.member_date!!, "Dairy", i)
        }

        val Meat = data.member_serving_number_Meat
        for (i in 1..Meat){
            addData(data.member_date!!,"Meats", i)
        }

        val Nuts = data.member_serving_number_Nuts
        for (i in 1..Nuts){
            addData(data.member_date!!, "Nuts", i)
        }

    }

    fun addLocation(id: Int, loc:Location):Int{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_LATITUDE, loc.latitude)
        values.put(COL_LONGITUDE, loc.longitude)

        return db.update("dietrecords", values, "$COL_ID = ?", arrayOf(id.toString()))
    }

    fun addImage(id: Int, image: ByteArray):Int{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_PHOTO, image)

        return db.update("dietrecords", values, "$COL_ID = ?", arrayOf(id.toString()))
    }

    //adapter items = myDB.getAllMovies(), //val items : ArrayList<MovieData>,
    //read data from database to return items : ArrayList<MovieData>, to be used by adapter*/

    fun convertIntToBoolean(value: Int): Boolean {
        return value != 0
    }

    @SuppressLint("Range")
    fun getAllData(): ArrayList<perServingData> {
        val allServingsList = ArrayList<perServingData>()
        val query = "SELECT * FROM dietrecords " +
                "Where $COL_DATE=(select Max($COL_DATE) from dietrecords)"

        val db = this.readableDatabase
        val c = db.rawQuery(query, null)

        if (c.moveToFirst()) {
            do {
                if(c.getColumnIndex(COL_LATITUDE)!=null && c.getColumnIndex(COL_LONGITUDE)!=null && c.getColumnIndex(
                        COL_PHOTO)!=null){
                    val oneServing = perServingData(
                        id = c.getInt(c.getColumnIndex(COL_ID)),
                        date= c.getString(c.getColumnIndex(COL_DATE)),
                        food_type = c.getString(c.getColumnIndex(COL_FOOD_TYPE)),
                        serving_number=c.getInt(c.getColumnIndex(COL_SERVING_NUM)),
                        photo = c.getBlob(c.getColumnIndex(COL_PHOTO)),
                        latitude = c.getString(c.getColumnIndex(COL_LATITUDE)),
                        longitude = c.getString(c.getColumnIndex(COL_LONGITUDE)),
                        status = convertIntToBoolean(c.getInt(c.getColumnIndex(COL_STATUS)))
                    )
                    allServingsList.add(oneServing)
                }
                else if (c.getColumnIndex(COL_PHOTO)!=null)
                        {
                            val oneServing = perServingData(
                                id = c.getInt(c.getColumnIndex(COL_ID)),
                                date= c.getString(c.getColumnIndex(COL_DATE)),
                                food_type = c.getString(c.getColumnIndex(COL_FOOD_TYPE)),
                                serving_number=c.getInt(c.getColumnIndex(COL_SERVING_NUM)),
                                photo = c.getBlob(c.getColumnIndex(COL_PHOTO)),
                                latitude = "",
                                longitude = "",
                                status = convertIntToBoolean(c.getInt(c.getColumnIndex(COL_STATUS)))
                            )
                            allServingsList.add(oneServing)
                }
                else{
                    val oneServing = perServingData(
                        id = c.getInt(c.getColumnIndex(COL_ID)),
                        date= c.getString(c.getColumnIndex(COL_DATE)),
                        food_type = c.getString(c.getColumnIndex(COL_FOOD_TYPE)),
                        serving_number=c.getInt(c.getColumnIndex(COL_SERVING_NUM)),
                        photo = null,
                        latitude = "",
                        longitude = "",
                        status = convertIntToBoolean(c.getInt(c.getColumnIndex(COL_STATUS)))
                    )
                    allServingsList.add(oneServing)
                }

            }while(c.moveToNext())
        }

        db.close()
        return allServingsList
    }

}