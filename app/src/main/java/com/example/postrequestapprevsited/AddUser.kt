package com.example.postrequestapprevsited

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val btnSave = findViewById<Button>(R.id.btn_add_user)
        val btnView = findViewById<Button>(R.id.btn_display_users)
        val edName = findViewById<EditText>(R.id.ed_name)
        val edLocation = findViewById<EditText>(R.id.ed_location)

        btnSave.setOnClickListener {
            val name = edName.text
            val loc = edLocation.text
            if(name.isNotEmpty() && loc.isNotEmpty()){
                val retrofitBuilder = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://dojo-recipes.herokuapp.com/")
                        .build()
                        .create(ApiInterface::class.java)

                val retrofitData = retrofitBuilder.addUser(MyDataItem(name.toString(), loc.toString(), 1))

                if(retrofitData != null){
                    retrofitData.enqueue(object : Callback<MyData?> {
                        override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                            Toast.makeText(this@AddUser, "Added successfully", Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<MyData?>, t: Throwable) {
                            Toast.makeText(this@AddUser, "Error: $t", Toast.LENGTH_LONG).show()
                        }
                    })
                }

            }
        }

        btnView.setOnClickListener {
//            MainActivity().update()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}