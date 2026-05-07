package com.sdslabs.phpmastery.util

import android.content.Context
import android.content.SharedPreferences

class XPManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("sds_labs_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_XP = "total_xp"
        private const val KEY_COMPLETED_LESSONS = "completed_lessons"
        private const val KEY_CURRENT_LESSON = "current_lesson"
    }

    fun getXP(): Int = prefs.getInt(KEY_XP, 240)

    fun addXP(amount: Int): Int {
        val newXP = getXP() + amount
        prefs.edit().putInt(KEY_XP, newXP).apply()
        return newXP
    }

    fun getCompletedLessons(): Set<String> =
        prefs.getStringSet(KEY_COMPLETED_LESSONS, setOf("0", "1")) ?: setOf("0", "1")

    fun markLessonComplete(lessonIndex: Int) {
        val completed = getCompletedLessons().toMutableSet()
        completed.add(lessonIndex.toString())
        prefs.edit().putStringSet(KEY_COMPLETED_LESSONS, completed).apply()
    }

    fun getCurrentLesson(): Int = prefs.getInt(KEY_CURRENT_LESSON, 2)

    fun setCurrentLesson(index: Int) {
        prefs.edit().putInt(KEY_CURRENT_LESSON, index).apply()
    }

    fun isLessonCompleted(index: Int): Boolean =
        getCompletedLessons().contains(index.toString())
}
