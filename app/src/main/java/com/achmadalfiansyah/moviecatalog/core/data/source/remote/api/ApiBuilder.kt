package com.achmadalfiansyah.moviecatalog.core.data.source.remote.api



import com.achmadalfiansyah.moviecatalog.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {
    private var retrofit: Retrofit = buildRetrofit(httpClient)
    fun createService(): ApiService = retrofit.create(ApiService::class.java)

    private val httpClient: OkHttpClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            var builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor {
                    val url = it.request()
                        .url
                        .newBuilder()
                        .build()
                    val request = it.request()
                        .newBuilder()
                        .url(url)
                        .build()

                    it.proceed(request)
                }
            if (BuildConfig.DEBUG) {
                builder = builder.addInterceptor(loggingInterceptor)
            }
            return builder.build()
        }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMBD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}