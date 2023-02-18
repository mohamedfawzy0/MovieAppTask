package com.movieapptask.di


import com.movieapptask.utils.AuthInterceptor
import com.movieapptask.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(
            AuthInterceptor(
                "Bearer",
                "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMmY2ODIxOTAzZTYzNDRlYjQ3MzdlNTg4NmRhNDBiZCIsInN1YiI6IjYzZWQ0MTM5MmYyNjZiMDA4NWFmNWFmNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.28XdfKPymP3ICgW_nxwOQ5QLA-kR0lAj3sc7sbh48vM"
            )
        ).addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)


}