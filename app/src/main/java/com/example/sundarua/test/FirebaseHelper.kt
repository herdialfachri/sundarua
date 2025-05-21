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

    data class Result(val success: Boolean, val data: List<QuizModel> = emptyList())
}