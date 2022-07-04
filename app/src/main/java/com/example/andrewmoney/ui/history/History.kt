package com.example.andrewmoney.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.andrewmoney.MainApplication
import com.example.andrewmoney.R
import com.example.andrewmoney.databinding.FragmentHistoryBinding
import com.example.andrewmoney.databinding.FragmentVaultBinding
import com.example.andrewmoney.ui.history.adapter.HistoryAdapter
import com.example.andrewmoney.viewmodel.AppViewModel
import com.example.andrewmoney.viewmodel.VaultViewModelFactory
import kotlinx.coroutines.launch


class History : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var mainViewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        initViewModel()

        adapter = HistoryAdapter()

        updateRecyclerData()

        val layoutManager = LinearLayoutManager(context)

        binding.historyRecycler.layoutManager = layoutManager
        binding.historyRecycler.adapter = adapter

        return binding.root

    }

    private fun initViewModel() {
        val repository = (activity?.application as MainApplication).repository
        mainViewModel =
            ViewModelProvider(this, VaultViewModelFactory(repository)).get(AppViewModel::class.java)
    }

    private fun updateRecyclerData() {
        lifecycleScope.launch {
            mainViewModel.getHistory().observe(viewLifecycleOwner, Observer {
                val _wait = runCatching { it }
                _wait.onSuccess {
                    adapter.historyList = it
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }

}