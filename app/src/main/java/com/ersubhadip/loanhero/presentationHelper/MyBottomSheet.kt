package com.ersubhadip.loanhero.presentationHelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ersubhadip.loanhero.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MyBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pan_sheet, container, false)
    }

}