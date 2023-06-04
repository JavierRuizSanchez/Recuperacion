package com.utad.recuperacion_

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("https://dummyjson.com/users")
    fun getAllUsers(): Call<List<User>>
}
