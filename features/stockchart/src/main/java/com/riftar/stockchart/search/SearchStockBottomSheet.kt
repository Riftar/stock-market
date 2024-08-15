package com.riftar.stockchart.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.riftar.common.databinding.LayoutSnackbarBinding
import com.riftar.common.view.ViewConstants
import com.riftar.stockchart.StockChartViewModel
import com.riftar.stockchart.databinding.LayoutSearchBottomSheetBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchStockBottomSheet : BottomSheetDialogFragment() {
    private var onStockSelected: (String) -> Unit = {}
    private lateinit var binding: LayoutSearchBottomSheetBinding
    private val viewModel: StockChartViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutSearchBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(
            DialogFragment.STYLE_NO_FRAME,
            com.riftar.common.R.style.RoundedTopCornerBottomSheetDialogTheme
        )
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expandBottomSheet()
        setViewListener()
        observeViewModel()
    }

    private fun expandBottomSheet() {
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {


        }
    }

    private fun changeLayoutState(isEnable: Boolean) {
        with(binding) {
            isCancelable = isEnable
            ivBack.isEnabled = isEnable
            btnSearch.isEnabled = isEnable
            IlSearch.isEnabled = isEnable
//            progressBar.showOrHide(!isEnable, View.INVISIBLE)
        }
    }

    private fun setViewListener() {
        with(binding) {
            ivBack.setOnClickListener {
                dismiss()
            }
            btnSearch.setOnClickListener {
                val text = etSearch.text.toString()
                if (text.isNotEmpty()) {
                    onStockSelected.invoke(text)
                    dismiss()
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showErrorSnackBar(
        message: String = ViewConstants.SNACKBAR_DEFAULT_MESSAGE
    ) {
        dialog?.window?.decorView?.let {
            val snackBar = Snackbar.make(it, message, ViewConstants.SNACKBAR_DURATION)
            val customSnackBarBinding =
                LayoutSnackbarBinding.inflate(LayoutInflater.from(requireContext()))
            with(customSnackBarBinding) {
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

    companion object {
        fun newInstance(onStockSelected: ((String) -> Unit)): SearchStockBottomSheet {
            return SearchStockBottomSheet().apply {
                this.onStockSelected = onStockSelected
            }
        }
    }
}