package com.uoc.pr2.ui.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.uoc.pr2.R
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.data.model.ItemType

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    public var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v:View = inflater.inflate(R.layout.fragment_detail, container, false)


        var v2: ImageView = v.findViewById(R.id.item_image_detail)


        if(item!!.type==ItemType.IMAGE) {

            //BEGIN-CODE-UOC-5.1
            val v2: ImageView = v.findViewById(R.id.item_image_detail)

            if (item!!.type == ItemType.IMAGE) {

                //BEGIN-CODE-UOC-5.1
                val storage = FirebaseStorage.getInstance()
                val gsReference = storage.getReferenceFromUrl(item!!.image_path!!)
                Glide.with(this)
                    .load(gsReference)
                    .into(v2)
            //END-CODE-UOC-5.1




        }
        else{

            (v2.parent as ViewManager).removeView(v2)
        }


        var v3: TextView = v.findViewById(R.id.item_text_detail)
        v3.setText(item!!.description)

        var v4: TextView = v.findViewById(R.id.item_text_title)
        v4.setText(item!!.name)

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}