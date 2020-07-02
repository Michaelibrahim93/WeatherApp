package com.test.weatherapp.dataaccess.storage

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.test.weatherapp.dataaccess.storage.dao.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object StorageModule {
    @Provides @Singleton
    fun provideDatabase(application: Application): WeatherDatabase {
        return Room
            .databaseBuilder(application, WeatherDatabase::class.java, "weather_db")
            .build()
    }

    @Provides
    fun provideCityDao(database: WeatherDatabase): CityDao {
        return database.cityDao()
    }

    @Provides @Singleton
    fun provideSharedPrefs(application: Application): SharedPreferences{
        return application.getSharedPreferences("weather_shared", Application.MODE_PRIVATE)
    }
}