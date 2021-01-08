package com.app.record.network

import com.app.base.model.network.records.Record
import com.app.base.model.network.records.RecordServ
import com.app.base.model.network.records.RecordType
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface RecordService {

    @GET("records/tags/pet/{petId}")
    suspend fun getRecord(
        @Path(value = "petId") petId: String,
        @Query("type") type: String
    ): List<Record>

    @POST("/records/")
    suspend fun createRecord(@Body record: RecordServ): Response<JSONObject>

    @GET("recordTypes/tag")
    suspend fun getRecordType(
        @Query("tag") type: String
    ): RecordType

    @PUT("/records/{id}")
    suspend fun updateRecord(
        @Body record: Record,
        @Path(value = "id") recordId: String
    ): Response<JSONObject>
}

