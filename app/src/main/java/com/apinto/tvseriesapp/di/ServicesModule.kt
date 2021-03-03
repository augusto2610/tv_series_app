package com.apinto.tvseriesapp.di

import com.apinto.tvseriesapp.BuildConfig.BASE_URL
import com.apinto.tvseriesapp.services.SubscriptionService
import com.apinto.tvseriesapp.services.TvSeriesService
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val servicesModule = module {

    factory { provideOkHttpClient() }
    single { provideRetrofit(get()).create(TvSeriesService::class.java) }

    single { SubscriptionService(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder().addInterceptor(interceptor).protocols(listOf(Protocol.HTTP_1_1)).build()
}