package com.app.record.records

import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.app.base.model.network.records.Record
import com.app.base.utils.Utils
import com.app.record.R
import com.app.record.databinding.LayoutRecordDialogBinding
import com.app.record.records.viewModel.RecordListViewModel
import java.text.SimpleDateFormat
import java.util.*


class RecordDialog : DialogFragment() {

    private lateinit var binding: LayoutRecordDialogBinding
    private lateinit var activity: LifecycleOwner
    private lateinit var viewModel: RecordListViewModel
    private var idPet = ""
    private var tagRecord = ""
    private var isEdit = false
    private var record = Record()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_record_dialog,
            container,
            false
        )
        binding.lifecycleOwner = this
        initFragment()
        initViews()

        return binding.root;
    }

    fun initFragment() {
        viewModel.getType(tagRecord)
        viewModel.recordType.observe(requireActivity(), Observer {
            if (null != it) {
                binding.textViewTitleType.text = it.name
            }
        })
        if (tagRecord == getString(R.string.medicine_tag)) {
            binding.textView3.visibility = View.GONE
            binding.dateButton2.visibility = View.GONE
            binding.datePickerEditField2.visibility = View.GONE
        }
        checkFunction()
    }

    private fun checkFunction() {
        if (!isEdit) {
            checkViews()
        } else {
            record.start_date = Utils.stringFormatToString(record.start_date)
            record.next_date = Utils.stringFormatToString(record.next_date)
            binding.record = record
            binding.confirmButton.setOnClickListener {
                viewModel.updateRecord(record)
                initObserver()
            }

        }
    }

    private fun checkViews() {
        val editTextTitle: EditText = binding.titleEditText

        binding.confirmButton.setOnClickListener {
            val textTitle: String = editTextTitle.text.toString()
            if (textTitle.trim().isEmpty()) {
                editTextTitle.error = "Campo obligatorio"
            } else {
                setDefaultDates()
                viewModel.createRecord(record, idPet)
                initObserver()
            }
        }
    }

    private fun initViews() {
        binding.record = record
        binding.dateButton.setOnClickListener {
            showDatePickerDialog(binding.datePickerEditField, true)
        }
        binding.dateButton2.setOnClickListener {
            showDatePickerDialog(binding.datePickerEditField2)
        }
    }

    private fun setDefaultDates() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDateandTime: String = sdf.format(Date())
        record.start_date = currentDateandTime
        record.next_date = currentDateandTime
        binding.datePickerEditField.setText(currentDateandTime)
        binding.datePickerEditField2.setText(currentDateandTime)
    }

    private fun initObserver() {
        viewModel.finishActivity.observe(requireActivity(), Observer { isfinished ->
            if (isfinished) {
                dismiss()
            }
        })
    }

    fun addData(idPet: String, tag: String, viewModel: RecordListViewModel) {
        this.idPet = idPet
        this.tagRecord = tag
        this.viewModel = viewModel
    }

    fun editData(tag: String, record: Record, viewModel: RecordListViewModel) {
        this.tagRecord = tag
        this.viewModel = viewModel
        this.record = record
        isEdit = true

    }

    private fun showDatePickerDialog(textViewDate: TextView, isLimit: Boolean = false) {

        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textViewDate.setText(sdf.format(cal.time))
            }
        val dpd = DatePickerDialog(
            requireContext(), dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        if (isLimit) {
            dpd.datePicker.maxDate = (cal.getTimeInMillis())
        } else {
            dpd.datePicker.minDate = (Utils.formatDate(binding.datePickerEditField.text.toString()))
        }
        dpd.show()
    }

    companion object {
        const val TAG = "TAG"
        fun newInstance(activity: LifecycleOwner): RecordDialog {
            val customDialog = RecordDialog()
            customDialog.activity = activity
            return customDialog
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = getActivity()
        if (activity is DialogInterface.OnDismissListener) {
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }
}