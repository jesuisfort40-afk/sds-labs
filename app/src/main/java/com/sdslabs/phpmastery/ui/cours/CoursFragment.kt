package com.sdslabs.phpmastery.ui.cours

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sdslabs.phpmastery.MainActivity
import com.sdslabs.phpmastery.R
import com.sdslabs.phpmastery.databinding.FragmentCoursBinding
import com.sdslabs.phpmastery.model.LessonData

class CoursFragment : Fragment() {

    private var _binding: FragmentCoursBinding? = null
    private val binding get() = _binding!!

    private var currentLessonIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore saved lesson index
        val xpManager = (requireActivity() as MainActivity).xpManager
        currentLessonIndex = xpManager.getCurrentLesson()

        setupSidebar()
        loadLesson(currentLessonIndex)

        binding.btnPrevious.setOnClickListener {
            if (currentLessonIndex > 0) loadLesson(currentLessonIndex - 1)
        }

        binding.btnNext.setOnClickListener {
            val nextIndex = currentLessonIndex + 1
            if (nextIndex < LessonData.lessons.size) {
                markCurrentComplete()
                loadLesson(nextIndex)
            } else {
                markCurrentComplete()
                // FIX: navigate to quiz using proper fragment manager, not querySelectorAll
                (requireActivity() as MainActivity).navigateTo(R.id.nav_quiz)
            }
        }
    }

    private fun setupSidebar() {
        binding.sidebarContainer.removeAllViews()
        val xpManager = (requireActivity() as MainActivity).xpManager

        val currentLesson = LessonData.lessons.getOrNull(currentLessonIndex) ?: return
        val currentModule = LessonData.modules.find { it.index == currentLesson.moduleIndex }
            ?: LessonData.modules[0]

        val start = currentModule.lessonStartIndex
        val end = start + currentModule.lessonCount

        // Render progress dots: ● done, ▶ current, ○ todo
        for (i in start until minOf(end, LessonData.lessons.size)) {
            val dot = android.widget.TextView(requireContext())
            val isDone = xpManager.isLessonCompleted(i)
            val isCurrent = i == currentLessonIndex
            dot.text = when {
                isDone    -> "●"
                isCurrent -> "▶"
                else      -> "○"
            }
            dot.textSize = 14f
            dot.setPadding(0, 0, 10, 0)
            dot.setTextColor(android.graphics.Color.parseColor(when {
                isDone    -> "#10b981"
                isCurrent -> "#00d4ff"
                else      -> "#334155"
            }))
            dot.setOnClickListener { loadLesson(i) }
            binding.sidebarContainer.addView(dot)
        }
    }

    fun loadLesson(index: Int) {
        if (index < 0 || index >= LessonData.lessons.size) return
        currentLessonIndex = index

        val lesson = LessonData.lessons[index]
        val xpManager = (requireActivity() as MainActivity).xpManager
        xpManager.setCurrentLesson(index)

        val mod = LessonData.modules.find { it.index == lesson.moduleIndex }
        val lessonNum = index - (mod?.lessonStartIndex ?: 0) + 1

        binding.tvLessonTitle.text = lesson.title
        binding.tvLessonDesc.text = lesson.description
        binding.tvBreadcrumb.text = "Module ${mod?.number ?: "?"} › Leçon $lessonNum"

        // Render content using WebView for proper HTML/code display
        val styledHtml = buildStyledHtml(lesson.htmlContent)
        binding.webViewLesson.loadDataWithBaseURL(null, styledHtml, "text/html", "UTF-8", null)

        // Update nav buttons
        binding.btnPrevious.visibility = if (index > 0) View.VISIBLE else View.INVISIBLE
        binding.btnNext.text = if (index < LessonData.lessons.size - 1) "Suivant → (+20 XP)" else "Passer au Quiz ✓"

        // Refresh sidebar
        setupSidebar()
        binding.lessonScrollView.scrollTo(0, 0)
    }

    private fun markCurrentComplete() {
        val xpManager = (requireActivity() as MainActivity).xpManager
        if (!xpManager.isLessonCompleted(currentLessonIndex)) {
            xpManager.markLessonComplete(currentLessonIndex)
            xpManager.addXP(20)
            (requireActivity() as MainActivity).updateXPDisplay()
            Toast.makeText(requireContext(), "+20 XP ! Leçon complétée 🎉", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildStyledHtml(content: String): String {
        return """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
  * { margin:0; padding:0; box-sizing:border-box; }
  body {
    font-family: -apple-system, sans-serif;
    background: #080c14;
    color: #e2e8f0;
    padding: 16px;
    font-size: 15px;
    line-height: 1.7;
  }
  h2 {
    font-size: 20px;
    font-weight: 800;
    margin-bottom: 10px;
    color: #fff;
  }
  h3 {
    font-size: 16px;
    font-weight: 700;
    margin: 24px 0 10px;
    padding-left: 10px;
    border-left: 3px solid #7c3aed;
    color: #e2e8f0;
  }
  p { margin: 10px 0; color: #cbd5e1; }
  strong { color: #00d4ff; }
  pre {
    background: #060a10;
    border: 1px solid #1e2d45;
    border-radius: 8px;
    padding: 14px;
    overflow-x: auto;
    margin: 14px 0;
    font-family: monospace;
    font-size: 13px;
    color: #e2e8f0;
    white-space: pre;
    line-height: 1.6;
  }
  code {
    font-family: monospace;
    font-size: 12px;
    background: #0d1424;
    padding: 2px 6px;
    border-radius: 3px;
    color: #67e8f9;
  }
  pre code {
    background: transparent;
    padding: 0;
    color: #e2e8f0;
  }
  .explain-box {
    background: rgba(0,212,255,0.05);
    border: 1px solid rgba(0,212,255,0.2);
    border-radius: 8px;
    padding: 14px;
    margin: 14px 0;
    font-size: 14px;
    color: #e2e8f0;
  }
  .note-box {
    background: rgba(245,158,11,0.05);
    border: 1px solid rgba(245,158,11,0.2);
    border-radius: 8px;
    padding: 14px;
    margin: 14px 0;
    font-size: 13px;
    color: #fde68a;
  }
</style>
</head>
<body>
$content
</body>
</html>
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
