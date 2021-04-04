package com.xavient.ffo.dagger

import android.app.Application
import com.orhanobut.hawk.Hawk
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var app: Application) {
    @Singleton
    @Provides
    fun provideApplication(): Application {
        return app
    }

    @Singleton
    @Provides
    fun providePreferenceService(): PreferencesService {
        if (!Hawk.isBuilt()) Hawk.init(app).build()
        return PreferencesService()
    }
}