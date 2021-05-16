package com.example.youwords.main_activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.youwords.R
import com.example.youwords.data.WordViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

//интерфейс для связи с событиями(кликами) во фрагментах
interface ActivityInterractor {
    fun transferOnSearchFragment()
    fun transferOnAddWordFragment()
}

class MainActivity : AppCompatActivity(R.layout.activity_main), ActivityInterractor {
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var wordviewmodel: WordViewModel
    private  var currentFragment:Int?=null
    private lateinit var navHostFragment:NavHostFragment

    //при нажатии кнопки назад подсвечиваем нужную иконку
    override fun onBackPressed() {
        super.onBackPressed()
        currentFragment =
            NavHostFragment.findNavController(navHostFragment).currentDestination?.id
        when (currentFragment) {
            R.id.startFragment -> {
                bottomNavigationView.menu.findItem(R.id.home).isChecked= true
            }
            R.id.addWordFragment -> {
                bottomNavigationView.menu.findItem(R.id.add).isChecked= true
            }
            R.id.searchFragment -> {
                bottomNavigationView.menu.findItem(R.id.search).isChecked= true
                bottomNavigationView.menu.findItem(R.id.search).isCheckable= true
            }
            R.id.allWordsFragment -> {
                bottomNavigationView.menu.findItem(R.id.list).isChecked= true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordviewmodel = ViewModelProvider(this).get( WordViewModel::class.java)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        //  toolbar.setOnMenuItemClickListener(this)
        // supportActionBar?.setDisplayShowHomeEnabled(true)
        // toolbar.setNavigationIcon(R.drawable.ic_baseline_24)
        //  toolbar.setNavigationOnClickListener{
        //    Toast.makeText(this, "Введите значение", Toast.LENGTH_LONG).show()


        //toolbar.inflateMenu(R.menu.menu_main)

        // currentFragment =
             //NavHostFragment.findNavController(navHostFragment).currentDestination?.id



        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null

        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                 currentFragment =
                    NavHostFragment.findNavController(navHostFragment).currentDestination?.id
                when (menuItem.itemId) {
                    R.id.home -> {
                        when (currentFragment) {
                            R.id.startFragment -> {
                            }
                            R.id.allWordsFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_allWordsFragment_to_StartFragment)
                            R.id.searchFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_searchFragment_to_startFragment)
                            R.id.addWordFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_addWordFragment_to_startFragment)

                            R.id.foundFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_foundFragment_to_startFragment)
                        }
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.add -> {
                        when (currentFragment) {
                            R.id.startFragment -> {
                                navHostFragment.findNavController()
                                    .navigate(R.id.action_startFragment_to_addWordFragment)
                            }
                            R.id.allWordsFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_allWordsFragment_to_addWordFragment)
                            R.id.searchFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_searchFragment_to_addWordFragment)
                            R.id.addWordFragment -> {
                            }
                            R.id.foundFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_foundFragment_to_addWordFragment)
                        }
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.search -> {
                        bottomNavigationView.menu.findItem(R.id.search).isCheckable = true
                        //  bottomNavigationView.menu.findItem(  R.id.search).setVisible(false)
                        when (currentFragment) {
                            R.id.startFragment -> {
                                navHostFragment.findNavController()
                                    .navigate(R.id.action_startFragment_to_searchFragment)
                            }
                            R.id.allWordsFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_allWordsFragment_to_searchFragment)
                            R.id.searchFragment -> {
                            }
                            R.id.addWordFragment -> {
                                navHostFragment.findNavController()
                                    .navigate(R.id.action_addWordFragment_to_searchFragment)
                            }
                            R.id.foundFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_foundFragment_to_searchFragment)
                        }
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.list -> {
                        when (currentFragment) {
                            R.id.startFragment -> {
                                navHostFragment.findNavController()
                                    .navigate(R.id.action_startFragment_to_allWordsFragment)
                            }
                            R.id.allWordsFragment -> {
                            }
                            R.id.searchFragment -> {
                                navHostFragment.findNavController()
                                    .navigate(R.id.action_searchFragment_to_allWordsFragment)
                            }
                            R.id.addWordFragment -> {
                                navHostFragment.findNavController()
                                    .navigate(R.id.action_addWordFragment_to_allWordsFragment)
                            }
                            R.id.foundFragment -> navHostFragment.findNavController()
                                .navigate(R.id.action_foundFragment_to_allWordsFragment)
                        }
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    //прослушивание нажатий в фрагментах для изменения подсветки иконок
    override fun transferOnSearchFragment() {
        bottomNavigationView.menu.findItem(R.id.search).isCheckable = false
    }
    override fun transferOnAddWordFragment() {
        bottomNavigationView.menu.findItem(R.id.home).isChecked = true
    }

////////всплывающее меню, диалоги
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.all_not_remember->alertDialogRemember()
            R.id.del_all->alertDialogDel()
            R.id.del_remember->alertDialogDelRemember()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun alertDialogDel() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.question_del_all)
        builder.setPositiveButton(R.string.yes){ _, _ ->
        wordviewmodel.deleteAll()
        }
        builder.setNegativeButton(R.string.no){ _, _ ->
        }
        builder.create().show()
    }
    private fun alertDialogRemember() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.question_remember_all)
        builder.setPositiveButton(R.string.yes){ _, _ ->
            wordviewmodel.updateAll_Remember()
        }
        builder.setNegativeButton(R.string.no){ _, _ ->
        }
        builder.create().show()
    }
    private fun alertDialogDelRemember(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.question_remember_not_all)
        builder.setPositiveButton(R.string.yes){ _, _ ->
            wordviewmodel.updateAll_Not_Remember()
        }
        builder.setNegativeButton(R.string.no){ _, _ ->
        }
        builder.create().show()
    }
}

