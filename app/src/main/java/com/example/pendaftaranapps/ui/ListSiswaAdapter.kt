package com.example.pendaftaranapps.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pendaftaranapps.AddUpdateActivity
import com.example.pendaftaranapps.data.response.DataItem
import com.example.pendaftaranapps.databinding.ItemSiswaBinding

class ListSiswaAdapter : ListAdapter<DataItem, ListSiswaAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(val binding: ItemSiswaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: DataItem) {
            binding.namaSiswa.text = dataItem.nama
            binding.sekolahAsal.text = dataItem.sekolahAsal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSiswaAdapter.ViewHolder {
        val binding = ItemSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSiswaAdapter.ViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)

        val activity = holder.itemView.context as Activity
        holder.binding.rvSiswa.setOnClickListener {
            val intent = Intent(activity, AddUpdateActivity::class.java)
            intent.putExtra(AddUpdateActivity.EXTRA_DATA, dataItem)
            activity.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
