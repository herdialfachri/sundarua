package com.example.sundarua.test

import android.annotation.SuppressLint
import com.example.sundarua.model.AksaraModel
import com.example.sundarua.adapter.AksaraAdapter
import com.google.firebase.database.*

object FirebaseHelper {

    private val database = FirebaseDatabase.getInstance("https://sundarua-id-default-rtdb.asia-southeast1.firebasedatabase.app/")

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
}