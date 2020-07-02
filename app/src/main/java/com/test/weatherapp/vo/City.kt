package com.test.weatherapp.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val coord: Coordinates
)