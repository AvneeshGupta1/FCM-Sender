package com.xavient.ffo

import android.app.Application
import com.xavient.ffo.dagger.ApiComponent
import com.xavient.ffo.dagger.AppModule
import com.xavient.ffo.dagger.DaggerApiComponent

class FCMSenerApplication : Application() {
    private lateinit var mAppComponent: ApiComponent
    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerApiComponent.builder()
            .appModule(AppModule(this)).build()
    }


    fun getAppComponent(): ApiComponent {
        return mAppComponent
    }

}