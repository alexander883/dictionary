package com.example.youwords.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.allwords.AllWordsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var k:AllWordsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
       // setupActionBarWithNavController(navController)
        toolbar=findViewById(R.id.toolbar)
        //bottomNavigationView.background.current
        setSupportActionBar(toolbar)
     //   bottomNavigationView.background=null
        bottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background=null

        bottomNavigationView.setOnNavigationItemReselectedListener {
       when (it.itemId){
           R.id.search->  Toast.makeText(this, "List", Toast.LENGTH_LONG).show()


        } }



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