package com.app.record.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.base.model.network.records.Record
import com.app.record.R
import com.app.record.databinding.FragmentRecordGeneralBinding
import com.app.record.records.adapters.RecordGeneralAdapter
import com.app.record.records.viewModel.RecordListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecordGeneralFragment : Fragment() {

    private lateinit var binding: FragmentRecordGeneralBinding
    private val viewModel: RecordListViewModel by viewModels()
    private var tagRecord = ""
    private var idPet = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_record_general, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setRecyclerView()
        initViews()
        createRecord()
        initObserverRecords()
        return binding.root
    }

    private fun createRecord() {
        binding.fab.setOnClickListener {
            initDialog()
        }
    }

    private fun initViews() {
        idPet = RecordListFragmentArgs.fromBundle(requireArguments()).idPet
        tagRecord = RecordListFragmentArgs.fromBundle(requireArguments()).recordTag
        viewModel.getRecords(idPet, tagRecord)
    }

    private fun setRecyclerView() {
        binding.recordList.setItemAnimator(null);
        binding.recordList.adapter =
            RecordGeneralAdapter()

        binding.refreshView.setOnRefreshListener {
            viewModel.getRecords(idPet, tagRecord)
            binding.refreshView.setRefreshing(false);
        }
    }

    private fun initDialog(isEdit: Boolean = false, record: Record = Record()) {
        val transaction = childFragmentManager.beginTransaction()
        val previous = childFragmentManager.findFragmentByTag(RecordDialog.TAG)
        if (previous != null) {
            transaction.remove(previous)
        }
        transaction.addToBackStack(null)
        val dialogFragment = RecordDialog.newInstance(requireActivity())
        dialogFragment.show(transaction, RecordDialog.TAG)

        if (isEdit) {
            dialogFragment.editData(tagRecord, record, viewModel)
        } else {
            dialogFragment.addData(idPet, tagRecord, viewModel)
        }

    }

    fun initObserverRecords() {
        viewModel.records.observe(requireActivity(), Observer {
            if (it.isEmpty()) {
                binding.statusImage.visibility = View.VISIBLE
            } else {
                binding.statusImage.visibility = View.GONE
            }
        })
    }
}