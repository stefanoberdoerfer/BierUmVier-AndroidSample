package de.stefanoberdoerfer.bierumvier.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import de.stefanoberdoerfer.bierumvier.R
import de.stefanoberdoerfer.bierumvier.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        if (destination.id == R.id.BeerDetailFragment) {
            binding.bottomNav.animate()
                .translationYBy(binding.bottomNav.height.toFloat())
                .setDuration(animationDurationMs)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    binding.bottomNav.visibility = View.GONE
                }
                .start()
        } else if (binding.bottomNav.visibility == View.GONE) {
            binding.bottomNav.animate()
                .withStartAction { binding.bottomNav.visibility = View.VISIBLE }
                .translationYBy(-binding.bottomNav.height.toFloat())
                .setDuration(animationDurationMs)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = findNavController(R.id.nav_host_fragment_content_main)
        NavigationUI.setupWithNavController(binding.bottomNav, controller)

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onResume() {
        super.onResume()
        controller.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        controller.removeOnDestinationChangedListener(listener)
    }

    companion object {
        private const val animationDurationMs = 300L
    }
}