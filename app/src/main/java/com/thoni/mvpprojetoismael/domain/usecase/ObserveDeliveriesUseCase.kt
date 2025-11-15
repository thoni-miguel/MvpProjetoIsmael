package com.thoni.mvpprojetoismael.domain.usecase

import com.thoni.mvpprojetoismael.domain.model.Delivery
import com.thoni.mvpprojetoismael.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow

class ObserveDeliveriesUseCase(
    private val repository: DeliveryRepository
) {
    operator fun invoke(): Flow<List<Delivery>> = repository.observeDeliveries()
}
