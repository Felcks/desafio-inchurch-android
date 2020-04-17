package matheusfelipe.desafio.inchurch.core.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RestApi {

    private val retrofit: Retrofit
    fun getRetrofit() = retrofit

    init {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)


        val client = clientBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()

    }
}