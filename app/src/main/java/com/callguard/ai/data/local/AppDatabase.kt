package com.callguard.ai.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CallEventEntity::class,
        TrustedCallerEntity::class,
        ServiceContextEntity::class,
        SpamPredictionEntity::class,
        UserFeedbackEntity::class,
        AppSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun callEventDao(): CallEventDao
    abstract fun trustedCallerDao(): TrustedCallerDao
    abstract fun serviceContextDao(): ServiceContextDao
    abstract fun userFeedbackDao(): UserFeedbackDao
    abstract fun appSettingsDao(): AppSettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "callguard_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
