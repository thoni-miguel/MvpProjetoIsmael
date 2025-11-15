package com.thoni.mvpprojetoismael.domain.repository

import com.thoni.mvpprojetoismael.domain.model.Delivery
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {
    fun observeDeliveries(): Flow<List<Delivery>>
    suspend fun addOrUpdate(delivery: Delivery)
}
