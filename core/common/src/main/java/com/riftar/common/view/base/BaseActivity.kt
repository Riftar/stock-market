package com.riftar.common.view.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.riftar.common.R
import com.riftar.common.databinding.LayoutSnackbarBinding
import com.riftar.common.view.ViewConstants

/**
 * Notes this base class can be useful when we have lots of activities in our project
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initViewListener()
        observeViewModel()
    }

    open fun observeViewModel() {
    }

    open fun initViewListener() {
    }

    protected abstract fun getViewBinding(): VB


    protected fun showSuccessSnackBar(message: String) {
        showErrorSnackBar(
            message = message,
            icon = R.drawable.ic_check,
            color = R.color.green_profit
        )
    }


    @SuppressLint("RestrictedApi")
    protected fun showErrorSnackBar(
        message: String = ViewConstants.SNACKBAR_DEFAULT_MESSAGE,
        @DrawableRes icon: Int = R.drawable.ic_warning,
        @ColorRes color: Int = R.color.red_loss
    ) {
        val snackBar = Snackbar.make(
            findViewById(android.R.id.content),
            message,
            ViewConstants.SNACKBAR_DURATION
        )
        val customSnackBarBinding = LayoutSnackbarBinding.inflate(LayoutInflater.from(this))
        with(customSnackBarBinding) {
            cardViewSnackBar.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context, color
                )
            )
            ivIcon.setImageResource(icon)
            tvMessage.text = message
            ivAction.setOnClickListener {
                snackBar.dismiss()
            }
        }
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        (snackBar.view as Snackbar.SnackbarLayout).apply {
            setPadding(0, 0, 0, 0)
            addView(customSnackBarBinding.root)
        }
        snackBar.show()
    }
}