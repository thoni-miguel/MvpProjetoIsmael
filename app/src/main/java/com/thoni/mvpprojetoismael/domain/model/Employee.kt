package com.thoni.mvpprojetoismael.domain.model

data class Employee(
    val id: String,
    val name: String,
    val sector: String?,
    val role: String?,
    val shoeSize: String?,
    val uniformSize: String?,
    val active: Boolean,
    val synced: Boolean,
    val updatedAt: Long
)
