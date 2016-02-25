package com.seabath.axu614.checkPresence.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.firebase.client.Firebase
import com.seabath.axu614.checkPresence.R
import com.seabath.axu614.checkPresence.classe.EleveClass
import com.seabath.axu614.checkPresence.handler.CustomAuthResultHandler

class AddUnicornDialog(context: Context) : Dialog(context) {

    val ADDRESS = "https://cotelette.firebaseio.com"
    val TABLE_LICORNE = "licorne"

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.unicorn_add_dialog)

        var buttonOk = findViewById(R.id.button_ok) as Button
        var buttonCancel = findViewById(R.id.button_cancel) as Button

        setTitle(R.string.action_add_unicorn);

        buttonOk.setOnClickListener({
            val fireBase: Firebase = Firebase(ADDRESS)
            val name = (findViewById(R.id.unicorn_name) as EditText).text.toString()
            val location = (findViewById(R.id.unicorn_location) as EditText).text.toString()
            fireBase.authWithCustomToken("RjrxBaP4K5lSyXpTfuFgwTn4748of2zjiOOM2mTp", CustomAuthResultHandler())
            fireBase.child(TABLE_LICORNE).setValue(EleveClass(name, location))
            this.cancel()
        })

        buttonCancel.setOnClickListener({ this.cancel() })
    }
}