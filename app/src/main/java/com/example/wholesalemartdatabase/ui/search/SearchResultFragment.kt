package com.example.wholesalemartdatabase.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wholesalemartdatabase.R
import com.example.wholesalemartdatabase.data.Customer
import com.example.wholesalemartdatabase.data.CustomerStatus
import com.example.wholesalemartdatabase.domain.DataBase
import com.example.wholesalemartdatabase.ui.mainrecyclerview.MainRecyclerViewAdapter
import com.example.wholesalemartdatabase.ui.mainrecyclerview.OnItemClicked
import kotlinx.android.synthetic.main.fragment_search_result.*
import java.math.BigInteger


class SearchResultFragment : Fragment(), OnItemClicked {

    private var mainRecyclerViewAdapter: MainRecyclerViewAdapter? = null
    private var customers = ArrayList<Customer>()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)

        customers = DataBase.getInstance().search(arguments)

        search_results_recycler_view.adapter = MainRecyclerViewAdapter(context, customers, this)
        search_results_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        search_results_toolbar.setNavigationOnClickListener { navController.popBackStack() }

        results_all_delete_button.setOnClickListener {
            for (customer in customers) {
                DataBase.getInstance().removeCustomerByPhone(customer.phone)
            }
            navController.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        mainRecyclerViewAdapter?.refreshData(customers)
    }

    override fun onItemClicked(phoneNumber: String?) {

        val bundle = Bundle()
        bundle.putString("clicked_phone", phoneNumber)
        navController.navigate(R.id.editItemFragment, bundle)

    }
}
