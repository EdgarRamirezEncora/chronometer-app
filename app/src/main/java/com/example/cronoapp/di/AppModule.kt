package com.example.cronoapp.di

import android.content.Context
import androidx.room.Room
import com.example.cronoapp.data.ChronometerDatabase
import com.example.cronoapp.data.dao.ChronometerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): ChronometerDatabase {
        return Room
            .databaseBuilder(
                context,
                ChronometerDatabase::class.java,
                "chronometerDatabase"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesChronometerDao(chronometerDatabase: ChronometerDatabase): ChronometerDao {
        return chronometerDatabase.chronometerDao()
    }

}