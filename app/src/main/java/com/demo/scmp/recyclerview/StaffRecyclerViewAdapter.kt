package com.demo.scmp.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.scmp.databinding.ListItemEmptyViewBinding
import com.demo.scmp.databinding.ListItemLoadMoreBinding
import com.demo.scmp.databinding.ListItemStaffBinding
import com.demo.scmp.interfaces.OnItemClickListener
import org.json.JSONObject

class StaffRecyclerViewAdapter(val listener: OnItemClickListener) : RecyclerView.Adapter<StaffRecyclerViewAdapter.BaseViewHolder>() {

    enum class VHType(val type: Int) {
        DATA(type = 1),
        LOADING(type = 2),
        UN_KNOW(type = 0)
    }

    private var list: ArrayList<JSONObject> = ArrayList()

    fun addAll(data: ArrayList<JSONObject>) {
        if(list.size > 0) {
            list.last().let {
                if(it.has("type") && it.getString("type") == "loading") {
                    val pos = list.size - 1
                    list.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }
        val startIndex = list.size
        list.addAll(data)
        notifyItemRangeInserted(startIndex, list.size)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].has("type").let {
            if (it) {
                if (list[position].getString("type") == "data") {
                    VHType.DATA.type
                } else if (list[position].getString("type") == "loading") {
                    VHType.LOADING.type
                } else {
                    VHType.UN_KNOW.type
                }
            } else {
                VHType.UN_KNOW.type
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VHType.DATA.type -> {
                val binding =
                    ListItemStaffBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return VH(binding)
            }
            VHType.LOADING.type -> {
                val binding =
                    ListItemLoadMoreBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return VHLoadMore(binding)
            }
            else -> {
                val binding =
                    ListItemEmptyViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return VHEmpty(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setUp(data = list[holder.adapterPosition], listener)
    }

    override fun getItemCount(): Int = list.size

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun setUp(data: JSONObject?, listener: OnItemClickListener)
    }

    class VH(val binding: ListItemStaffBinding) : BaseViewHolder(binding.root) {
        override fun setUp(data: JSONObject?, listener: OnItemClickListener) {
            data?.let {
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
            } ?: run {
                binding.componentStaffInfo.getTvEmail().text = ""
                binding.componentStaffInfo.getTvAvatar().text = ""
                binding.componentStaffInfo.getTvFirstName().text = ""
                binding.componentStaffInfo.getTvLastName().text = ""
            }
        }
    }

    class VHLoadMore(val binding: ListItemLoadMoreBinding) : BaseViewHolder(binding.root) {
        override fun setUp(data: JSONObject?, listener: OnItemClickListener) {
            binding.loading.visibility = View.GONE
            binding.btnLoadMore.visibility = View.VISIBLE
            binding.btnLoadMore.setOnClickListener {
                listener.onClickItem(data, adapterPosition)
                binding.loading.visibility = View.VISIBLE
                binding.btnLoadMore.visibility = View.GONE
            }
        }
    }

    class VHEmpty(val binding: ListItemEmptyViewBinding) : BaseViewHolder(binding.root) {
        override fun setUp(data: JSONObject?, listener: OnItemClickListener) {}
    }

}