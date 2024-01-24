package com.demo.scmp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.scmp.databinding.ListItemStaffBinding
import org.json.JSONObject

class StaffRecyclerViewAdapter : RecyclerView.Adapter<StaffRecyclerViewAdapter.VH>() {

    private var list: ArrayList<JSONObject> = ArrayList()

    fun addAll(data: ArrayList<JSONObject>) {
        val startIndex = list.size
        list.addAll(data)
        notifyItemRangeInserted(startIndex, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ListItemStaffBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setUp(data = list[holder.adapterPosition])
    }

    override fun getItemCount(): Int = list.size

    class VH(val binding: ListItemStaffBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setUp(data: JSONObject) {
            data.let {
                binding.componentStaffInfo.getTvEmail().text =
                    it.has("email").let { it1 ->
                        if (it1) {
                            it.getString("email")
                        } else {
                            "-"
                        }
                    }
                binding.componentStaffInfo.getTvAvatar().text =
                    it.has("avatar").let { it1 ->
                        if (it1) {
                            it.getString("avatar")
                        } else {
                            "-"
                        }
                    }
                binding.componentStaffInfo.getTvFirstName().text =
                    it.has("first_name").let { it1 ->
                        if (it1) {
                            it.getString("first_name")
                        } else {
                            "-"
                        }
                    }
                binding.componentStaffInfo.getTvLastName().text =
                    it.has("last_name").let { it1 ->
                        if (it1) {
                            it.getString("last_name")
                        } else {
                            "-"
                        }
                    }
            }
        }
    }
}