package edu.syr.assignment5

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator

import edu.syr.assignment5.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "task_id"

/**
 * A simple [Fragment] subclass.
 * Use the [Task1MasterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class Task1MasterFragment : Fragment(), MyMovieAdapter.MyItemClickListener{
    // TODO: Rename and change types of parameters
    private var taskID: Int = 2

    lateinit var myAdapter: MyMovieAdapter
    lateinit var Listener: OnFragmentInteraction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            taskID = it.getInt(ARG_PARAM1)
        }

        //retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    interface OnFragmentInteraction {
        fun onFragmentInteraction2(serving: perServingData)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Task1MasterFragment.OnFragmentInteraction) {
            Listener = context
        } else {
            throw RuntimeException(context.toString() + "must implement OnFragmentInteractionListener")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_task1_master, container, false)

        //1. create/use the RecyclerView Instance specified in layout
        val rview = view.findViewById<RecyclerView>(R.id.rview)

        //2. set or change the RecyclerView layout
        rview.layoutManager = LinearLayoutManager(view.context)

        //3. create an adapter and attach the adapter to the RecyclerView
        myAdapter = MyMovieAdapter(view.context)

        //item animation
        rview.itemAnimator = FlipInRightYAnimator()

        //adapter animation
        rview.adapter = myAdapter

        rview.adapter = SlideInLeftAnimationAdapter(myAdapter).apply {
            // Change the durations.
            setDuration(1000)

            // Disable the first scroll mode.
            setFirstOnly(false)
        }

        //Register ItemClick listener for MyMovieAdapter
        myAdapter.setMyItemClickListener(this)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Task1MasterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(task_id: Int) =
            Task1MasterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, task_id)

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if(taskID!=2) {
            super.onCreateOptionsMenu(menu, inflater)
            return
        }

        if( menu.findItem(R.id.action_search) == null ){
            inflater.inflate(R.menu.menu_recyclerview_fragment, menu)
        }

        val search = menu.findItem(R.id.action_search)!!.actionView as SearchView
        if ( search != null ){
            search.setOnQueryTextListener( object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val rview = view?.findViewById<RecyclerView>(R.id.rview)
                    val pos = myAdapter.findFirst( query!! ) //find the movie and scroll to the position
                    if (pos >= 0) {
                        rview?.smoothScrollToPosition(pos)
                        Toast.makeText(context, "Search Movie " + query + " found ... ", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        rview?.smoothScrollToPosition(0)
                        Toast.makeText(context, "Search Movie " + query + " not found ... ", Toast.LENGTH_SHORT).show()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }

        // Define the listener
        val expandListener = object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // Do something when action item collapses
                return true // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Do something when expanded
                return true // Return true to expand action view
            }
        }

        // Get the MenuItem for the action item
        val actionMenuItem = menu?.findItem(R.id.action_search)

        // Assign the listener to that action item
        actionMenuItem?.setOnActionExpandListener(expandListener)

        super.onCreateOptionsMenu(menu, inflater)
    }

    inner class ActionBarCallBack(val position:Int): ActionMode.Callback {

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

            when(item!!.itemId){

                R.id.AddPicFromLib -> {
                    Log.i("rain","AddPicFromLib")
                    myAdapter.deleteMovie(position)
                    mode!!.finish()
                }

                R.id.AddPicFromCamera -> {
                    Log.i("rain","AddPicFromCamera")
                    myAdapter.deleteMovie(position)
                    mode!!.finish()
                }
            }

            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode!!.menuInflater.inflate(R.menu.menu_popup, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            //val movie = myAdapter.getItem(position) as MovieData
            //mode!!.title = movie.title
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {

        }
    }

    //================================================================================
    override fun onItemClick(serving: perServingData) {
        Listener.onFragmentInteraction2(serving)
    }//interface function to be override by host activity

    override fun onItemMenuClicked(view: View, position: Int) {

        //Log.i("rain","onItemMenuClicked fired!!! ${position.toString()}")

        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)

        popup.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.add_location -> {
                    myAdapter.addLocationToDB(position, (activity as Task2Activity).curLocation)
                    return@setOnMenuItemClickListener true
                }

                R.id.AddPicFromLib -> {
                    myAdapter.DeleteMoviefromDB(position)
                    myAdapter.deleteMovie(position)
                    return@setOnMenuItemClickListener true
                }

                else ->{
                    return@setOnMenuItemClickListener false
                }
            }
        }

        popup.show()

    }

}