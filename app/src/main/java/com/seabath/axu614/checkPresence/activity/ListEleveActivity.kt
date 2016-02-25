package com.seabath.axu614.checkPresence.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.seabath.axu614.checkPresence.R
import com.seabath.axu614.checkPresence.adapter.EleveListAdapter
import com.seabath.axu614.checkPresence.classe.EleveClass
import kotlinx.android.synthetic.main.unicorn_list_activity.*
import java.util.*

class ListEleveActivity : AppCompatActivity() {

    val ADDRESS = "https://cotelette.firebaseio.com"
    val TABLE_LICORNE = "licorne"

    var mListDisplayed : ListView? = null
    var mListEleve: ArrayList<EleveClass> = ArrayList()
    var mAdapter : EleveListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unicorn_list_activity)


        val blo = this

        mListDisplayed = list_unicorn as ListView

        val fireBase: Firebase = Firebase(ADDRESS)

        fireBase.child(TABLE_LICORNE).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: FirebaseError?) {
                Log.e("ERROR_DATABASE", "Error with with reading database: " + error!!.message)
            }

            override fun onDataChange(data: DataSnapshot?) {
                for (child in data!!.children){
                    val newUnicorn = child.getValue(EleveClass::class.java)
                    mListEleve.add(newUnicorn)
                }
                mAdapter = EleveListAdapter(blo, mListEleve)
                (mListDisplayed as ListView).adapter = mAdapter
            }

        })

        mAdapter = EleveListAdapter(this, mListEleve)
        (mListDisplayed as ListView).adapter = mAdapter
    }
}
