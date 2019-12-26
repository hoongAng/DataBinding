package com.example.databinding

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myContact = Contact("Ang", "0138888898")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // display layout
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //assign attribute of local variable to UI variable
        binding.contact = myContact

        buttonUpdate.setOnClickListener{
            binding.apply {
                contact?.name = "See"
                contact?.phone = "011123123"
                invalidateAll()
            }
        }

        //Create an event handler for buttonSend
        buttonSend.setOnClickListener{
            sendMessage()
        }
    }

    private fun sendMessage() {
        //create an explicit intent for the second activity
        val intent = Intent(this,SecondActivity::class.java)

        //Prepare extra
        val message = editTextMessage.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        //start an activity with no return value
        //startActivity(intent)
        //start an activity with return value
        startActivityForResult(intent,REQUEST_REPLY)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_REPLY){
            if(resultCode == Activity.RESULT_OK){
                val reply = data?.getStringExtra(MainActivity.EXTRA_REPLY)
                textViewReply.text = String.format("%s : %s",
                    getString(R.string.reply),reply)
            }else{
                textViewReply.text = String.format("%s : %s",
                    getString(R.string.reply),"No reply")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        const val EXTRA_MESSAGE = "com.example.databinding.MESSAGE"
        const val EXTRA_REPLY = "com.example.databinding.REPLY"
        const val REQUEST_REPLY = 1
    }
}
