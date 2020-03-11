package com.example.wholesalemartdatabase.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wholesalemartdatabase.R
import com.example.wholesalemartdatabase.data.Customer
import com.example.wholesalemartdatabase.data.CustomerStatus
import com.example.wholesalemartdatabase.ui.mainrecyclerview.MainRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_search_result.*
import java.math.BigInteger


class SearchResultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = NavHostFragment.findNavController(this)

        val customers = ArrayList<Customer>()


        customers.add(Customer("Artem", "Bagrenec", "+79056644712", BigInteger("6000", 10), CustomerStatus.GOLD))
        customers.add(Customer("Artem", "Shrek", "+79056644710", BigInteger("2000", 10), CustomerStatus.SILVER))
        customers.add(Customer("Artem", "Rudoi", "+79056644711", BigInteger("400", 10), CustomerStatus.BRONZE))

        search_results_recycler_view.adapter = MainRecyclerViewAdapter(context, customers)
        search_results_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        search_results_toolbar.setNavigationOnClickListener { navController.popBackStack() }
    }
}
