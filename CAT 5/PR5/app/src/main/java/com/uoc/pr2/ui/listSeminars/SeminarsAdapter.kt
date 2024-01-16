package com.uoc.pr2.ui.list

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uoc.pr2.R
import com.uoc.pr2.data.model.Seminary


class SeminarsAdapter(private val onClick: (Seminary) -> Unit) :
    ListAdapter<Seminary, SeminarsAdapter.ItemViewHolder>(SeminarsDiffCallback) {


    public lateinit var context: Context

    /* ViewHolder for Item, takes in the inflated view and the onClick behavior. */
    class ItemViewHolder(itemView: View, val onClick: (Seminary) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text)
        public val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
        private var currentItem: Seminary? = null

        public lateinit var context: Context

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    it.view = itemImageView
                    onClick(it)
                }
            }
        }



        /* Bind item name and image. */
        fun bind(item: Seminary) {
            currentItem = item



            itemTextView.text = item.name


            var resource_id:Int = R.drawable.requestlogo

            if(item.id==1){
                resource_id = R.drawable.doglogo
            }
            else if(item.id==2){
                resource_id = R.drawable.medicinelogo
            }
            else if(item.id==3){
                resource_id = R.drawable.ailogo
            }


            val bmp = BitmapFactory.decodeResource(context.getResources(), resource_id)
            itemImageView.setImageBitmap(bmp)




        }
    }



    override fun getItemViewType(position: Int): Int {

            return 1

    }

    /* Creates and inflates view and return Item. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seminar, parent, false)

        var holder =  ItemViewHolder(view, onClick)
        holder.context = context

        return holder;
    }

    /* Gets current Item and uses it to bind view. */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemImageView.transitionName = item.name
        holder.bind(item)


    }


}

object SeminarsDiffCallback : DiffUtil.ItemCallback<Seminary>() {
    override fun areItemsTheSame(oldItem: Seminary, newItem: Seminary): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Seminary, newItem: Seminary): Boolean {
        return oldItem.id == newItem.id
    }
}