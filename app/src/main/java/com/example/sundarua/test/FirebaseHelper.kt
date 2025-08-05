package com.example.sundarua.test

import android.annotation.SuppressLint
import com.example.sundarua.adapter.AksaraAdapter
import com.example.sundarua.model.AksaraModel
import com.example.sundarua.model.QuizModel
import com.google.firebase.database.*

object FirebaseHelper {

    private val database = FirebaseDatabase.getInstance("https://sundarua-id-default-rtdb.asia-southeast1.firebasedatabase.app/")

    /**
     * Ambil data aksara dari Firebase Realtime Database
     */
    fun getAksaraData(
        nodeName: String,
        list: ArrayList<AksaraModel>,
        adapter: AksaraAdapter,
        callback: (Boolean) -> Unit
    ) {
        val ref = database.reference.child(nodeName)
        ref.keepSynced(true)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (data in snapshot.children) {
                    val item = data.getValue(AksaraModel::class.java)
                    item?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged()
                callback(true)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }

    /**
     * Ambil daftar kuis dari Firebase Realtime Database
     */
    fun getQuizList(callback: (Result) -> Unit) {
        val reference = database.reference.child("quizzes")

        reference.get().addOnSuccessListener { snapshot ->
            val quizList = mutableListOf<QuizModel>()
            if (snapshot.exists()) {
                for (data in snapshot.children) {
                    val id = data.child("id").getValue(String::class.java) ?: ""
                    val title = data.child("title").getValue(String::class.java) ?: ""
                    val subtitle = data.child("subtitle").getValue(String::class.java) ?: ""
                    val time = data.child("time").getValue(String::class.java) ?: ""
                    val questionList = mutableListOf<com.example.sundarua.model.QuestionModel>()

                    val questionsSnapshot = data.child("questionList")
                    for (q in questionsSnapshot.children) {
                        val questionText = q.child("question").getValue(String::class.java) ?: ""
                        val questionImageUrl = q.child("questionImageUrl").getValue(String::class.java) ?: ""
                        val isImageQuestion = q.child("isImageQuestion").getValue(Boolean::class.java) ?: false
                        val correct = q.child("correct").getValue(String::class.java) ?: ""
                        val options = q.child("options").children.mapNotNull { it.getValue(String::class.java) }

                        questionList.add(
                            com.example.sundarua.model.QuestionModel(
                                question = questionText,
                                questionImageUrl = questionImageUrl,
                                isImageQuestion = isImageQuestion,
                                options = options,
                                correct = correct
                            )
                        )
                    }

                    val quiz = QuizModel(
                        id = id,
                        title = title,
                        subtitle = subtitle,
                        time = time,
                        questionList = questionList
                    )

                    quizList.add(quiz)
                }

                callback(Result(true, quizList))
            } else {
                callback(Result(true, emptyList()))
            }
        }.addOnFailureListener {
            callback(Result(false))
        }
    }

    data class Result(val success: Boolean, val data: List<QuizModel> = emptyList())
}