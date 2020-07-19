package com.oluwafemi.stickit

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class StickyNoteActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sharedPref = getSharedPreferences(getString(R.string.file_key), Context.MODE_PRIVATE) ?: return

        val presentNote = sharedPref.getString(getString(R.string.our_note), "")

        //Check for the intent filter action
        if (intent.action == Intent.ACTION_PROCESS_TEXT){

            //Get the text processed
            val interStick = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT) ?: ""

            if(presentNote.isNullOrEmpty()){
                with(sharedPref.edit()){
                    putString(getString(R.string.our_note), interStick)
                    apply()
                }
            } else {
                with(sharedPref.edit()){
                    putString(getString(R.string.our_note), "$presentNote\n\n$interStick")
                    apply()
                }
            }
            Toast.makeText(this, "Clipped: $interStick", Toast.LENGTH_SHORT).show()
        }
    }
}