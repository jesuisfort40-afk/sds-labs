package com.sdslabs.phpmastery.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sdslabs.phpmastery.MainActivity
import com.sdslabs.phpmastery.R
import com.sdslabs.phpmastery.databinding.FragmentHomeBinding
import com.sdslabs.phpmastery.model.LessonData
import com.sdslabs.phpmastery.model.Module

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeroSection()
        setupModulesGrid()
    }

    private fun setupHeroSection() {
        val xpManager = (requireActivity() as MainActivity).xpManager
        val completed = xpManager.getCompletedLessons().size
        val totalLessons = LessonData.lessons.size

        binding.tvXpValue.text = xpManager.getXP().toString()
        binding.tvCompletedLessons.text = completed.toString()
        binding.tvTotalLessons.text = totalLessons.toString()

        val progress = (completed.toFloat() / totalLessons * 100).toInt()
        binding.progressBar.progress = progress
        binding.tvProgressPercent.text = "$progress%"
    }

    private fun setupModulesGrid() {
        binding.modulesContainer.removeAllViews()
        LessonData.modules.forEach { module ->
            val card = createModuleCard(module)
            binding.modulesContainer.addView(card)
        }
    }

    private fun createModuleCard(module: Module): View {
        val inflater = LayoutInflater.from(requireContext())
        val card = inflater.inflate(R.layout.item_module_card, binding.modulesContainer, false)

        val xpManager = (requireActivity() as MainActivity).xpManager
        val start = module.lessonStartIndex
        val end = start + module.lessonCount
        val allDone = module.lessonCount > 0 && (start until end).all { xpManager.isLessonCompleted(it) }

        card.findViewById<TextView>(R.id.tvModuleNum).text =
            "MODULE ${module.number}${if (allDone) " ✓" else if (module.isLocked) " 🔒" else ""}"

        card.findViewById<TextView>(R.id.tvModuleTitle).text = module.title
        card.findViewById<TextView>(R.id.tvModuleDesc).text = module.description

        val tagView = card.findViewById<TextView>(R.id.tvModuleTag)
        tagView.text = if (allDone) "Complété" else module.tag
        when (module.tagColor) {
            "green" -> tagView.setTextColor(Color.parseColor("#10b981"))
            "cyan" -> tagView.setTextColor(Color.parseColor("#00d4ff"))
            "purple" -> tagView.setTextColor(Color.parseColor("#a78bfa"))
            "amber" -> tagView.setTextColor(Color.parseColor("#f59e0b"))
        }

        if (module.isLocked) {
            card.alpha = 0.5f
        } else {
            card.setOnClickListener {
                val activity = requireActivity() as MainActivity
                activity.navigateTo(R.id.nav_cours)
                // Open the first lesson of this module
                activity.openLesson(module.lessonStartIndex)
            }
        }

        return card
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
