// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.zaelaroseapps.initiativeapp.databinding.ActivityMainBinding
import androidx.navigation.ui.setupActionBarWithNavController as setupActionBarWithNavController1

class MainActivity : AppCompatActivity()  {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
    }

    /**
     * Called when the hamburger menu or back button are pressed on the Toolbar
     *
     * Delegate this to Navigation.
     */
    override fun onSupportNavigateUp()
            = navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)

    /**
     * Setup Navigation for this Activity
     */
    private fun setupNavigation() {
        // first find the nav controller
        val navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.toolbar)

        // then setup the action bar, tell it about the DrawerLayout
        setupActionBarWithNavController1(navController, binding.drawerLayout)


        // finally setup the left drawer (called a NavigationView)
        binding.navigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
//            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            when(destination.id) {
                R.id.homeFragment -> {
                    binding.toolbar.title = "Home"
                }
                else -> {
                    binding.toolbar.title = "Game"
                }
            }
        }
    }
}
