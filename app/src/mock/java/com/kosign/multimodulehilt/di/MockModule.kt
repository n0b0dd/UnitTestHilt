package com.kosign.multimodulehilt.di

import android.util.Log
import com.kosign.multimodulehilt.data.*
import com.kosign.multimodulehilt.data.api.UrlInterceptorHolder
import com.kosign.multimodulehilt.data.mock.mockserver.MockScenarios
import com.kosign.multimodulehilt.data.mock.mockserver.MockServerManager
import com.kosign.multimodulehilt.util.*
import com.kosign.multimodulehilt.util.ENABLE_SSL_PINNING
import com.kosign.multimodulehilt.util.WEATHER_API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockModule {

    class MockUrlInterceptor :Interceptor {

        var mockServerManager: MockServerManager? = null

        fun enableMockServer() {
            mockServerManager?.enableApi(WEATHER_API_SEARCH_URL, MockScenarios.SUCCESS)
            mockServerManager?.enableApi(WEATHER_API_CURRENT_WEATHER_URL, MockScenarios.SUCCESS)
            mockServerManager?.enableApi(RESERVATION_API, MockScenarios.SUCCESS)

            mockServerManager?.startServer()
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            if (mockServerManager?.shouldMockApi(request.url.encodedPath) == true) {
                // Only validate the host name when you mock the API
                Log.d(">>>", "intercept: ${request.url.encodedPath}")
                val newUrl: HttpUrl = request.url.newBuilder()
                    .scheme(mockServerManager?.scheme ?: MockServerManager.HTTP_SCHEME)
                    .host(MockServerManager.HOST)
                    .port(mockServerManager?.port ?: 1)
                    .build()
                Log.d(">>>", "intercept: ${newUrl}")
                request = request.newBuilder()
                    .url(newUrl)
                    .build()
            }
            return chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideUrlInterceptorHolder( mockServerManager: MockServerManager): UrlInterceptorHolder {
        Log.d(">>>", "provideUrlInterceptorHolder: ${mockServerManager.scheme}")
        val mockUrlInterceptor = MockUrlInterceptor()
        mockUrlInterceptor.mockServerManager = mockServerManager
        mockUrlInterceptor.enableMockServer()
        return UrlInterceptorHolder(mockUrlInterceptor)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(urlInterceptorHolder: UrlInterceptorHolder): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(urlInterceptorHolder.urlInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        Log.d(">>>>", "provideRetrofit: ${client.interceptors}")
        return Retrofit
            .Builder()
            .baseUrl(WEATHER_API_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideRepository(prodDataSource: ProdDataSource): ProdRepository {
        Log.d(">>>", "Creating repository :: $prodDataSource")
        return ProdRepoImpl(prodDataSource)
    }

    @Provides
    @Singleton
    fun provideDataSource(retrofit: Retrofit): ProdDataSource{
        Log.d(">>>", "Creating data source :: ${retrofit}")
        return ProdDataSourceImpl(retrofit)
    }


    @Provides
    @Singleton
    fun provideMockServerManager() = MockServerManager()

//    @Provides
//    @Singleton
//    fun provideMockWebServer() : MockWebServer{
//        val mockWebServer = MockWebServer()
//        Thread {
//            mockWebServer.start();
//        }.start()
//
//        return mockWebServer;
//    }

}