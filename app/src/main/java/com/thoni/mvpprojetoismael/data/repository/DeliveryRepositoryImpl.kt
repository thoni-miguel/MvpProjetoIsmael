package com.thoni.mvpprojetoismael.data.repository

import com.thoni.mvpprojetoismael.data.local.dao.DeliveriesDao
import com.thoni.mvpprojetoismael.domain.model.Delivery
import com.thoni.mvpprojetoismael.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeliveryRepositoryImpl(
    private val deliveriesDao: DeliveriesDao
) : DeliveryRepository {
    override fun observeDeliveries(): Flow<List<Delivery>> {
        return deliveriesDao.observeDeliveries().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addOrUpdate(delivery: Delivery) {
        deliveriesDao.upsert(delivery.toEntity())
    }
}
