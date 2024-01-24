package com.demo.scmp.controllers.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.scmp.databinding.ActivityStaffListBinding
import com.demo.scmp.interfaces.OnItemClickListener
import com.demo.scmp.recyclerview.StaffRecyclerViewAdapter
import com.demo.scmp.services.network.ApiCallBack
import com.demo.scmp.services.network.ApiManager
import com.demo.scmp.services.network.ApiUrl
import com.demo.scmp.services.network.HttpMethod
import org.json.JSONObject

class StaffListActivity : BaseActivity() {

    private lateinit var binding: ActivityStaffListBinding

    private var page: Int = 1
    private var totalPages: Int = 0

    private var adapter: StaffRecyclerViewAdapter = StaffRecyclerViewAdapter(getListener())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.hasExtra("token")) {
                binding.tvToken.text = it.getStringExtra("token")
            }
        }

        setupRv()
        setOnClick()
        callApiGetUserData()
    }

    private fun setOnClick() {
        binding.tvLogout.setOnClickListener {
            finish()
        }
    }

    private fun setupRv() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL

        binding.rv.layoutManager = llm
        binding.rv.adapter = adapter
    }

    private fun getListener(): OnItemClickListener = object : OnItemClickListener {
        override fun onClickItem(data: JSONObject?, pos: Int) {
            data?.let {
                if(data.has("type") && data.getString("type") == "loading") {
                    callApiGetUserData()
                }
            }
        }
    }

    private fun callApiGetUserData() {
        ApiManager.call(
            HttpMethod.GET,
            ApiUrl.API_USERS.endpoint + "?page=" + page,
            null,
            object : ApiCallBack {
                override fun onFail(e: String) {
                    runOnUiThread {
                        binding.rv.visibility = View.VISIBLE
                        binding.fltLoading.visibility = View.GONE
                        showMsg(e)
                    }

                }

                override fun onError(e: String) {
                    runOnUiThread {
                        binding.rv.visibility = View.VISIBLE
                        binding.fltLoading.visibility = View.GONE
                        showMsg(e)
                    }
                }

                override fun onSuccess(responseData: JSONObject) {
                    runOnUiThread {
                        binding.rv.visibility = View.VISIBLE
                        binding.fltLoading.visibility = View.GONE
                        val list: ArrayList<JSONObject> = ArrayList()
                        if (responseData.has("data")) {
                            val data = responseData.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                if (data.get(i) is JSONObject) {
                                    val jo = data.getJSONObject(i)
                                    jo.put("type", "data")
                                    list.add(jo)
                                }
                            }
                            if (data.length() > 0 && page == 1) {
                                data.getJSONObject(0)?.let {
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
                        if (responseData.has("page") && responseData.has("total_pages")) {
                            totalPages = responseData.getInt("total_pages")
                            page = responseData.getInt("page") + 1
                            if (page <= totalPages) {
                                val loadingJO = JSONObject()
                                loadingJO.put("type", "loading")
                                list.add(loadingJO)
                            }
                        }
                        adapter.addAll(list)
                    }

                }
            })
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}