package com.example.movieapp.data.remote

import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Constants.API_KEY
import com.example.movieapp.utils.Constants.BASE_URL
import com.example.movieapp.viewmodel.GridMoviesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl() = Constants.BASE_URL


    @Provides
    @Singleton
    fun provideMyApi(): ServiceApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val url = originalRequest.url.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val request = originalRequest.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceApi::class.java)
    }


}


