package com.thoni.mvpprojetoismael

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thoni.mvpprojetoismael.ui.delivery.DeliveryListViewModel
import com.thoni.mvpprojetoismael.ui.delivery.DeliveryListViewModelFactory
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListViewModel
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListViewModelFactory
import com.thoni.mvpprojetoismael.ui.epitype.EpiTypeListViewModel
import com.thoni.mvpprojetoismael.ui.epitype.EpiTypeListViewModelFactory
import com.thoni.mvpprojetoismael.ui.home.HomeScreen
import com.thoni.mvpprojetoismael.ui.theme.MvpProjetoIsmaelTheme
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListScreen
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListViewModel
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListViewModelFactory

class MainActivity : ComponentActivity() {
    private val appContainer by lazy { (application as MvpProjetoIsmaelApp).appContainer }
    private val employeeViewModel: EmployeeListViewModel by viewModels {
        EmployeeListViewModelFactory(
            appContainer.observeEmployeesUseCase,
            appContainer.addEmployeeUseCase
        )
    }
    private val epiTypeViewModel: EpiTypeListViewModel by viewModels {
        EpiTypeListViewModelFactory(
            appContainer.observeEpiTypesUseCase,
            appContainer.addEpiTypeUseCase
        )
    }
    private val deliveryViewModel: DeliveryListViewModel by viewModels {
        DeliveryListViewModelFactory(
            appContainer.observeDeliveriesUseCase,
            appContainer.observeEmployeesUseCase,
            appContainer.observeEpiTypesUseCase,
            appContainer.registerDeliveryUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MvpProjetoIsmaelTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val employeeState by employeeViewModel.uiState.collectAsStateWithLifecycle()
                    val epiState by epiTypeViewModel.uiState.collectAsStateWithLifecycle()
                    val deliveryState by deliveryViewModel.uiState.collectAsStateWithLifecycle()

                    HomeScreen(
                        employeeState = employeeState,
                        epiTypeState = epiState,
                        deliveryState = deliveryState,
                        onAddEmployee = employeeViewModel::onAddEmployee,
                        onEmployeeErrorShown = employeeViewModel::onErrorShown,
                        onEmployeeInputsCleared = employeeViewModel::onInputsCleared,
                        onAddEpiType = epiTypeViewModel::onAddEpiType,
                        onEpiTypeErrorShown = epiTypeViewModel::onErrorShown,
                        onEpiTypeInputsCleared = epiTypeViewModel::onInputsCleared,
                        onSelectDeliveryEmployee = deliveryViewModel::onSelectEmployee,
                        onSelectDeliveryEpiType = deliveryViewModel::onSelectEpiType,
                        onRegisterDelivery = deliveryViewModel::onRegisterDelivery,
                        onDeliveryErrorShown = deliveryViewModel::onErrorShown,
                        onDeliverySuccessShown = deliveryViewModel::onSuccessMessageShown
                    )
                }
            }
        }
    }
}
