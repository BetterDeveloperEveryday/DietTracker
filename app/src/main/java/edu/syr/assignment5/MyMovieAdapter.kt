package edu.syr.assignment5

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.youtube.player.internal.j
import com.squareup.picasso.Picasso

class MyMovieAdapter(val context: Context):
    androidx.recyclerview.widget.RecyclerView.Adapter<MyMovieAdapter.MovieViewHolder>() {

    //create DB in RecyclerView Adapter
    val myDB = DatabaseHelper(context)
    var items= myDB.getAllData()
    lateinit var location: String

    var myListener: MyItemClickListener? = null
    var lastPosition = -1 //for animation


    interface MyItemClickListener {
        //interface – a type that will be derived from by the host activity
        fun onItemClick(oneServing: perServingData) //interface function to be override by host activity
        fun onItemMenuClicked(view: View, position: Int)
    }

    //this is for host activity or host fragment to call
    fun setMyItemClickListener(listener: MyItemClickListener) {
        this.myListener = listener
    }

    // static inner ViewHolder class
    inner class MovieViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val moviePoster = view.findViewById<ImageView>(R.id.rvPoster)
        val foodtype = view.findViewById<TextView>(R.id.rvTitle)
        val servingNumber = view.findViewById<TextView>(R.id.rvOverview)
        val location = view.findViewById<TextView>(R.id.rvLocation)
        val overflow = view.findViewById<ImageView>(R.id.overflow)
        val status = view.findViewById<CheckBox>(R.id.rvChx)

        init {

            status.setOnCheckedChangeListener { buttonView, isChecked ->
                items?.get(adapterPosition).status = isChecked
            }

            view.setOnClickListener {
                if (myListener != null) {
                    if (adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                        items?.get(adapterPosition)?.let { it1 -> myListener!!.onItemClick(it1) }
                    }
                }
            }



            overflow.setOnClickListener {
                if (myListener != null) {
                    if (adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                        myListener!!.onItemMenuClicked(it, adapterPosition)
                    }
                }
            }

        }
    }


    //1.adapter’s onCreateViewHolder() to construct the view holder and set the view
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View=layoutInflater.inflate(R.layout.movie_list_item_right, parent, false)
        return MovieViewHolder(view)
    }

    fun readAllData() {
        items = myDB.getAllData()
        notifyDataSetChanged()
    }

    //2.adapter’s onBindViewHolder() to bind the view holder to its data
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        // bind each item in the list to a view
        if(items==null) return
        var serving = items?.get(position)

        //holder.moviePoster.setImageResource(movie.poster_id!!)
        if (serving != null) {
            holder.foodtype.text = serving.food_type
        }

        //setAnimation(holder.moviePoster, position)

        var length = serving?.food_type!!.length
        length = if (length > 150) 150 else length
        holder.servingNumber.text = serving.serving_number.toString()

        if(serving.latitude==null || serving.longitude==null){
            holder.location.text = ""
        }
        else{
            holder.location.text = "[Latitude: "+ serving.latitude+", Longitude: "+serving.longitude +"]"
        }
        holder.status.isChecked = serving.status!!

        holder.status.setOnClickListener {
            myDB.setDietStatus(items!![position].id, holder.status.isChecked)
        }
    }

    //3.adapter’s getItemCount() to return number of items of data
    override fun getItemCount(): Int {
        // number of items in the list
        return items?.size
    }

    fun deleteMovie(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun deleteFinishedServings() {

        var cnt = 0
        var itemCnt=0
        if(items!=null)
            itemCnt=items!!.size
        for (i in 0 until itemCnt) {
            if (items!![i].status!!)
                cnt += 1
        }

        for (i in 0 until cnt) {
            for (j in items!!.indices) {
                if (!items!![j].status!!) {
                    items!!.removeAt(j)
                    notifyItemRemoved(j)
                    break
                }
            }
        }

    }


    /*fun duplicateMovie(position: Int) {
            var movie = items[position].copy()
            items.add(position+1, movie)
            Log.i("rain","prepare to duplicate ${position.toString()} ")
            notifyItemInserted(position+1)
            Log.i("rain","Position ${position.toString()} duplicated")
            }*/

    fun findFirst(query: String): Int {
        for ((index, serving) in items.withIndex()) {
            if (serving.food_type!!.equals(query, ignoreCase = true))
                return index
        }
        return -1
    }


    fun AddMovietoDB(position: Int) {
        val movie = items[position]
        //myDB.addMovie(movie)
    }

    fun DeleteMoviefromDB(position: Int) {
        val movie = items[position]
        val id = movie.id
        //myDB.deleteMovie(id!!)
    }

    fun addLocationToDB(position: Int, loc: Location) {

       /*if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
          ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
           val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
           var loc : Location?= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

           if (loc == null) {
               // fall	back	to network if GPS is	not	available
               loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
               if(loc==null){
               location="no location information"
               }
               else{
               val myLat = loc.getLatitude();
               val myLng = loc.getLongitude();
               location = "[Latitude: "+ myLat+", Longitude: "+myLng +"]"
           }}

           if (loc != null) {
               val myLat = loc.getLatitude();
               val myLng = loc.getLongitude();
               location = "[Latitude: "+ myLat+", Longitude: "+myLng +"]"
           }
       }*/

        myDB.addLocation(items[position].id, loc)

    }
}