package edu.syr.assignment5

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import java.util.*

class Task1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task1)

        val tbar: Toolbar?  = findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(tbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        val todayDate = findViewById<EditText>(R.id.date)
        val GrainsServings = findViewById<EditText> (R.id.GrainsServings1)
        val VegetableServings = findViewById<EditText>(R.id.VegetableServings2)
        val FruitServings = findViewById<EditText>(R.id.FruitServings3)
        val DairyServings = findViewById<EditText>(R.id.DairyServings4)
        val MeatServings = findViewById<EditText>(R.id.MeatServings5)
        val NutsServings = findViewById<EditText>(R.id.NutsServings6)
        val button = findViewById<Button>(R.id.submit_goal)
        val pickDate = findViewById<Button>(R.id.button2)


        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        todayDate.setText(String.format("%04d-%02d-%02d",year,(month+1),day))
        todayDate.keyListener=null
        //button click to show DatePickerDialog

        pickDate.setOnClickListener {

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener {
                        view, mYear, mMonth, mDay ->run {
                    val strDate=String.format("%04d-%02d-%02d", mYear, mMonth + 1, mDay)
                    todayDate.setText(strDate)
                    }
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }



        button.setOnClickListener{
            val dbHandler=DatabaseHelper(this)
            dbHandler.initializeTables()

            val todaygoal=wholeData(todayDate.text.toString(),
                GrainsServings.text.toString().toInt(),
                VegetableServings.text.toString().toInt(),
                FruitServings.text.toString().toInt(),
                DairyServings.text.toString().toInt(),
                MeatServings.text.toString().toInt(),
                NutsServings.text.toString().toInt())

            dbHandler.addDataSet(todaygoal)

            //dbHandler.addDate(todayDate.text.toString())


            val intent = Intent(this, Task2Activity::class.java)
            intent.putExtra("action", 0)
            startActivity(intent)

        }

        }

}