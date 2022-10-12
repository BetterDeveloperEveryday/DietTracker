package edu.syr.assignment5

//import android.content.Context
//import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import edu.syr.assignment5.R
//import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.util.*
import kotlin.math.log

// extension function to get / download bitmap from url
fun URL.toBitmap(): Bitmap?{
    return try {
        BitmapFactory.decodeStream(openStream())
    }catch (e:IOException){
        null
    }
}

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */






class MovieFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var tvTitle:TextView
    private lateinit var tvDescription:TextView
    private lateinit var imgImage:ImageView
    private lateinit var tvYear:TextView
    private lateinit var rbStars:RatingBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         //networkUtilsCoroutines = NetworkUtilsCoroutines()
        //-------------------------------------------------------------
        tvTitle=view.findViewById(R.id.tvTitle)
        tvDescription=view.findViewById(R.id.tvDescription)
        imgImage=view.findViewById(R.id.imgImage)
        tvYear=view.findViewById(R.id.tvYear)
        rbStars=view.findViewById(R.id.rbStars)
        //-------------------------------------------------------------

        ShowMovieInfo()
    }

    private fun ShowMovieInfo()  {

        //tvTitle.text=movie.title
        //tvDescription.text= movie.overview



        var avg:Double= 0.0


        rbStars.rating=(avg/2).toFloat()
        rbStars.stepSize= 0.1F


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_detail, menu)

        val shareItem = menu.findItem(R.id.action_share)
        val mShareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.type = "text/plain"


        if (mShareActionProvider != null && intentShare != null){
            mShareActionProvider.setShareIntent(intentShare)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

}