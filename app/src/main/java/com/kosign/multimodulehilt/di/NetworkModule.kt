//package com.kosign.multimodulehilt.di
//
//import android.util.Log
//import com.kosign.multimodulehilt.data.ProdDataSource
//import com.kosign.multimodulehilt.data.ProdDataSourceImpl
//import com.kosign.multimodulehilt.data.ProdRepoImpl
//import com.kosign.multimodulehilt.data.ProdRepository
//import com.kosign.multimodulehilt.data.api.UrlInterceptor
//import com.kosign.multimodulehilt.data.api.UrlInterceptorHolder
//import com.kosign.multimodulehilt.util.ENABLE_SSL_PINNING
//import com.kosign.multimodulehilt.util.FlowCallAdapterFactory
//import com.kosign.multimodulehilt.util.WEATHER_API_BASE_URL
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//open class NetworkModule {
//
//    open fun getUrlInterceptor(): Interceptor {
//        return UrlInterceptor()
//    }
//
//    @Provides
//    @Singleton
//    fun provideUrlInterceptorHolder(): UrlInterceptorHolder {
//        val urlInterceptor = getUrlInterceptor()
////        if (urlInterceptor is DebugUrlInterceptor) {
////            urlInterceptor.certificatePinner = getCertificatePinner()
////        }
//        return UrlInterceptorHolder(urlInterceptor)
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(urlInterceptorHolder: UrlInterceptorHolder): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        return OkHttpClient
//            .Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor(urlInterceptorHolder.urlInterceptor)
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(client: OkHttpClient): Retrofit =
//        Retrofit
//            .Builder()
//            .baseUrl(WEATHER_API_BASE_URL)
//            .client(client)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .addCallAdapterFactory(FlowCallAdapterFactory())
//            .build()
//
//    @Provides
//    @Singleton
//    fun provideRepository(prodDataSource: ProdDataSource): ProdRepository {
//        Log.d(">>>", "Creating repository :: $prodDataSource")
//        return ProdRepoImpl(prodDataSource)
//    }
//
//    @Provides
//    @Singleton
//    fun provideDataSource(retrofit: Retrofit): ProdDataSource {
//        Log.d(">>>", "Creating data source :: ")
//        return ProdDataSourceImpl(retrofit)
//    }
//
//}