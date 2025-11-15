package com.thoni.mvpprojetoismael.ui.delivery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliverySection(
    state: DeliveryListUiState,
    snackbarHostState: SnackbarHostState,
    onSelectEmployee: (String) -> Unit,
    onSelectEpiType: (String) -> Unit,
    onRegisterDelivery: () -> Unit,
    onErrorShown: () -> Unit,
    onSuccessMessageShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    var employeeExpanded by remember { mutableStateOf(false) }
    var epiExpanded by remember { mutableStateOf(false) }
    val selectedEmployee = state.employees.firstOrNull { it.id == state.selectedEmployeeId }
    val selectedEpiType = state.epiTypes.firstOrNull { it.id == state.selectedEpiTypeId }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    LaunchedEffect(state.errorMessage) {
        val error = state.errorMessage
        if (error != null) {
            snackbarHostState.showSnackbar(
                message = error,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            onErrorShown()
        }
    }

    LaunchedEffect(state.successMessage) {
        val message = state.successMessage
        if (message != null) {
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            onSuccessMessageShown()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Entregas",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        if (state.employees.isEmpty()) {
            Text(
                text = "Cadastre funcionários antes de registrar entregas.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (state.epiTypes.isEmpty()) {
            Text(
                text = "Cadastre tipos de EPI antes de registrar entregas.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        ExposedDropdownMenuBox(
            expanded = employeeExpanded,
            onExpandedChange = { employeeExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                value = selectedEmployee?.name ?: "Selecione um funcionário",
                onValueChange = {},
                label = { Text("Funcionário") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = employeeExpanded) },
                enabled = state.employees.isNotEmpty()
            )
            ExposedDropdownMenu(
                expanded = employeeExpanded,
                onDismissRequest = { employeeExpanded = false }
            ) {
                state.employees.forEach { employee ->
                    DropdownMenuItem(
                        text = { Text(employee.name) },
                        onClick = {
                            onSelectEmployee(employee.id)
                            employeeExpanded = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = epiExpanded,
            onExpandedChange = { epiExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                value = selectedEpiType?.name ?: "Selecione um EPI",
                onValueChange = {},
                label = { Text("EPI") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = epiExpanded) },
                enabled = state.epiTypes.isNotEmpty()
            )
            ExposedDropdownMenu(
                expanded = epiExpanded,
                onDismissRequest = { epiExpanded = false }
            ) {
                state.epiTypes.forEach { epiType ->
                    DropdownMenuItem(
                        text = { Text(epiType.name) },
                        onClick = {
                            onSelectEpiType(epiType.id)
                            epiExpanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = onRegisterDelivery,
            enabled = !state.isSaving && selectedEmployee != null && selectedEpiType != null,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Registrar entrega")
        }

        if (state.isSaving) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (state.isLoading && state.deliveries.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (state.deliveries.isEmpty()) {
            Text(
                text = "Nenhuma entrega registrada ainda.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.deliveries.forEach { delivery ->
                    DeliveryCard(
                        delivery = delivery,
                        dateFormatter = dateFormatter
                    )
                }
            }
        }
    }
}

@Composable
private fun DeliveryCard(
    delivery: DeliveryUiItem,
    dateFormatter: SimpleDateFormat,
    modifier: Modifier = Modifier
) {
    val deliveredAt = remember(delivery.deliveredAt) {
        dateFormatter.format(Date(delivery.deliveredAt))
    }
    val dueAt = remember(delivery.dueAt) {
        delivery.dueAt?.let { dateFormatter.format(Date(it)) }
    }

    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = delivery.employeeName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "EPI: ${delivery.epiTypeName}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Entregue em: $deliveredAt",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = dueAt?.let { "Vence em: $it" } ?: "Sem vencimento calculado",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = if (delivery.synced) "Sincronizado" else "Pendente",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
