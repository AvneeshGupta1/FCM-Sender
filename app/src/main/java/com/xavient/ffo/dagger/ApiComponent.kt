package com.xavient.ffo.dagger

import com.xavient.ffo.activity.BaseActivity
import com.xavient.ffo.viewmodel.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface ApiComponent {
    fun inject(loginViewModel: HomeViewModel)
    fun inject(baseActivity: BaseActivity)
}
