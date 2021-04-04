package com.xavient.ffo.dagger

import com.xavient.ffo.utils.booleanProperty
import com.xavient.ffo.utils.stringProperty
import dagger.Module

@Module
class PreferencesService {

    var serverToken by stringProperty()
    var deviceTokenToken by stringProperty()
    var keyValueJson by stringProperty(default = "[{\"key\":\"key1\",\"value\":\"value1\"},{\"key\":\"key2\",\"value\":\"value2\"}]")
}