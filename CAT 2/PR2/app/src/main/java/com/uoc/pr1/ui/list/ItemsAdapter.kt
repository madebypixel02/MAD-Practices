package com.uoc.pr1.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uoc.pr1.R
import com.uoc.pr1.data.model.Item
import com.uoc.pr1.data.model.ItemType


class ItemsAdapter(private val onClick: (Item) -> Unit) :
    ListAdapter<Item, ItemsAdapter.ItemViewHolder>(ItemDiffCallback) {

    /* ViewHolder for Item, takes in the inflated view and the onClick behavior. */
    class ItemViewHolder(itemView: View, val onClick: (Item) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text)
        public val itemImageView: ImageView? = itemView.findViewById(R.id.item_image)
        private var currentItem: Item? = null

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

            //BEGIN-CODE-UOC-4.4
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
            //END-CODE-UOC-4.4

        }
    }


    override fun getItemViewType(position: Int): Int {

            val item = getItem(position)
            return item.type.v1

    }

    /* Creates and inflates view and return Item. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var view:View? = null

        //BEGIN-CODE-UOC-4.3
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)

        //END-CODE-UOC-4.3

        return ItemViewHolder(view, onClick)
    }

    /* Gets current Item and uses it to bind view. */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemImageView?.transitionName = item.name
        holder.bind(item)


    }

    /*
    override fun submitList(list: MutableList<Item>?) {
        // replace 5 with any number you want
        super.submitList(list)
    }

     */
}

object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }
}