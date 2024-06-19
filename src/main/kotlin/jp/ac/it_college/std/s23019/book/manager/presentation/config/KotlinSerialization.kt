package jp.ac.it_college.std.s23019.book.manager.presentation.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class KotlinSerialization : WebMvcConfigurer {
    @OptIn(ExperimentalSerializationApi::class)
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(0, KotlinSerializationJsonHttpMessageConverter(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                namingStrategy = JsonNamingStrategy.SnakeCase
            }
        ))
    }
}