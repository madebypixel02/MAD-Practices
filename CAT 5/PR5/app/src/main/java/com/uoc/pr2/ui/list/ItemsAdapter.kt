package com.uoc.pr2.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.uoc.pr2.R
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.data.model.ItemType


class ItemsAdapter(private val onClick: (Item) -> Unit) :
    ListAdapter<Item, ItemsAdapter.ItemViewHolder>(ItemDiffCallback) {

    public lateinit var context: Context

    /* ViewHolder for Item, takes in the inflated view and the onClick behavior. */
    class ItemViewHolder(itemView: View, val onClick: (Item) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text)
        public val itemImageView: ImageView? = itemView.findViewById(R.id.item_image)
        private var currentItem: Item? = null

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
        fun bind(item: Item) {
            currentItem = item


            if(item.type==ItemType.IMAGE) {
                itemTextView.text = item.name

                if (item.image != null) {
                    itemImageView?.setImageResource(item.image)
                } else {
                    itemImageView?.setImageResource(R.drawable.rose)
                }


            }
            else{
                itemTextView.text = item.name
            }


        }
    }


    override fun getItemViewType(position: Int): Int {

            val item = getItem(position)
            return item.type.v1

    }

    /* Creates and inflates view and return Item. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var view:View? = null


        if(viewType == 1) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_item_basic, parent, false)
        }
        else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
        }


        val viewHolder = ItemViewHolder(view, onClick)


        viewHolder.context = context

        return viewHolder
    }

    /* Gets current Item and uses it to bind view. */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemImageView?.transitionName = item.name
        holder.bind(item)


    }


}

object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }
}