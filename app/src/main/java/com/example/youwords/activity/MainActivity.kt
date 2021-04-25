package com.example.youwords.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.youwords.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
       // setupActionBarWithNavController(navController)
        toolbar=findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
    }

 //   override fun onSupportNavigateUp(): Boolean {
 //       return navController.navigateUp() || super.onSupportNavigateUp()
 //   }
     override fun onCreateOptionsMenu(menu: Menu?): Boolean {

     menuInflater.inflate(R.menu.menu_main, menu)
     return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       val id=item.itemId
       if(id==R.id.menu)
       {     Toast.makeText(this, "MENU! ", Toast.LENGTH_LONG).show()}
     return super.onOptionsItemSelected(item)

   }

}