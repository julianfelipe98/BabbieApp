package com.app.record.repository

import com.app.base.model.network.records.Record
import com.app.base.model.network.records.RecordServ
import com.app.base.model.network.records.RecordType
import com.app.record.network.RecordService
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val service: RecordService
) {

    suspend fun getRecord(idPet: String, tagRecord: String): List<Record> {
        return service.getRecord(idPet, tagRecord)
    }

    suspend fun createRecord(record: RecordServ): Response<JSONObject> {
        return service.createRecord(record)
    }

    suspend fun getRecordType(tagRecord: String): RecordType {
        return service.getRecordType(tagRecord)
    }

    suspend fun updateRecord(record: Record, id: String) {
        service.updateRecord(record, id).toString()
    }
}

