package com.demo.scmp.conponents

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.demo.scmp.R
import com.demo.scmp.databinding.ComponentStaffInfoBinding

class StaffInfoFrame @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var binding: ComponentStaffInfoBinding

    init {

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL

        binding = ComponentStaffInfoBinding.inflate(LayoutInflater.from(context), this)

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.StaffInfoFrame, 0, 0)
            ta.recycle()
        }
    }

    fun getTvEmail(): TextView {
        return binding.tvEmail
    }

    fun getTvAvatar(): TextView {
        return binding.tvAvatar
    }

    fun getTvFirstName(): TextView {
        return binding.tvFirstName
    }

    fun getTvLastName(): TextView {
        return binding.tvLastName
    }
}