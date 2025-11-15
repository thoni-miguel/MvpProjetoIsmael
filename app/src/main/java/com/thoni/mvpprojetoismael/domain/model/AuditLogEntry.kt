package com.thoni.mvpprojetoismael.domain.model

data class AuditLogEntry(
    val id: String,
    val action: String,
    val entity: String,
    val entityId: String,
    val timestamp: Long,
    val payload: String?,
    val synced: Boolean
)
