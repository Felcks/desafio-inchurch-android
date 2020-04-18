package matheusfelipe.desafio.inchurch.core.api

import android.content.Context
import android.net.ConnectivityManager
import matheusfelipe.desafio.inchurch.core.App
import matheusfelipe.desafio.inchurch.core.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
            .addInterceptor(NetworkConnectionInterceptor(App.instance))


        val client = clientBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()

    }
}

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val mContext: Context = context

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

}