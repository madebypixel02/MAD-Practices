package com.uoc.pr2.ui.list

import android.graphics.Bitmap
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
    ListAdapter<Seminary, SeminarsAdapter.ItemViewHolder>(SeminarDiffCallback) {

    /* ViewHolder for Item, takes in the inflated view and the onClick behavior. */
    class ItemViewHolder(itemView: View, val onClick: (Seminary) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text)
        public val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
        private var currentItem: Seminary? = null

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

            //BEGIN-CODE-UOC-5.1
            val imagePath = item.image_path
            val bitmap = BitmapFactory.decodeFile(imagePath)
            itemImageView.setImageBitmap(bitmap)
            //END-CODE-UOC-5.1




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

object SeminarDiffCallback : DiffUtil.ItemCallback<Seminary>() {
    override fun areItemsTheSame(oldItem: Seminary, newItem: Seminary): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Seminary, newItem: Seminary): Boolean {
        return oldItem.id == newItem.id
    }
}