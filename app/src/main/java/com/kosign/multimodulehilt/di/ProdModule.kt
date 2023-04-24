//package com.kosign.multimodulehilt.di
//
//import com.kosign.multimodulehilt.data.ProdDataSource
//import com.kosign.multimodulehilt.data.ProdDataSourceImpl
//import com.kosign.multimodulehilt.data.ProdRepoImpl
//import com.kosign.multimodulehilt.data.ProdRepository
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//object ProdModule {
//
//    @Provides
//    fun provideRepository(prodDataSource: ProdDataSource): ProdRepository = ProdRepoImpl(prodDataSource)
//
//    @Provides
//    fun provideDataSource():ProdDataSource = ProdDataSourceImpl()
//}