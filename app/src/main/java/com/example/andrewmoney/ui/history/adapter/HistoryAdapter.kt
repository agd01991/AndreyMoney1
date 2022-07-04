package com.example.andrewmoney.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.andrewmoney.data.local.model.LocalHistoryModel
import com.example.andrewmoney.data.local.model.LocalVaultModel
import com.example.andrewmoney.databinding.HistoryItemBinding
import com.example.andrewmoney.databinding.VaultItemBinding
import com.example.andrewmoney.ui.vault.adapter.VaultAdapter

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var historyList: List<LocalHistoryModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemBinding.inflate(inflater, parent, false)

        return HistoryAdapter.HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]

        with(holder.binding){
            firstVaultName.text = "RUB"
            secondVaultName.text = item.name

            priceField.text = item.firstAmount
            exchangedPrice.text = item.secondAmount
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class HistoryViewHolder (var binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root)

}