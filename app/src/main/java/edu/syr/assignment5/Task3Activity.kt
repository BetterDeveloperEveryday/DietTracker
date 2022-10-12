package edu.syr.assignment5

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import java.util.*

//, Task1MasterFragment.OnFragmentInteraction
class Task3Activity : AppCompatActivity() {

    //lateinit var frag_movie: MovieFragment
    lateinit var frag_master: Task1MasterFragment
    lateinit var tbTop:Toolbar
    lateinit var tbBottom:Toolbar
    val myDB = DatabaseHelper(this)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task3)

        //--top toolbar---------------------------------
        tbTop = findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(tbTop)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.str_title_task3)
        //supportActionBar?.setDisplayShowTitleEnabled(false)

        //----------------------------------------------------

        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //button click to show DatePickerDialog
        val button = findViewById<Button>(R.id.button)
        val result = findViewById<TextView>(R.id.textView3)

        button.setOnClickListener {

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener {
                        view, mYear, mMonth, mDay -> run{
                            val strDate=String.format("%04d-%02d-%02d",mYear,mMonth+1,mDay)
                            val result1 = myDB.checkFinish(strDate)
                            if (result1==0) {
                                result.setText("Oops! It looks like you did not track on "+strDate+".")
                            }
                            else if (result1==1){
                                result.setText("Congratulations! You did a great job on "+strDate+". Keep going!")
                            }
                            else{
                                result.setText("Sorry. You did not eat enough on "+strDate)
                            }
                        }
                },
                year,
                month,
                day
            )

            //show dialog
            datePickerDialog.show()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this)
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}