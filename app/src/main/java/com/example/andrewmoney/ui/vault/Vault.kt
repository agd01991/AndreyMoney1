package com.example.andrewmoney.ui.vault

import android.os.Bundle
import android.os.DeadObjectException
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
import com.example.andrewmoney.VaultInterface
import com.example.andrewmoney.data.local.model.LocalVaultModel
import com.example.andrewmoney.databinding.FragmentVaultBinding
import com.example.andrewmoney.ui.vault.adapter.VaultAdapter
import com.example.andrewmoney.ui.vault.change.Change
import com.example.andrewmoney.viewmodel.AppViewModel
import com.example.andrewmoney.viewmodel.VaultViewModelFactory
import kotlinx.coroutines.launch

interface VaultFunctions {
    fun likeVault(name: String)
    fun dislikeVault(name: String)
    fun clickItemListener(name: String, rate: Double)
}
class Vault : Fragment() {

    private lateinit var binding: FragmentVaultBinding
    private lateinit var adapter: VaultAdapter
    private lateinit var mainViewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVaultBinding.inflate(inflater, container, false)

        initViewModel()

        val vaultInterface = requireActivity() as VaultInterface

        adapter = VaultAdapter(object : VaultFunctions {
            override fun likeVault(name: String) {
                lifecycleScope.launch {
                    mainViewModel.likeVault(name)
                    updateRecyclerData()
                }
            }

            override fun dislikeVault(name: String) {
                lifecycleScope.launch {
                    mainViewModel.dislikeVault(name)
                    updateRecyclerData()
                }
            }

            override fun clickItemListener(name: String, rate: Double) {
                Change.setName(name)
                Change.setCourse(rate)
                vaultInterface.openChangeScreen()
            }
        })

        updateRecyclerData()

        val layoutManager = LinearLayoutManager(context)
        binding.vaultRecycler.layoutManager = layoutManager
        binding.vaultRecycler.adapter = adapter

        return binding.root

    }

    private fun initViewModel() {
        val repository = (activity?.application as MainApplication).repository
        mainViewModel =
            ViewModelProvider(this, VaultViewModelFactory(repository)).get(AppViewModel::class.java)
    }

    private fun updateRecyclerData() {
        lifecycleScope.launch {
            mainViewModel.getLatestVault().observe(viewLifecycleOwner, Observer {
                val _wait = runCatching { it }
                _wait.onSuccess {
                    adapter.vaultList = it
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }
}