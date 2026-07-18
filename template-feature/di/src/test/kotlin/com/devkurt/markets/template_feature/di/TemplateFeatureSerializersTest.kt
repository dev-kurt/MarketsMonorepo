package com.devkurt.markets.template_feature.di

import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.template_feature.ui.api.TemplateFeatureRoute
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
class TemplateFeatureSerializersTest {

    private val serializersModule = SerializersModule {
        with(TemplateFeatureModule().templateFeatureRouteSerializers()) { register() }
    }

    @Test
    fun `TemplateFeatureRoute is registered under GraphMain`() {
        assertNotNull(serializersModule.getPolymorphic(GraphMain::class, TemplateFeatureRoute))
    }
}
