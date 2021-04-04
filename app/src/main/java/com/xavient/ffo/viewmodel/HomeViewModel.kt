package com.xavient.ffo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.xavient.ffo.FCMSenerApplication
import com.xavient.ffo.dagger.PreferencesService
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.inject.Inject


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var preferencesService: PreferencesService


    init {
        (application as FCMSenerApplication).getAppComponent().inject(this)
    }


    fun pushNotification(): MutableLiveData<String> {
        val messageResponse: MutableLiveData<String> = MutableLiveData()
        doAsync {
            try {
                val token = ArrayList<String>()
                token.add(preferencesService.deviceTokenToken)
                val json = JSONObject()
                json.put("registration_ids", JSONArray(token))
                val info = JSONObject()

                val jsonArray = JSONArray(preferencesService.keyValueJson)

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    var key = ""
                    var value = ""
                    if (obj.has("key")) {
                        key = obj.getString("key")
                    }
                    if (obj.has("value")) {
                        value = obj.getString("value")
                    }
                    info.put(key, value)
                }
                json.put("data", info)

                val url = URL("https://fcm.googleapis.com/fcm/send")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Authorization","key=" + preferencesService.serverToken)
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                val outputStream = conn.outputStream
                outputStream.write(json.toString().toByteArray())
                val inputStream = conn.inputStream
                val resp = convertStreamToString(inputStream)
                val jsonObj = JSONObject(resp)
                val success = jsonObj.getString("success")
                if (success=="1"){
                    messageResponse.postValue("Message send success")
                }else{
                    messageResponse.postValue("Message send failure")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return messageResponse
    }


    private fun convertStreamToString(`is`: InputStream): String {
        val s = Scanner(`is`).useDelimiter("\\A")
        return if (s.hasNext()) s.next().replace(",", ",\n") else ""
    }


}