package com.thoni.mvpprojetoismael.domain.usecase

import com.thoni.mvpprojetoismael.domain.model.EpiType
import com.thoni.mvpprojetoismael.domain.repository.EpiTypeRepository
import java.util.UUID

class AddEpiTypeUseCase(
    private val repository: EpiTypeRepository
) {
    suspend operator fun invoke(name: String, monthsValidity: Int?): Result<Unit> {
        if (name.isBlank()) {
            return Result.failure(IllegalArgumentException("Nome do EPI é obrigatório"))
        }

        val validity = monthsValidity ?: return Result.failure(
            IllegalArgumentException("Validade em meses é obrigatória")
        )

        if (validity <= 0) {
            return Result.failure(IllegalArgumentException("Validade deve ser maior que zero"))
        }

        val now = System.currentTimeMillis()
        val epiType = EpiType(
            id = UUID.randomUUID().toString(),
            name = name.trim(),
            monthsValidity = validity,
            synced = false,
            updatedAt = now
        )

        return runCatching {
            repository.addOrUpdate(epiType)
        }
    }
}
