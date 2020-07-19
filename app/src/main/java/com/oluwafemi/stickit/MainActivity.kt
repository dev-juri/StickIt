package com.oluwafemi.stickit

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oluwafemi.stickit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Creates a shared preference file
        val sharedPref = getSharedPreferences(getString(R.string.file_key), Context.MODE_PRIVATE) ?: return

        val presentNote = sharedPref.getString(getString(R.string.our_note), "")
         //Check if there's anything in the shared preference
        if(presentNote == null){
            //Create a value for the note
            with(sharedPref.edit()) {
                putString(getString(R.string.our_note), "")
                apply()
            }
        }

        //Add a TextWatcher to the EditText
        binding.stickyNote.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //Update the sharedPref after text changes
                with(sharedPref.edit()) {
                    putString(getString(R.string.our_note), p0.toString())
                    apply()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.stickyNote.setText(presentNote)

    }
}