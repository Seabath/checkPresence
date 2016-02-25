package com.seabath.axu614.checkPresence.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.seabath.axu614.checkPresence.R
import com.seabath.axu614.checkPresence.adapter.StudentListAdapter

class ListStudentActivity : AppCompatActivity() {

    var mListDisplayed : ListView? = null
    var mAdapter : StudentListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_list_activity)

        val listStudent = MainActivity.Foo.mListStudent

        mListDisplayed = findViewById(R.id.list_student) as ListView

        mAdapter = StudentListAdapter(this, listStudent)
        (mListDisplayed as ListView).adapter = mAdapter
    }
}
