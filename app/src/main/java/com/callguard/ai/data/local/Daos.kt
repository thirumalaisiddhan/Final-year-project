package com.callguard.ai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CallEventDao {
    @Query("SELECT * FROM call_events ORDER BY timestamp DESC")
    fun getAllCalls(): Flow<List<CallEventEntity>>

    @Query("SELECT * FROM call_events WHERE id = :id LIMIT 1")
    suspend fun getCallById(id: String): CallEventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCall(call: CallEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(calls: List<CallEventEntity>)

    @Update
    suspend fun updateCall(call: CallEventEntity)

    @Query("DELETE FROM call_events")
    suspend fun clearAll()
}

@Dao
interface TrustedCallerDao {
    @Query("SELECT * FROM trusted_callers ORDER BY name ASC")
    fun getAllTrusted(): Flow<List<TrustedCallerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trusted: TrustedCallerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<TrustedCallerEntity>)

    @Update
    suspend fun update(trusted: TrustedCallerEntity)

    @Query("DELETE FROM trusted_callers WHERE id = :id")
    suspend fun deleteById(id: String)
}

@Dao
interface ServiceContextDao {
    @Query("SELECT * FROM service_context ORDER BY organizationName ASC")
    fun getAllContexts(): Flow<List<ServiceContextEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(context: ServiceContextEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ServiceContextEntity>)

    @Update
    suspend fun update(context: ServiceContextEntity)

    @Query("DELETE FROM service_context WHERE id = :id")
    suspend fun deleteById(id: String)
}

@Dao
interface UserFeedbackDao {
    @Query("SELECT * FROM user_feedback ORDER BY timestamp DESC")
    fun getAllFeedback(): Flow<List<UserFeedbackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedback: UserFeedbackEntity)
}

@Dao
interface AppSettingsDao {
    @Query("SELECT * FROM app_settings WHERE id = 1 LIMIT 1")
    fun getSettings(): Flow<AppSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: AppSettingsEntity)
}
