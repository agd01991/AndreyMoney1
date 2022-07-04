package com.example.andrewmoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.andrewmoney.databinding.ActivityMainBinding
import com.example.andrewmoney.ui.graph.Graph
import com.example.andrewmoney.ui.history.History
import com.example.andrewmoney.ui.vault.Vault
import com.example.andrewmoney.ui.vault.change.Change
import com.example.andrewmoney.viewmodel.AppViewModel
import com.example.andrewmoney.viewmodel.VaultViewModelFactory

interface VaultInterface{
    fun openChangeScreen()
}
interface ChangeInterface{
    fun closeChangeScreen()
}
class MainActivity : AppCompatActivity(), VaultInterface, ChangeInterface {

    private lateinit var binding: ActivityMainBinding

    private val vaultFragment = Vault()
    private val changeFragment = Change()
    private val historyFragment = History()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        startFragment()

        binding.navigation.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.home -> replaceFragment(vaultFragment)
                R.id.history -> replaceFragment(historyFragment)

            }
            true
        }

        setContentView(binding.root)
    }


    private fun startFragment(){
        replaceFragment(vaultFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.fragmentContainer.id, fragment)
            transaction.commit()
        }
    }

    override fun openChangeScreen() {
        binding.navigation.visibility = View.GONE
        replaceFragment(changeFragment)
    }

    override fun closeChangeScreen() {
        binding.navigation.visibility = View.VISIBLE
        replaceFragment(vaultFragment)
    }

}