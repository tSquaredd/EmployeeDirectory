package com.tsquaredapps.employeedirectory.directory

import android.util.MalformedJsonException
import com.tsquaredapps.employeedirectory.common.BaseCoroutineViewModelTest
import com.tsquaredapps.employeedirectory.directory.DirectoryState.*
import com.tsquaredapps.employeedirectory.directory.adapter.LetterHeader
import com.tsquaredapps.employeedirectory.ext.assertStateOrder
import com.tsquaredapps.employeedirectory.model.Employee
import com.tsquaredapps.employeedirectory.repository.EmployeeApi
import com.tsquaredapps.employeedirectory.repository.Failure
import com.tsquaredapps.employeedirectory.repository.Success
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DirectoryViewModelTest : BaseCoroutineViewModelTest<DirectoryState>() {

    private lateinit var viewModel: DirectoryViewModel
    private val employeeApi: EmployeeApi = mockk()

    // Intentionally unsorted list of employees
    private val employeeList = listOf<Employee>(
        mockk { every { fullName } returns THIRD_EMPLOYEE_NAME },
        mockk { every { fullName } returns FIRST_EMPLOYEE_NAME },
        mockk { every { fullName } returns FOURTH_EMPLOYEE_NAME },
        mockk { every { fullName } returns SECOND_EMPLOYEE_NAME }
    )

    @BeforeEach
    fun beforeEach() {
        viewModel = DirectoryViewModel(employeeApi).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Test
    fun `given api returns employee list, when view model started, then set state to ShowEmployees`() {
        coEvery { employeeApi.getEmployees() } returns Success(employeeList)

        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(ShowEmployees::class)
        (stateList.first() as ShowEmployees).employees.assertSortedEmployeeList()
    }

    @Test
    fun `given api returns empty list, when view model started, then set state to ShowEmptyList`() {
        coEvery { employeeApi.getEmployees() } returns Success(emptyList())

        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is ShowEmptyList }
    }

    @Test
    fun `given api fails, when view model started, then set state to ShowError`() {
        coEvery { employeeApi.getEmployees() } returns Failure(MalformedJsonException(""))

        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is ShowError }
    }

    @Test
    fun `given api fails, when employee retries, then show loader and try again`() {
        coEvery { employeeApi.getEmployees() } returns Success(employeeList)

        viewModel.onRetryClicked()

        verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(ShowLoader::class, ShowEmployees::class)
        (stateList[1] as ShowEmployees).employees.assertSortedEmployeeList()
    }

    private fun List<Pair<LetterHeader, List<Employee>>>.assertSortedEmployeeList() {
        get(0).let { (letterHeader, employeeList) ->
            assertEquals('A', letterHeader.letter)
            assertEquals(1, employeeList.size)
            assertEquals(FIRST_EMPLOYEE_NAME, employeeList.first().fullName)
        }

        get(1).let { (letterHeader, employeeList) ->
            assertEquals('C', letterHeader.letter)
            assertEquals(2, employeeList.size)
            assertEquals(SECOND_EMPLOYEE_NAME, employeeList.first().fullName)
            assertEquals(THIRD_EMPLOYEE_NAME, employeeList[1].fullName)
        }

        get(2).let { (letterHeader, employeeList) ->
            assertEquals('Z', letterHeader.letter)
            assertEquals(1, employeeList.size)
            assertEquals(FOURTH_EMPLOYEE_NAME, employeeList.first().fullName)
        }
    }

    companion object {
        const val FIRST_EMPLOYEE_NAME = "Adam Adamski"
        const val SECOND_EMPLOYEE_NAME = "Curt Diggler"
        const val THIRD_EMPLOYEE_NAME = "Curt Ziggler"
        const val FOURTH_EMPLOYEE_NAME = "Zach Attack"
    }
}