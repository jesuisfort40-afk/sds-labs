package com.sdslabs.phpmastery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sdslabs.phpmastery.databinding.ActivityMainBinding
import com.sdslabs.phpmastery.ui.ai.AiMentorFragment
import com.sdslabs.phpmastery.ui.cours.CoursFragment
import com.sdslabs.phpmastery.ui.home.HomeFragment
import com.sdslabs.phpmastery.ui.quiz.QuizFragment
import com.sdslabs.phpmastery.ui.terminal.TerminalFragment
import com.sdslabs.phpmastery.util.XPManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var xpManager: XPManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        xpManager = XPManager(this)
        updateXPDisplay()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_cours -> loadFragment(CoursFragment())
                R.id.nav_quiz -> loadFragment(QuizFragment())
                R.id.nav_terminal -> loadFragment(TerminalFragment())
                R.id.nav_ai -> loadFragment(AiMentorFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out
            )
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun navigateTo(fragmentId: Int) {
        binding.bottomNav.selectedItemId = fragmentId
    }

    fun openLesson(lessonIndex: Int) {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is CoursFragment) {
            fragment.loadLesson(lessonIndex)
        } else {
            xpManager.setCurrentLesson(lessonIndex)
        }
    }

    fun updateXPDisplay() {
        binding.tvXpCount.text = "${xpManager.getXP()} XP"
    }
}
