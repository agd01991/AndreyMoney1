package com.example.andrewmoney.ui.vault.change

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.andrewmoney.ChangeInterface
import com.example.andrewmoney.MainApplication
import com.example.andrewmoney.data.local.model.LocalHistoryModel
import com.example.andrewmoney.databinding.FragmentChangeBinding
import com.example.andrewmoney.viewmodel.AppViewModel
import com.example.andrewmoney.viewmodel.VaultViewModelFactory
import kotlinx.coroutines.launch


class Change : Fragment() {

    private lateinit var binding: FragmentChangeBinding
    private lateinit var mainViewModel: AppViewModel

    override fun onResume() {
        super.onResume()
        initExchange()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangeBinding.inflate(inflater, container, false)

        initExchange()

        initButtonFunctions()

        initEditView()

        initViewModel()

        return binding.root
    }

    private fun initViewModel(){
        val repository = (activity?.application as MainApplication).repository

        mainViewModel = ViewModelProvider(this, VaultViewModelFactory(repository)).get(AppViewModel::class.java)
    }

    private fun initEditView(){

        binding.priceField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty() && s.length <= 9) {
                    val summ = s.toString().toDouble() * Change.getCourse()
                    binding.exchangedPrice.setText(summ.toString())
                }
                else{
                    binding.priceField.setText("0")
                }
            }
        })

    }

    private fun initButtonFunctions(){
        val changeInterface = requireActivity() as ChangeInterface
        binding.backButton.setOnClickListener {
            changeInterface.closeChangeScreen()
        }
        binding.button.setOnClickListener {
            lifecycleScope.launch {
                val firstPrice = binding.priceField.text.toString()
                val secondPrice = binding.exchangedPrice.text.toString()
                mainViewModel.addHistory(LocalHistoryModel(0, getName(), firstPrice, secondPrice))
//                changeInterface.closeChangeScreen()
            }
        }
    }

    private fun initExchange(){
        binding.firstVaultName.setText("RUB")
        binding.secondVaultName.setText(getName())

        // INIT START PRICE
        binding.priceField.setText(getStartPrice().toString())
        val summ = getStartPrice() * Change.getCourse()
        binding.exchangedPrice.setText(summ.toString())
    }

    companion object{

        private var startPrice: Double = 0.0


        private var name: String = ""
        private var course: Double = 0.0

        fun getStartPrice(): Double{
            return startPrice
        }

        fun getName(): String{
            return name
        }

        fun setName(item: String){
            name = item
        }

        fun getCourse(): Double{
            return course
        }

        fun setCourse(item: Double){
            course = item
        }

    }

}