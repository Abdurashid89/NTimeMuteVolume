package net

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    //http://api.aladhan.com/v1/calendar?method=2&month=12&year=2020&school=1&latitude=
// 41.300398700358386&longitude=69.24361426456768"
    @GET("calendar?method=2&month=12&year=2020&school=1&latitude=41.300398700358386&longitude=69.24361426456768")
    fun getAll(): Call<JsonObject>


    @GET("calendar?method=2&month=12&year=2020&school=1&latitude=41.300398700358386&longitude=69.24361426456768")
    fun getData() : Call<Any>

    @GET("calendar")
    fun getMonthById(@Query("month") month: Int, @Query("year") year : Int,
                     @Query("latitude") ltd : Double, @Query("longitude") longitude : Double) : Single<JsonObject>

}