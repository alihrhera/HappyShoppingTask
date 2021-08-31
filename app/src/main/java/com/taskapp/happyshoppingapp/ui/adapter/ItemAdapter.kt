package com.taskapp.happyshoppingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.data.models.Item
import com.taskapp.happyshoppingapp.data.models.OnItemClick
import com.taskapp.happyshoppingapp.databinding.RowItemBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    private var itemList: MutableList<Item> = ArrayList()
    private lateinit var onItemClick: OnItemClick
    private lateinit var onDeleteClick: OnItemClick
    private lateinit var onLikeClick: OnItemClick


    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    fun setOnDeleteClick(onDelete: OnItemClick) {
        this.onDeleteClick = onDelete
    }

    fun setOnLikeClick(onLike: OnItemClick) {
        this.onLikeClick = onLike
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setDataSource(itemList: List<Item>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    fun deleteItem(item: Item) {
        itemList.remove(item)
        notifyDataSetChanged()

    }

    fun update(item: Item) {
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

        Picasso.get().load(item.image ?: "test")
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