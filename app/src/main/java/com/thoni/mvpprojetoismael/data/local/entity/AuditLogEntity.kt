package com.thoni.mvpprojetoismael.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_log")
data class AuditLogEntity(
    @PrimaryKey val id: String,
    val action: String,
    val entity: String,
    val entityId: String,
    val timestamp: Long,
    val payload: String?,
    val synced: Boolean
)
