package com.test.weatherapp.vo

import androidx.room.Entity

//TODO:: use foreign key
@Entity
class CityForecast constructor(
    id: Long,//city_id
    name: String,
    state: String?,
    country: String,
    coord: Coordinates,
    val forecastList: List<WeatherForecast>? = null,
    val lastForecastUpdate: Long? = null,//time in unix
    var isBookMarked: Boolean = false
): City(id, name, state, country, coord) {

    override fun equals(other: Any?): Boolean {
        if (other is City)
            return other.id == id

        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = forecastList?.hashCode() ?: 0
        result = 31 * result + (lastForecastUpdate?.hashCode() ?: 0)
        result = 31 * result + isBookMarked.hashCode()
        return result
    }

    companion object {
        fun create(city: City, forecastList: List<WeatherForecast>? = null
                   , lastForecastUpdate: Long? = null ,isBookMarked: Boolean = false): CityForecast {
            return CityForecast(city.id, city.name, city.state, city.country, city.coord
                , forecastList, lastForecastUpdate, isBookMarked)
        }
    }
}