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
import com.uoc.pr1.data.model.Seminar


class SeminarsAdapter(private val onClick: (Seminar) -> Unit) :
    ListAdapter<Seminar, SeminarsAdapter.ItemViewHolder>(SeminarDiffCallback) {

    /* ViewHolder for Item, takes in the inflated view and the onClick behavior. */
    class ItemViewHolder(itemView: View, val onClick: (Seminar) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text)
        public val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
        private var currentItem: Seminar? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    it.view = itemImageView
                    onClick(it)
                }
            }
        }



        /* Bind item name and image. */
        fun bind(item: Seminar) {
            currentItem = item

            //BEGIN-CODE-UOC-2.2
            itemTextView.text = item.name
            if (item.image != null) {
                itemImageView.setImageResource(item.image)
            } else {
                itemImageView.setImageResource(R.drawable.ailogo)
            }
            //END-CODE-UOC-2.2

        }
    }


    override fun getItemViewType(position: Int): Int {

            return 1

    }

    /* Creates and inflates view and return Item. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seminar, parent, false)
        return ItemViewHolder(view, onClick)
    }

    /* Gets current Item and uses it to bind view. */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemImageView.transitionName = item.name
        holder.bind(item)


    }


}

object SeminarDiffCallback : DiffUtil.ItemCallback<Seminar>() {
    override fun areItemsTheSame(oldItem: Seminar, newItem: Seminar): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Seminar, newItem: Seminar): Boolean {
        return oldItem.id == newItem.id
    }
}