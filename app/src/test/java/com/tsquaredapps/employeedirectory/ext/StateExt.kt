package com.tsquaredapps.employeedirectory.ext

import org.junit.jupiter.api.Assertions
import kotlin.reflect.KClass

fun <T> MutableList<T>.assertStateOrder(vararg states: KClass<out Any>) {
    states.forEachIndexed { index, clazz ->
        Assertions.assertTrue { clazz.isInstance(this[index]) }
    }
}