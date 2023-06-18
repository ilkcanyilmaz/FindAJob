package com.ilkcanyilmaz.findajob.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ilkcanyilmaz.findajob.data.AuthRepository
import com.ilkcanyilmaz.findajob.data.AuthRepositoryImpl
import com.ilkcanyilmaz.findajob.data.FirestoreDataSource
import com.ilkcanyilmaz.findajob.data.FirestoreDataSourceImpl
import com.ilkcanyilmaz.findajob.data.FirestoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun providesAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providesFirestoreDataSource(): FirestoreDataSource =
        FirestoreDataSourceImpl(providesFirestore())

    @Provides
    fun providesFirestoreRepository(): FirestoreRepository =
        FirestoreRepository(providesFirestoreDataSource())


    @Provides
    fun provideAuthRepository():AuthRepository = AuthRepositoryImpl(FirebaseAuth.getInstance())


}