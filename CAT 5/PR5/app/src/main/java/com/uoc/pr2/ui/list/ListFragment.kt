package com.uoc.pr2.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uoc.pr2.AddRequest
import com.uoc.pr2.AddRequestResult
import com.uoc.pr2.MainActivity
import com.uoc.pr2.PARAM_ADDREQUESTRESULT_CLASS
import com.uoc.pr2.data.DataSource
import com.uoc.pr2.data.ListenerData
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.databinding.FragmentListBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    private val itemsListViewModel by viewModels<ItemsListViewModel> {
        ItemsListViewModelFactory(requireContext())
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerView: RecyclerView
    lateinit var itemsAdapter:  ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }


    private var btnNew_visibility:Boolean = false

    lateinit var l:LinearLayoutManager

    lateinit var timer:CountDownTimer

    var listener:ListenerData = ListenerData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          binding = FragmentListBinding.inflate(inflater)


        itemsAdapter = ItemsAdapter { item -> adapterOnClick(item) }

        itemsAdapter.context = context!!

        recyclerView = binding.recyclerList


        l = LinearLayoutManager((activity as MainActivity?)!!,LinearLayoutManager.VERTICAL,false)


        recyclerView.setLayoutManager(l);



       recyclerView.adapter = itemsAdapter



        itemsListViewModel.itemsLiveData?.observe(  (activity as MainActivity?)!!, {
            it?.let {

                    itemsAdapter.submitList(it as MutableList<Item>)


            }

        })


        var getResult  = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                val value = it.data?.getParcelableExtra<AddRequestResult>(PARAM_ADDREQUESTRESULT_CLASS)


                var dataSource: DataSource
                dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default,this.context)


                dataSource.addItemAsync(value?.title!!,value?.description!!,value?.uri, listener)


            }
        }



        binding.btnNew.setOnClickListener {

            val intent = Intent(this.context, AddRequest::class.java)
            getResult.launch(intent)



        }




        return binding.root
    }


    public fun showAddRequestButton()
    {
        btnNew_visibility = true

    }

    public fun hideAddRequestButton()
    {
        btnNew_visibility = false

    }


    override fun onDestroy()
    {
        super.onDestroy()

    }

    override fun onResume()
    {
        super.onResume()
        if(btnNew_visibility){

            binding.btnNew.visibility = VISIBLE
        }
        else{
            binding.btnNew.visibility = GONE
        }

    }

    private fun adapterOnClick(item: Item) {
        (activity as MainActivity?)!!.openDetail(item)

    }

    fun SetAdapter()
    {

        recyclerView.adapter = itemsAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */



        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



