package edu.syr.assignment5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import android.location.Location
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
//import android.widget.Toolbar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.core.app.NavUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//, Task1MasterFragment.OnFragmentInteraction
class Task2Activity : AppCompatActivity(),Task1MasterFragment.OnFragmentInteraction{

    //Create location services client
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var curLocation:Location=Location("nonsense")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)
        curLocation.latitude=123.0345
        curLocation.longitude=67.889

        var fragMaster: Task1MasterFragment? = null
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if(savedInstanceState!=null){
            fragMaster=supportFragmentManager.findFragmentByTag("master_fragment") as Task1MasterFragment
        }
        else {
            fragMaster = Task1MasterFragment()
            val bdl = Bundle()
            bdl.putInt("task_id", 2)
            fragMaster.arguments = bdl

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_Container1, fragMaster, "master_fragment")
                .setReorderingAllowed(true)
                //.addToBackStack(null)
                .commit()
        }

        //action bar----------------------------------------------------------
        val tbar:Toolbar?  = findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(tbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.str_title_task2)


        val btnShow = findViewById<ImageButton>(R.id.btn_showbtbar)
        btnShow.setOnClickListener()
        {
            fragMaster?.myAdapter?.deleteFinishedServings()
        }

        val ShowAll = findViewById<ImageButton>(R.id.btn_showAll)
        ShowAll.setOnClickListener()
        {
            fragMaster?.myAdapter?.readAllData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*val id = item.itemId
        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this)
            super.onBackPressed()
            return true
        }
         */
        return super.onOptionsItemSelected(item)
    }


    override fun onFragmentInteraction2(serving: perServingData){
        val myFragment = MovieFragment()
        val bdl = Bundle()

        bdl.putSerializable("movie", serving)
        myFragment.arguments = bdl

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_Container1, myFragment)
            //.addToBackStack(null)
            .commit()
    }

}



