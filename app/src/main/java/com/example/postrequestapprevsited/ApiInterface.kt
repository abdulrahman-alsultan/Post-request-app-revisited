package com.example.postrequestapprevsited

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @GET("test/")
    fun getData(): Call<List<MyDataItem>>

    @POST("test/")
    fun addUser(@Body userData: MyDataItem): Call<MyData>

    @DELETE("test/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>

    @PUT("test/{id}")
    fun updateUser(@Path("id") id:Int, @Body userData: MyDataItem): Call<MyData>

}