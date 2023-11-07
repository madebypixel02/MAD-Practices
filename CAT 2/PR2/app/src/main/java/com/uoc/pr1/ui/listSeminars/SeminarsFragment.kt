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
import com.uoc.pr1.data.model.Seminar
import com.uoc.pr1.databinding.FragmentListSeminarsBinding


class SeminarsFragment : Fragment() {
    private lateinit var binding: FragmentListSeminarsBinding

    private val seminarsViewModel by viewModels<SeminarsViewModel> {
        SeminarsViewModelViewModelFactory(requireContext())
    }


    lateinit var recyclerView: RecyclerView
    lateinit var seminarsAdapter:  SeminarsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }



    }



    lateinit var l:LinearLayoutManager

    lateinit var timer:CountDownTimer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // postponeEnterTransition()
        // Inflate the layout for this fragment

        //
        binding = FragmentListSeminarsBinding.inflate(inflater)


        seminarsAdapter = SeminarsAdapter { d -> adapterOnClick(d) }

       // itemsAdapter.submitList(itemsListViewModel.itemsLiveData.value as MutableList<Item>)


        recyclerView = binding.recyclerList

      //  recyclerView.getRecycledViewPool().setMaxRecycledViews(1,0)

      //  recyclerView.setItemViewCacheSize(1000)

        l = LinearLayoutManager((activity as MainActivity?)!!,LinearLayoutManager.VERTICAL,false)

      //  var l = MyLinearLayoutManager((activity as MainActivity?)!!,LinearLayoutManager.VERTICAL,false, this)


        recyclerView.setLayoutManager(l);



       recyclerView.adapter = seminarsAdapter



        seminarsViewModel.itemsLiveData?.observe(  (activity as MainActivity?)!!, {
            it?.let {

                seminarsAdapter.submitList(it as MutableList<Seminar>)


            }

        })




/*
        postponeEnterTransition()
        recyclerView.viewTreeObserver
            .addOnPreDrawListener {
                if(this.recyclerView.childCount !=0) {
                    startPostponedEnterTransition()
                    true
                }
                else {
                    false
                }

            }
            */

        /*
        (recyclerView.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()

        }
*/



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

    private fun adapterOnClick(item: Seminar) {
        (activity as MainActivity?)!!.openSeminar(item)

    }

    fun SetAdapter()
    {

        recyclerView.adapter = seminarsAdapter
    }

    companion object {


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SeminarsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}



