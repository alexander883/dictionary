package com.example.youwords.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.*
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.allwords.AllWordsFragment
import com.example.youwords.search_and_found.SearchFragment
import com.example.youwords.start.StartFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
       // setupActionBarWithNavController(navController)
        toolbar=findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        bottomNavigationView=findViewById(R.id.bottomNavigationView)
        lateinit  var  sF: Fragment

        bottomNavigationView.background=null
       val  startF=StartFragment()
        //supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, startF).commit()
        //    bottomNavigationView.background=null
      //  val g:AllWordsFragment
       // supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_container, startF).commit()

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    val fragment = SearchFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.list -> {
                    val fragment =StartFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


fun listner() {///// этот метод работает не так-по второму клику кнопки бара
    bottomNavigationView.setOnNavigationItemReselectedListener {
        Toast.makeText(this, " is clicked", Toast.LENGTH_SHORT).show()
        when (it.itemId) {
            R.id.search -> sF = SearchFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, sF).commit()
    }
}

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