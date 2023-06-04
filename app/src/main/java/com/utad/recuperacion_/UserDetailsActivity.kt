package com.utad.recuperacion_

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class UserDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val fullName = intent.getStringExtra("fullName")
        val lastName = intent.getStringExtra("lastName")
        val photo = intent.getStringExtra("photo")
        val company = intent.getStringExtra("company")

        val imageViewPhoto: ImageView = findViewById(R.id.imageViewPhoto)
        val textViewFullName: TextView = findViewById(R.id.textViewFullName)
        val textViewlastName: TextView = findViewById(R.id.textViewlastName)
        val textViewCompany: TextView = findViewById(R.id.textViewCompany)

        Picasso.get().load(photo).into(imageViewPhoto)
        textViewFullName.text = fullName
        textViewlastName.text = lastName
        textViewCompany.text = company
    }
}
