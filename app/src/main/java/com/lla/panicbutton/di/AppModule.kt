package com.lla.panicbutton.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.lla.panicbutton.db.PanicDatabase
import com.lla.panicbutton.util.Constants.PANIC_DATABASE_NAME
import com.lla.panicbutton.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePanicDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PanicDatabase::class.java,
        PANIC_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRecordingDao(db: PanicDatabase) = db.getRecodingDao()

    @Singleton
    @Provides
    fun provideEpisodeDao(db: PanicDatabase) = db.getEpisodeDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
}