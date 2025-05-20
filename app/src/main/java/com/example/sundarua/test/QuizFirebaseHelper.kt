package com.example.sundarua.test

import com.example.sundarua.model.QuizModel
import com.google.firebase.database.FirebaseDatabase

object QuizFirebaseHelper {

    data class Result(val success: Boolean, val data: List<QuizModel> = emptyList())

    fun getQuizList(callback: (Result) -> Unit) {
        val database = FirebaseDatabase.getInstance("https://sundarua-id-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val reference = database.reference.child("quizzes")

        reference.get().addOnSuccessListener { snapshot ->
            val quizList = mutableListOf<QuizModel>()
            if (snapshot.exists()) {
                for (data in snapshot.children) {
                    val quiz = data.getValue(QuizModel::class.java)
                    if (quiz != null) {
                        quizList.add(quiz)
                    }
                }
                callback(Result(true, quizList))
            } else {
                callback(Result(true, emptyList()))
            }
        }.addOnFailureListener {
            callback(Result(false))
        }
    }
}