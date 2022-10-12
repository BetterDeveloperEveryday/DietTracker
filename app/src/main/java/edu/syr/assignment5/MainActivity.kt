package edu.syr.assignment5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigation = findViewById<NavigationView>(R.id.navi)

        setSupportActionBar(toolbar)

        val drawer = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, R.string.close)
        drawer.syncState()

        navigation.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // handler navigation menu item selection!

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        when (item.itemId) {

            R.id.firstActivity -> {
                val intent = Intent(this, Task1Activity::class.java)
                intent.putExtra("action", 0)
                startActivity(intent)
            }

            R.id.secondActivity -> {
                val intent = Intent(this, Task2Activity::class.java)
                intent.putExtra("action", 1)
                startActivity(intent)
            }

            R.id.thirdActivity -> {
                val intent = Intent(this, Task3Activity::class.java)
                intent.putExtra("action", 2)
                startActivity(intent)
            }

            R.id.fourthActivity -> {
                val intent = Intent(this, Task4Activity::class.java)
                //intent.putExtra("action", 3)
                intent.putExtra(Task4Activity.VIDEO_ID, "n86CT4SB_Yg")
                startActivity(intent)
            }
        }

        drawerLayout.closeDrawer(Gravity.LEFT)
        return true
    }

}