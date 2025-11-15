package com.thoni.mvpprojetoismael.domain.usecase

import com.thoni.mvpprojetoismael.domain.model.Delivery
import com.thoni.mvpprojetoismael.domain.repository.DeliveryRepository
import com.thoni.mvpprojetoismael.domain.repository.EpiTypeRepository
import java.util.Calendar
import java.util.UUID

class RegisterDeliveryUseCase(
    private val deliveryRepository: DeliveryRepository,
    private val epiTypeRepository: EpiTypeRepository
) {
    suspend operator fun invoke(employeeId: String, epiTypeId: String): Result<Unit> {
        if (employeeId.isBlank() || epiTypeId.isBlank()) {
            return Result.failure(IllegalArgumentException("Funcionário e EPI são obrigatórios"))
        }

        return runCatching {
            val epiType = epiTypeRepository.getById(epiTypeId)
                ?: throw IllegalArgumentException("Tipo de EPI inválido")
            val now = System.currentTimeMillis()
            val dueAt = calculateDueDate(now, epiType.monthsValidity)
            val delivery = Delivery(
                id = UUID.randomUUID().toString(),
                employeeId = employeeId,
                epiTypeId = epiTypeId,
                deliveredAt = now,
                dueAt = dueAt,
                confirmMethod = null,
                photoPath = null,
                synced = false,
                updatedAt = now
            )
            deliveryRepository.addOrUpdate(delivery)
        }
    }

    private fun calculateDueDate(deliveredAt: Long, monthsValidity: Int): Long? {
        if (monthsValidity <= 0) return null
        val calendar = Calendar.getInstance().apply {
            timeInMillis = deliveredAt
            add(Calendar.MONTH, monthsValidity)
        }
        return calendar.timeInMillis
    }
}
