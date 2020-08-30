package com.tsquaredapps.employeedirectory.directory

import androidx.lifecycle.viewModelScope
import com.tsquaredapps.employeedirectory.common.BaseStateViewModel
import com.tsquaredapps.employeedirectory.common.DispatcherProvider
import com.tsquaredapps.employeedirectory.directory.DirectoryState.*
import com.tsquaredapps.employeedirectory.directory.adapter.LetterHeader
import com.tsquaredapps.employeedirectory.model.Employee
import com.tsquaredapps.employeedirectory.repository.EmployeeApi
import com.tsquaredapps.employeedirectory.repository.Failure
import com.tsquaredapps.employeedirectory.repository.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class DirectoryViewModel
@Inject constructor(
    private val employeeApi: EmployeeApi,
    private val dispatchers: DispatcherProvider
) : BaseStateViewModel<DirectoryState>() {

    private var employeesByLetterList = listOf<Pair<LetterHeader, List<Employee>>>()

    fun start() {
        if (employeesByLetterList.isNullOrEmpty()) {
            viewModelScope.launch(dispatchers.main()) {
                when (val result = employeeApi.getEmployees()) {
                    is Success -> handleSuccessfulApiResponse(result.data)
                    is Failure -> state.value = ShowError
                }
            }
        } else {
            state.value = ShowEmployees(employeesByLetterList)
        }
    }

    private fun handleSuccessfulApiResponse(employeeList: List<Employee>) {
        with(employeeList) {
            state.value = if (isEmpty()) ShowEmptyList
            else {
                employeesByLetterList = createEmployeeListGroupedByLetter()
                ShowEmployees(employeesByLetterList)
            }
        }
    }

    private fun List<Employee>.createEmployeeListGroupedByLetter(): List<Pair<LetterHeader, List<Employee>>> =
        asSequence()
            .sortedBy { it.fullName }
            .groupBy { it.fullName.first().toUpperCase() }
            .map { LetterHeader(it.key) to it.value }

    fun onRetryClicked() {
        state.value = ShowLoader
        viewModelScope.launch(dispatchers.default()) {
            // without delay a fast response can make it seem like no retry occurred
            delay(RETRY_DELAY)
            start()
        }
    }

    companion object {
        const val RETRY_DELAY = 1000L
    }
}

sealed class DirectoryState {
    class ShowEmployees(val employees: List<Pair<LetterHeader, List<Employee>>>) : DirectoryState()
    object ShowEmptyList : DirectoryState()
    object ShowError : DirectoryState()
    object ShowLoader : DirectoryState()
}