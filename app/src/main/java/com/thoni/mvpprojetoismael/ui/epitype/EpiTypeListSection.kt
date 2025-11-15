package com.thoni.mvpprojetoismael.ui.epitype

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thoni.mvpprojetoismael.domain.model.EpiType

@Composable
fun EpiTypeSection(
    state: EpiTypeListUiState,
    snackbarHostState: SnackbarHostState,
    onAddEpiType: (String, String) -> Unit,
    onErrorShown: () -> Unit,
    onInputsCleared: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (name, setName) = remember { mutableStateOf("") }
    val (monthsValidity, setMonthsValidity) = remember { mutableStateOf("") }

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

    LaunchedEffect(state.shouldClearInput) {
        if (state.shouldClearInput) {
            setName("")
            setMonthsValidity("")
            onInputsCleared()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Tipos de EPI",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = name,
            onValueChange = setName,
            label = { Text("Nome do EPI") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = monthsValidity,
            onValueChange = setMonthsValidity,
            label = { Text("Validade (meses)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onAddEpiType(name, monthsValidity) },
            enabled = name.isNotBlank() && monthsValidity.isNotBlank() && !state.isSaving,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Adicionar")
        }

        if (state.isSaving) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (state.isLoading && state.epiTypes.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            EpiTypeList(
                epiTypes = state.epiTypes,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EpiTypeList(
    epiTypes: List<EpiType>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        epiTypes.forEach { epiType ->
            EpiTypeItem(epiType)
        }
    }
}

@Composable
private fun EpiTypeItem(epiType: EpiType) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = epiType.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Validade: ${epiType.monthsValidity} meses",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = if (epiType.synced) "Sincronizado" else "Pendente",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
