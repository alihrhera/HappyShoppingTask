package com.taskapp.happyshoppingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.data.models.app.ProductItem
import com.taskapp.happyshoppingapp.data.call_back.OnItemClick
import com.taskapp.happyshoppingapp.databinding.RowItemBinding

class ItemAdapter :
    ListAdapter<ProductItem, ItemAdapter.ItemHolder>(
        NotificationDiffCallback()
    ) {

    class NotificationDiffCallback : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(
            oldItem: ProductItem,
            newItem: ProductItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ProductItem,
            newItem: ProductItem
        ): Boolean {

            return oldItem == newItem

        }
    }


    private var itemList: MutableList<ProductItem> = ArrayList()
    lateinit var onItemClick: OnItemClick
    lateinit var onDeleteClick: OnItemClick
    lateinit var onLikeClick: OnItemClick


    fun setDataSource(itemList: List<ProductItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        submitList(itemList)
    }

    fun deleteItem(item: ProductItem) {
        itemList.remove(item)
        submitList(itemList)
    }

    fun update(item: ProductItem) {
        notifyItemChanged(itemList.indexOf(item))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemBinding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = itemList[position]
        holder.row.itemName.text = item.name
        holder.row.itemPrice.text = ("EGP ${item.price}")

        Picasso.get().load(item.image)
            .centerCrop().fit()
            .error(R.drawable.item_place_holder)
            .into(holder.row.itemImage)


        holder.row.likeBtn.setImageResource(R.drawable.ic_deslike)
        if (item.like) {
            holder.row.likeBtn.setImageResource(R.drawable.ic_like)
        }



        holder.itemView.setOnClickListener {
            onItemClick.onClick(item)
        }

        holder.row.deleteBtn.setOnClickListener {
            onDeleteClick.onClick(item)
        }

        holder.row.likeBtn.setOnClickListener {
            onLikeClick.onClick(item)
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    class ItemHolder(val row: RowItemBinding) : RecyclerView.ViewHolder(row.root)

}