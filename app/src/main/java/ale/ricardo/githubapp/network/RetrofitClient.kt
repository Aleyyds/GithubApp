package ale.ricardo.githubapp.network

import android.util.Log
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val TAG: String? = "Ricardo-log"
    private val URL: String = "https://api.github.com/"
    private val instance: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor {
            Log.d(TAG, "HttpLoggingInterceptor: $it")
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY



        Retrofit.Builder().client(OkHttpClient.Builder()
            .addInterceptor(PublicParamsForHeader()).addInterceptor(interceptor).build()).addConverterFactory(GsonConverterFactory.create()).baseUrl(URL)
            .build()
    }

    fun <T> createApi(clazz: Class<T>): T {
        return instance.create(clazz) as T
    }
}