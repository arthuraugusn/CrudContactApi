package com.example.crudcontactapi.api

import com.example.crudcontactapi.model.Contact
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContactCall {

    @GET("contacts")
    fun getContacts(): Call<List<Contact>>

    @GET("contacts/{id}")
    fun getContactById(@Path("id")id: Long): Call<Contact>

    @POST("contacts")
    fun saveContact(@Body contact: Contact): Call<Contact>

    @DELETE("contacts/{id}")
    fun deleterContact(@Path("id")id: Long)
}