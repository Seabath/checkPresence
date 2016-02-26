package com.seabath.axu614.checkPresence.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.seabath.axu614.checkPresence.R
import com.seabath.axu614.checkPresence.activity.MainActivity
import com.seabath.axu614.checkPresence.classe.StudentClass

class AddStudentDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.student_add_dialog)

        var buttonOk = findViewById(R.id.button_ok) as Button
        var buttonCancel = findViewById(R.id.button_cancel) as Button

        setTitle(R.string.action_add_student);

        buttonOk.setOnClickListener({
            val name : TextView = findViewById(R.id.student_name) as TextView
            val firstname : TextView = findViewById(R.id.student_firstname) as TextView
            val uid : TextView = findViewById(R.id.student_uid) as TextView
            MainActivity.Foo.mListStudent.add(StudentClass(name.text.toString(), firstname.text.toString(), uid.text.toString().toInt()))
            Toast.makeText(context, "Student added", Toast.LENGTH_LONG).show()
            name.text = ""
            firstname.text = ""
            uid.text = ""
        })

        buttonCancel.setOnClickListener({ this.cancel() })
    }
}