package com.example.postrequestapprevsited

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class UpdateDelete : AppCompatActivity() {

    lateinit var userId: TextView
    lateinit var userName: EditText
    lateinit var userLocation: EditText
    lateinit var delete: Button
    lateinit var update: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        userId = findViewById(R.id.tv_user_id)
        userName = findViewById(R.id.ed_name)
        userLocation = findViewById(R.id.ed_location)
        delete = findViewById(R.id.btn_delete)
        update = findViewById(R.id.btn_update)

        val id = intent.getIntExtra("id", -1)
        var name = intent.getStringExtra("name")
        var location = intent.getStringExtra("location")

        userId.text = "User ID: $id"
        userName.hint = name
        userLocation.hint = location



        delete.setOnClickListener {
            try {
                val retrofitBuilder = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://dojo-recipes.herokuapp.com/")
                        .build().create(ApiInterface::class.java)

                val retrofitResult = retrofitBuilder.deleteUser(id)
                retrofitResult.enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        Toast.makeText(this@UpdateDelete, "Delete successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UpdateDelete, MainActivity::class.java))
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(this@UpdateDelete, "Error: $t", Toast.LENGTH_LONG).show()
                    }
                })
            }catch (e: Exception){
                Toast.makeText(this, "Error: $e", Toast.LENGTH_LONG).show()
            }
        }

        update.setOnClickListener {
            if(userName.text.isNotEmpty() || userLocation.text.isNotEmpty()){
                name = if(userName.text.isNotEmpty())
                    userName.text.toString()
                else
                    name

                location = if(userLocation.text.isNotEmpty())
                    userLocation.text.toString()
                else
                    location

                try{
                    val retrofitBuilder = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://dojo-recipes.herokuapp.com/")
                            .build().create(ApiInterface::class.java)

                    val retrofitResult = retrofitBuilder.updateUser(id, MyDataItem(name.toString(), location.toString(), id))

                    retrofitResult.enqueue(object : Callback<MyData?> {
                        override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                            Toast.makeText(this@UpdateDelete, "Update successfully", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@UpdateDelete, MainActivity::class.java))
                        }

                        override fun onFailure(call: Call<MyData?>, t: Throwable) {
                            Toast.makeText(this@UpdateDelete, "onFailure: $t", Toast.LENGTH_LONG).show()
                            Log.d("onFailure", t.toString())
                        }
                    })
                }catch (e: Exception){
                    Toast.makeText(this, "Error: $e", Toast.LENGTH_LONG).show()
                    Log.d("catch", e.toString())
                }
            }
        }


    }
}