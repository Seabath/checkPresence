package com.seabath.axu614.checkPresence.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.firebase.client.Firebase
import com.seabath.axu614.checkPresence.R
import com.seabath.axu614.checkPresence.classe.EleveClass
import com.seabath.axu614.checkPresence.dialog.AddUnicornDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.setAndroidContext(this)
        setContentView(R.layout.activity_main)

        val toolBar = findViewById(R.id.toolBar) as Toolbar
        setSupportActionBar(toolBar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        val id = item.itemId

        when (id) {
            R.id.action_settings -> {
                var uni : EleveClass? = null;
                uni?.doNullPointerException()
            }
            R.id.action_show_list -> {
                val intent = Intent(baseContext, ListEleveActivity::class.java)
                startActivity(intent)
            }
            R.id.action_add_unicorn -> {
                var blo = AddUnicornDialog(this)
                blo.show()
            }
            else -> Log.e("ERROR OPTION", "dafuk?")
        }

        return super.onOptionsItemSelected(item)
    }
}
