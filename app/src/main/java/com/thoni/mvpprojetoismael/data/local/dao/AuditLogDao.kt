package com.thoni.mvpprojetoismael.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.thoni.mvpprojetoismael.data.local.entity.AuditLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AuditLogDao {
    @Query("SELECT * FROM audit_log ORDER BY timestamp DESC")
    fun observeLogs(): Flow<List<AuditLogEntity>>

    @Upsert
    suspend fun upsert(entry: AuditLogEntity)
}
