package com.thoni.mvpprojetoismael.domain.model

data class EpiType(
    val id: String,
    val name: String,
    val monthsValidity: Int,
    val synced: Boolean,
    val updatedAt: Long
)
