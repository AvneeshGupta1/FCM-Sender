package com.xavient.ffo.activity

import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.xavient.ffo.R
import com.xavient.ffo.utils.visibleIf
import com.xavient.ffo.viewmodel.HomeViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_parameter.view.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.json.JSONArray
import org.json.JSONObject

class HomeActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnSendNotification.setOnClickListener(this)

        val jsonArray = JSONArray(preferencesService.keyValueJson)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            layoutParameter.addView(getParameterView(jsonObject))
        }

    }

    private fun getParameterView(keyValueObject: JSONObject): View {
        val view = layoutInflater.inflate(R.layout.layout_parameter, null)
        if (keyValueObject.has("key")) {
            val key = keyValueObject.getString("key")
            view.edtKey.setText(key)
        }
        if (keyValueObject.has("value")) {
            val key = keyValueObject.getString("value")
            view.edtValue.setText(key)
        }

        view.ivClose.visibleIf(layoutParameter.childCount != 0)
        view.ivClose.setOnClickListener {
            layoutParameter.removeView(view)
        }
        view.edtKey.addTextChangedListener(object : FCMTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                keyValueObject.put("key", s.toString())
            }
        })

        view.edtValue.addTextChangedListener(object : FCMTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                keyValueObject.put("value", s.toString())
            }
        })

        return view
    }


    override fun onClick(v: View?) {
        when (v) {
            btnSendNotification -> {

                val jsonArray = JSONArray()
                for (i in 0 until layoutParameter.childCount) {
                    val keyValueObject = JSONObject()
                    val child = layoutParameter.getChildAt(i)
                    val key = child.edtKey.text.toString()
                    keyValueObject.put("key", key)
                    val value = child.edtValue.text.toString()
                    keyValueObject.put("value", value)
                    jsonArray.put(keyValueObject)
                }

                preferencesService.keyValueJson = jsonArray.toString()

                val dialog = indeterminateProgressDialog(R.string.please_wait)
                dialog.show()

                val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
                homeViewModel.pushNotification().observe(this, {
                    dialog.dismiss()
                    Toasty.info(this, it).show()
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_setting) {
            startActivity<SettingActivity>()
            overridePendingTransition(R.anim.enter, R.anim.exit)
            return true
        }
        if (item.itemId == R.id.action_add) {
            val jsonObject = JSONObject()
            layoutParameter.addView(getParameterView(jsonObject))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayout(): Int {
        return R.layout.activity_home
    }


}
