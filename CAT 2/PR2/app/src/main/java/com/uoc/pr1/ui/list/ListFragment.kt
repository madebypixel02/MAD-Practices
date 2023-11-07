package com.uoc.pr1.ui.list

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uoc.pr1.MainActivity
import com.uoc.pr1.data.model.Item
import com.uoc.pr1.databinding.FragmentListBinding


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



    lateinit var l:LinearLayoutManager

    lateinit var timer:CountDownTimer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          binding = FragmentListBinding.inflate(inflater)


        itemsAdapter = ItemsAdapter { item -> adapterOnClick(item) }



        recyclerView = binding.recyclerList


        l = LinearLayoutManager((activity as MainActivity?)!!,LinearLayoutManager.VERTICAL,false)


        recyclerView.setLayoutManager(l);



       recyclerView.adapter = itemsAdapter



        itemsListViewModel.itemsLiveData?.observe(  (activity as MainActivity?)!!, {
            it?.let {

                    itemsAdapter.submitList(it as MutableList<Item>)


            }

        })



        return binding.root
    }



    override fun onDestroy()
    {
        super.onDestroy()

    }

    override fun onResume()
    {
        super.onResume()


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



