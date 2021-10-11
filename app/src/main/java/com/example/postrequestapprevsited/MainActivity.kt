package com.example.postrequestapprevsited

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {


    lateinit var fab : FloatingActionButton
    val list = mutableListOf<MyDataItem>()
    lateinit var grid: GridView
    lateinit var adapter: NamesGridView
    lateinit var display: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)
        grid = findViewById(R.id.gridView)
        adapter = NamesGridView(list, this)
        grid.adapter = adapter
//        display = findViewById<TextView>(R.id.display)




        fab.setOnClickListener {
            startActivity(Intent(this, AddUser::class.java))
        }

        CoroutineScope(IO).launch {
            getMyData()
        }
    }

    suspend fun getMyData(){
        try {
            val retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://dojo-recipes.herokuapp.com/")
                    .build()
                    .create(ApiInterface::class.java)

            val retrofitData = retrofitBuilder.getData()

            retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
                override fun onResponse(
                        call: Call<List<MyDataItem>?>,
                        response: Response<List<MyDataItem>?>
                ) {
                    val str = StringBuilder()
                    for (n in response.body()!!){
                        list.add(MyDataItem(n.name, n.location, n.pk))
                    }
                    update()
//                display.text = str
                }

                override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: $t", Toast.LENGTH_LONG).show()
                }
            })

        }catch (e: Exception){

        }
    }

    fun update(){
        adapter.notifyDataSetChanged()
    }
}