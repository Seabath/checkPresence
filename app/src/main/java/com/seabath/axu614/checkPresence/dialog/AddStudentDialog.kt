package com.seabath.axu614.checkPresence.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
            val name : String = (findViewById(R.id.student_name) as TextView).text.toString()
            val firstname : String = (findViewById(R.id.student_firstname) as TextView).text.toString()
            MainActivity.Foo.mListStudent.add(StudentClass(name, firstname, 1000))
        })

        buttonCancel.setOnClickListener({ this.cancel() })
    }
}