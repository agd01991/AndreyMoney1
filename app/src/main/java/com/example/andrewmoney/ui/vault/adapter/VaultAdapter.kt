package com.example.andrewmoney.ui.vault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.andrewmoney.R
import com.example.andrewmoney.data.local.model.LocalVaultModel
import com.example.andrewmoney.databinding.VaultItemBinding
import com.example.andrewmoney.ui.vault.VaultFunctions

class VaultAdapter(private val functions: VaultFunctions): RecyclerView.Adapter<VaultAdapter.VaultViewHolder>() {

    var vaultList: List<LocalVaultModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VaultItemBinding.inflate(inflater, parent, false)

        return VaultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VaultViewHolder, position: Int) {
        val item = vaultList[position]

        holder.itemView.setOnClickListener {
            functions.clickItemListener(item.name, item.rate)
        }

        with(holder.binding){
            vaultName.text = item.name

            if(item.isLiked){
                likeIcon.setImageResource(R.drawable.ic_star)
            }
            else{
                likeIcon.setImageResource(R.drawable.ic_star_outline)
            }

            likeIcon.setOnClickListener {
                when {
                    item.isLiked -> {
                        functions.dislikeVault(item.name)
                    }
                    else -> {
                        functions.likeVault(item.name)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return vaultList.size
    }

    class VaultViewHolder (var binding: VaultItemBinding): RecyclerView.ViewHolder(binding.root)

}