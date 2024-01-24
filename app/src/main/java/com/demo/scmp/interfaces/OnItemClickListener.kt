package com.demo.scmp.interfaces

import org.json.JSONObject

interface OnItemClickListener {
    fun onClickItem(data: JSONObject?, pos: Int)
}