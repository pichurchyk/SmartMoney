package com.example.smartmoney.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smartmoney.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var menu: BottomNavigationView? = null

    var isMenuVisible: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btnHideMenu = findViewById<AppCompatImageButton>(R.id.btnHideMenu)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()
        menu = findViewById(R.id.menu)
        menu?.setupWithNavController(navController)

        btnHideMenu.setOnClickListener {
            slideMenu()
            if (!isMenuVisible) {
                btnHideMenu.setImageResource(R.drawable.ic_hide_active)
            }
            else {
                btnHideMenu.setImageResource(R.drawable.ic_hide)
            }
        }
    }

    private fun slideMenu() {
        val direction = if (isMenuVisible) 0F else 1F

        menu?.animate()
            ?.alpha(direction)?.duration = 100
        isMenuVisible = !isMenuVisible
    }
}