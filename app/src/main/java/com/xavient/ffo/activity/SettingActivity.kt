package com.xavient.ffo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.xavient.ffo.R
import com.xavient.ffo.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.layout_input.view.*

class SettingActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvServerTokenValue.text = preferencesService.serverToken
        tvDeviceTokenValue.text = preferencesService.deviceTokenToken
        ivEditServerToken.setOnClickListener(this)
        ivEditDeviceToken.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            ivEditServerToken -> {
                editServerTokenDialog()
            }
            ivEditDeviceToken -> {
                editDeviceTokenDialog()
            }
        }
    }

    private fun editServerTokenDialog() {
        val customView = layoutInflater.inflate(R.layout.layout_input, null)
        AlertDialog.Builder(this).setTitle(R.string.edit_server_token).setView(customView)
            .setNegativeButton(R.string.cancel) { _, _ ->
            }.setPositiveButton(R.string.save) { _, _ ->
                preferencesService.serverToken = customView.edtInput.text.toString()
                tvServerTokenValue.text = preferencesService.serverToken
                hideKeyboard()
            }.show()
    }

    private fun editDeviceTokenDialog() {
        val customView = layoutInflater.inflate(R.layout.layout_input, null)
        AlertDialog.Builder(this).setTitle(R.string.edit_device_token).setView(customView)
            .setNegativeButton(R.string.cancel) { _, _ ->
            }.setPositiveButton(R.string.save) { _, _ ->
                preferencesService.deviceTokenToken = customView.edtInput.text.toString()
                tvDeviceTokenValue.text = preferencesService.deviceTokenToken
                hideKeyboard()
            }.show()
    }

    override fun getLayout(): Int {
        return R.layout.activity_settings
    }

}
