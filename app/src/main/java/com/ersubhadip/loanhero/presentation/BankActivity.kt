package com.ersubhadip.loanhero.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ersubhadip.loanhero.databinding.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import me.tankery.lib.circularseekbar.CircularSeekBar
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.roundToInt

class BankActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBankBinding
    private lateinit var viewPanBottomSheet: BottomSheetDialog
    private lateinit var viewLoadingBottomSheet: BottomSheetDialog
    private lateinit var viewTransferBottomSheet: BottomSheetDialog
    private lateinit var viewSelectionBottomSheet: BottomSheetDialog
    private lateinit var bindingPanSheet: PanSheetBinding
    private lateinit var bindingLoadingSheet: LoadingSheetBinding
    private lateinit var bindingTransferSheet: TransferSheetBinding
    private lateinit var bindingSelectionSheet: SelectionSheetBinding
    private var isDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(this@BankActivity)
        binding.progressAmount.progress = 0f

        binding.progressAmount.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                setTextAmount(progress.roundToInt())

            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {

            }

            @SuppressLint("SetTextI18n")
            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {

            }
        })


        binding.startVerification.setOnClickListener {
            if (binding.progressAmount.progress.roundToInt() != 0) {
                viewPanBottomSheet.show()

                bindingPanSheet.next.setOnClickListener {
                    if (checkPanNumber(bindingPanSheet.panInput.text.toString().trim())) {
                        viewLoadingBottomSheet.show()

                    } else {
                        showToast("Invalid Pan Number")
                    }
                }

                //todo:bottom sheet sequence
                //1 - enter pan
                //2 - loading sheet
                //3 - transfer to bank sheet - click and done

            } else {
                showToast("Please select an amount")

            }
        }

        viewPanBottomSheet.setOnCancelListener {
            if (!isDone) {
                showToast("Process Cancelled")

            }
        }
    }

    private fun initViews(context: Context) {
        //pan sheet setting
        bindingPanSheet = PanSheetBinding.inflate(layoutInflater)
        viewPanBottomSheet = BottomSheetDialog(context)
        viewPanBottomSheet.apply {
            setCancelable(true)
            setContentView(bindingPanSheet.root)
        }

        //loading sheet
        bindingLoadingSheet = LoadingSheetBinding.inflate(layoutInflater)
        viewLoadingBottomSheet = BottomSheetDialog(context)
        viewLoadingBottomSheet.apply {
            setCancelable(false)
            setContentView(bindingLoadingSheet.root)
        }

        //transfer sheet
        bindingTransferSheet =
            TransferSheetBinding.inflate(layoutInflater)
        viewTransferBottomSheet = BottomSheetDialog(context)
        viewTransferBottomSheet.apply {
            setCancelable(true)
            setContentView(bindingTransferSheet.root)

            //selection sheet
            bindingSelectionSheet =
                SelectionSheetBinding.inflate(layoutInflater)
            viewSelectionBottomSheet = BottomSheetDialog(context)
            viewSelectionBottomSheet.apply {
                setCancelable(true)
                setContentView(bindingSelectionSheet.root)

            }
        }
    }

    private fun setTextAmount(amt: Int) {
        binding.amountText.text = "₹ $amt"
    }

    fun checkPanNumber(panCardNo: String?): Boolean {
        val regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
        val p: Pattern = Pattern.compile(regex)

        if (panCardNo == null) {
            return false
        }
        val m: Matcher = p.matcher(panCardNo)
        return m.matches()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@BankActivity, msg, Toast.LENGTH_SHORT)
            .show()
    }

}