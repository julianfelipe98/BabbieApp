package com.app.record.records.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.model.network.records.Record
import com.app.base.model.network.records.RecordType
import com.app.base.utils.NetworkApiStatus
import com.app.base.utils.Utils
import com.app.record.repository.RecordRepository
import kotlinx.coroutines.launch

class RecordListViewModel @ViewModelInject constructor(
    private val recordRepository: RecordRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _records = MutableLiveData<List<Record>>()
    val records: LiveData<List<Record>> = _records

    private val _finishActivity = MutableLiveData<Boolean>()
    val finishActivity: LiveData<Boolean> = _finishActivity

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _recordType = MutableLiveData<RecordType>()
    val recordType: LiveData<RecordType> = _recordType

    fun createRecord(record: Record, idPet: String) {

        record.start_date = "${record._start_date}"
        record.next_date = "${record._next_date}"
        record.pet_id = idPet
        _recordType.value?.let {
            record.record_type_id = it.id
        }

        viewModelScope.launch {
            try {
                Log.e(
                    "create",
                    recordRepository.createRecord(Utils.recordToRecordService(record)).toString()
                )
                _finishActivity.value = true
            } catch (t: Throwable) {
                _finishActivity.value = false
            }
        }
    }

    fun updateRecord(record: Record) {

        record.start_date = "${record._start_date}"
        record.next_date = "${record._next_date}"
        record.record_type_id = record.recordType.id
        record.pet_id = record.pet.id

        viewModelScope.launch {
            try {
                recordRepository.updateRecord(record, record.id)
                _finishActivity.value = true
            } catch (t: Throwable) {
                _finishActivity.value = false
            }
        }
    }

    fun getType(tag: String) {
        viewModelScope.launch {
            try {
                val data = recordRepository.getRecordType(tag)
                _recordType.value = data
            } catch (t: Throwable) {
                Log.e("RecordService", "Error")
            }
        }
    }

    fun getRecords(idPet: String, recordTag: String) {

        viewModelScope.launch {
            try {
                _status.value = NetworkApiStatus.LOADING
                val data = recordRepository.getRecord(idPet, recordTag)
                _records.value = data
                _status.value = NetworkApiStatus.DONE
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                _records.value = ArrayList()
            }
        }
    }

}