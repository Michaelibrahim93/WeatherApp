package com.test.weatherapp.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.basemodule.utils.CalendarUtils
import java.util.*

//TODO:: use foreign key
@Entity
class CityForecast constructor(
    @PrimaryKey
    val id: Long,//city_id
    val city: City,
    val forecastList: List<WeatherForecast>? = null,
    val lastForecastUpdate: Long? = null,//time in unix
    var isBookMarked: Boolean = false
){

    fun todayWeather(): WeatherForecast? {
        val calendar = Calendar.getInstance()
        return forecastList?.find {
            val itrCal = CalendarUtils.unixToCalendar(it.unixTime)
            CalendarUtils.same(CalendarUtils.CalendarUnit.DAY, calendar, itrCal)
        }
    }

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
            return CityForecast(city.id, city, forecastList, lastForecastUpdate, isBookMarked)
        }
    }
}