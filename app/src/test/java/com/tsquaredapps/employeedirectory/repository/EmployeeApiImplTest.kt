package com.tsquaredapps.employeedirectory.repository

import android.util.MalformedJsonException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class EmployeeApiImplTest {
    lateinit var api: EmployeeApiImpl
    private val employeeApiService: EmployeeApiService = mockk()

    @BeforeEach
    fun beforeEach() {
        api = EmployeeApiImpl(employeeApiService)
    }

    @Test
    fun `given api retrieves employees, then return Success`() = runBlockingTest {
        coEvery { employeeApiService.getEmployees() } returns listOf(mockk(), mockk())

        val result = api.getEmployees()

        assertTrue { result is Success }
        assertEquals(2, (result as Success).data.size)
    }

    @Test
    fun `given api retrieves no employees, then return Success with empty list`() =
        runBlockingTest {
            coEvery { employeeApiService.getEmployees() } returns emptyList()

            val result = api.getEmployees()

            assertTrue { result is Success }
            assertTrue { (result as Success).data.isEmpty() }
        }

    @Test
    fun `given api service throws exception, then return Failure`() = runBlockingTest {
        coEvery { employeeApiService.getEmployees() } throws MalformedJsonException("")

        val result = api.getEmployees()

        assertTrue { result is Failure }
    }
}