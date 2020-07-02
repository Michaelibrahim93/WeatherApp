package com.test.weatherapp.vo

import androidx.room.Entity

//TODO:: use foreign key
@Entity
class CityForecast constructor(
    id: Long,//city_id
    name: String,
    state: String,
    country: String,
    coord: Coordinates,
    val weatherForecast: WeatherForecast? = null,
    val lastForecastUpdate: Long? = null,//time in unix
    var isBookMarked: Boolean = false
): City(id, name, state, country, coord) {
    companion object {
        fun create(city: City): CityForecast {
            return CityForecast(city.id, city.name, city.state, city.country, city.coord
                , null, null, false)
        }
    }
}