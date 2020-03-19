package com.example.wholesalemartdatabase.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment

import com.example.wholesalemartdatabase.R
import com.example.wholesalemartdatabase.data.Customer
import com.example.wholesalemartdatabase.data.CustomerStatus
import com.example.wholesalemartdatabase.domain.DataBase
import kotlinx.android.synthetic.main.fragment_create_item.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.math.BigInteger


class CreateItemFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePhoneInput()
        radio_group_create_status.check(R.id.radio_create_status_bronze)
        create_item_button.setOnClickListener {
            if (checkCorrectData()) {
                val customer = Customer(name_create_input.text.toString().trim().toLowerCase(),
                        surname_create_input.text.toString().trim().toLowerCase(),
                        phone_number_create_input.text.toString().trim(),
                        BigInteger(budget_create_input.text.toString().trim(), 10),
                        when (radio_group_create_status.checkedRadioButtonId) {
                            R.id.radio_create_status_gold -> CustomerStatus.GOLD
                            R.id.radio_create_status_silver -> CustomerStatus.SILVER
                            else -> CustomerStatus.BRONZE
                        }
                )

                if (DataBase.getInstance().addNewCustomer(customer)) {
                    val navController = NavHostFragment.findNavController(this)
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "A client with such a phone exists", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Data is not correct", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun preparePhoneInput() {
        phone_number_create_input.setText("+")
        phone_number_create_input.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().startsWith("+")) {
                    phone_number_create_input.setText("+")
                }
            }
        })
    }

    private fun checkCorrectData(): Boolean {
        val name = name_create_input.text.toString().trim()
        val surname = surname_create_input.text.toString().trim()
        val phoneNumber = phone_number_create_input.text.toString().trim()
        val budget = budget_create_input.text.toString().trim()

        var correct = true

        if (!name.matches(Regex("[a-zA-Z]+"))) {
            name_create_input.error = "Data is not correct"
            correct = false
        }

        if (!surname.matches(Regex("[a-zA-Z]+"))) {
            surname_create_input.error = "Data is not correct"
            correct = false
        }

        if (!phoneNumber.matches(Regex("[+][0-9]+"))) {
            phone_number_create_input.error = "Data is not correct"
            correct = false
        }

        if (!(budget.matches(Regex("[0-9]+")))) {
            budget_create_input.error = "Data is not correct"
            correct = false
        }

        return correct
    }
}
