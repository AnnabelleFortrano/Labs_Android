package ru.mirea.lab0

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Linkbutton = findViewById<Button>(button2.id)
        val Exitbutton = findViewById<Button>(MAINbutton.id)

        Linkbutton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/fortrano")))}
        })
        Exitbutton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()}
        })
}
}
