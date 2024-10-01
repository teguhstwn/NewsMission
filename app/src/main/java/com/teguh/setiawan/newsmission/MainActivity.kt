package com.teguh.setiawan.newsmission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.teguh.setiawan.newsmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var navController: NavController? = null
    private val navControllerDestination = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.detailFragment -> showBottomNavigation(false)
            R.id.splashScreenFragment -> showBottomNavigation(false)
            else -> showBottomNavigation(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavHost()
        setUpNavigationDestination()
    }

    private fun setNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()
        navController?.let { binding.navBottom.setupWithNavController(it) }
    }

    private fun setUpNavigationDestination() {
        navController?.addOnDestinationChangedListener(navControllerDestination)
    }

    private fun showBottomNavigation(isShow: Boolean) {
        binding.navBottom.isVisible = isShow
    }

    override fun onDestroy() {
        navController?.removeOnDestinationChangedListener(navControllerDestination)
        navController = null
        super.onDestroy()
    }
}