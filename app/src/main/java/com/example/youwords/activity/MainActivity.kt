package com.example.youwords.activity

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.youwords.R
import com.example.youwords.allwords.AllWordsFragment
import com.example.youwords.search_and_found.SearchFragment
import com.example.youwords.start.StartFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

interface ActivityInterractor {

    fun onFragmentClosed(s:String)
}


class MainActivity : AppCompatActivity(R.layout.activity_main), ActivityInterractor {
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView


var k:String?=null
    override fun onFragmentClosed(s:String) {
        bottomNavigationView.menu.findItem(  R.id.search).isCheckable= false
       // bottomNavigationView.menu.findItem(  R.id.list).isVisible=false
      //  bottomNavigationView.menu.findItem(  R.id.search).setVisible(true)
  k=s

    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Log.i("LOG", "$k")
        //setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
      //setupActionBarWithNavController(navController)
       toolbar=findViewById(R.id.toolbar)

      setSupportActionBar(toolbar)
      //  toolbar.setOnMenuItemClickListener(this)
     // supportActionBar?.setDisplayShowHomeEnabled(true)
       // toolbar.setNavigationIcon(R.drawable.ic_baseline_24)
      //  toolbar.setNavigationOnClickListener{
        //    Toast.makeText(this, "Введите значение", Toast.LENGTH_LONG).show()


 //toolbar.inflateMenu(R.menu.menu_main)


        bottomNavigationView=findViewById(R.id.bottomNavigationView)

        bottomNavigationView.background=null



        //    bottomNavigationView.background=null
      //  val g:AllWordsFragment

       // val currentFragment = supportFragmentManager.primaryNavigationFragment


        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            val currentFragment = NavHostFragment.findNavController(navHostFragment).currentDestination?.id
            when (menuItem.itemId) {
                R.id.home -> {
                    when(currentFragment){
                        R.id.startFragment->{}
                        R.id.allWordsFragment-> navHostFragment.findNavController().navigate(R.id.action_allWordsFragment_to_StartFragment)
                        R.id.searchFragment->navHostFragment.findNavController().navigate(R.id.action_searchFragment_to_startFragment)
                        R.id.addWordFragment->navHostFragment.findNavController().navigate(R.id.action_addWordFragment_to_startFragment)
                        R.id.wordsReadFragment->navHostFragment.findNavController().navigate(R.id.action_wordsReadFragment_to_startFragment)
                        R.id.foundFragment->navHostFragment.findNavController().navigate(R.id.action_foundFragment_to_startFragment)
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.add-> {
                    when(currentFragment){
                        R.id.startFragment->{navHostFragment.findNavController().navigate(R.id.action_startFragment_to_addWordFragment)}
                        R.id.allWordsFragment-> navHostFragment.findNavController().navigate(R.id.action_allWordsFragment_to_addWordFragment)
                        R.id.searchFragment->navHostFragment.findNavController().navigate(R.id.action_searchFragment_to_addWordFragment)
                        R.id.addWordFragment->{}
                        R.id.wordsReadFragment->navHostFragment.findNavController().navigate(R.id.action_wordsReadFragment_to_addWordFragment)
                        R.id.foundFragment->navHostFragment.findNavController().navigate(R.id.action_foundFragment_to_addWordFragment)
                    }
                        return@OnNavigationItemSelectedListener true
                 }
                R.id.search -> {
                    bottomNavigationView.menu.findItem(  R.id.search).isCheckable= true
                      //  bottomNavigationView.menu.findItem(  R.id.search).setVisible(false)
                        Toast.makeText(this, "перещли", Toast.LENGTH_LONG).show()

                    when(currentFragment){
                        R.id.startFragment->{navHostFragment.findNavController().navigate(R.id.action_startFragment_to_searchFragment)}
                        R.id.allWordsFragment-> navHostFragment.findNavController().navigate(R.id.action_allWordsFragment_to_searchFragment)
                        R.id.searchFragment->{}
                        R.id.addWordFragment->{navHostFragment.findNavController().navigate(R.id.action_addWordFragment_to_searchFragment)}
                        R.id.wordsReadFragment->navHostFragment.findNavController().navigate(R.id.action_wordsReadFragment_to_searchFragment)
                        R.id.foundFragment->navHostFragment.findNavController().navigate(R.id.action_foundFragment_to_searchFragment)
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.list -> {
                    when(currentFragment){
                        R.id.startFragment->{navHostFragment.findNavController().navigate(R.id.action_startFragment_to_allWordsFragment)}
                        R.id.allWordsFragment->{}
                        R.id.searchFragment->{navHostFragment.findNavController().navigate(R.id.action_searchFragment_to_allWordsFragment)}
                        R.id.addWordFragment->{navHostFragment.findNavController().navigate(R.id.action_addWordFragment_to_allWordsFragment)}
                        R.id.wordsReadFragment->navHostFragment.findNavController().navigate(R.id.action_wordsReadFragment_to_allWordsFragment)
                        R.id.foundFragment->navHostFragment.findNavController().navigate(R.id.action_foundFragment_to_allWordsFragment)
                    }
                    return@OnNavigationItemSelectedListener true
                }


            }
            false
        }
            bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


}

  // override fun onSupportNavigateUp(): Boolean {
  //      return navController.navigateUp() || super.onSupportNavigateUp()
  // }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

    menuInflater.inflate(R.menu.menu_main, menu)
    return true
   }

 /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
      val id=item.itemId
      if(id==R.id.all_not_remember)
      {      Log.i("LOG", "!!!!!!!!!")
          Toast.makeText(this, "MENU! ", Toast.LENGTH_LONG).show()}
        return super.onOptionsItemSelected(item)
    }
*/


}


