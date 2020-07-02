package com.test.basemodule.utils

import kotlin.random.Random

fun<T> List<T>.selectRandom(): T {
    return get(Random.nextInt(0, size))
}