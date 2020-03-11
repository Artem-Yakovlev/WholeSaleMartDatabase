package com.example.wholesalemartdatabase.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.wholesalemartdatabase.R
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePhoneInput()

        val navController = NavHostFragment.findNavController(this)

        search_request_button.setOnClickListener {
            if (checkCorrectRequest()) {
                navController.navigate(R.id.searchResultFragment)
            }
        }
    }

    private fun checkCorrectRequest(): Boolean {
        if (emptyCheck()) {
            Toast.makeText(context, "An empty request is useless!", Toast.LENGTH_LONG).show()
            return false
        } else if (!correctNameAndSurnameCheck()) {
            Toast.makeText(context, "Data is not correct", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun preparePhoneInput() {
        phone_number_request_input.setText("+")

        phone_number_request_input.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().startsWith("+")) {
                    phone_number_request_input.setText("+")
                }
            }
        })
    }

    private fun emptyCheck(): Boolean {
        return phone_number_request_input.text.toString().trim() == "+" &&
                name_request_input.text.toString().trim() == "" &&
                surname_request_input.text.toString().trim() == "" &&
                budget_request_input.text.toString().trim() == "" &&
                (radio_group_request_status.checkedRadioButtonId == R.id.radio_status_any ||
                        radio_group_request_status.checkedRadioButtonId == -1)
    }

    private fun correctNameAndSurnameCheck(): Boolean {
        //TODO("Need refactor")
        val name = name_request_input.text.toString().trim()
        val surname = surname_request_input.text.toString().trim()
        val phoneNumber = phone_number_request_input.text.toString().trim()
        val budget = budget_request_input.text.toString().trim()

        var correct = true

        if (!name.matches(Regex("[a-zA-Z]*"))) {
            name_request_input.error = "Data is not correct"
            correct = false
        }

        if (!surname.matches(Regex("[a-zA-Z]*"))) {
            surname_request_input.error = "Data is not correct"
            correct = false
        }

        if (!phoneNumber.matches(Regex("[+][0-9]*"))) {
            phone_number_request_input.error = "Data is not correct"
            correct = false
        }

        if (!(budget.matches(Regex("[0-9]*")) && (budget.isEmpty() || Integer.parseInt(budget) > 0)) ) {
            budget_request_input.error = "Data is not correct"
            correct = false
        }

        return correct
    }
}