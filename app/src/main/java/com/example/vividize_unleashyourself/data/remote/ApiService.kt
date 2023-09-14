package com.example.vividize_unleashyourself.data.remote

import com.example.vividize_unleashyourself.data.model.Quote
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://retoolapi.dev/rL3BT2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface QuotesApiService {
    @GET("quote/")
    suspend fun getQuote(
//        @Query("") id: String
    ): List<Quote>


}

object QuotesApi {

    val retrofitService: QuotesApiService by lazy { retrofit.create(QuotesApiService::class.java) }
}