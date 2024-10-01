package com.teguh.setiawan.newsmission.ui.splashscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.teguh.setiawan.newsmission.R
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.black)
        navigateToMain()
    }

    private fun navigateToMain() {
        lifecycleScope.launch {
            delay(2.seconds)
            findNavController().apply {
                popBackStack()
                navigate(R.id.homeFragment)
            }
        }
    }
}
