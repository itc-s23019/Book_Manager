package jp.ac.it_college.std.s23019.book.manager.presentation.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession


@Configuration
@EnableRedisHttpSession
class HttpSessionConfig {
    @Bean
    fun connectionFactory(): JedisConnectionFactory {
        return JedisConnectionFactory()
    }
}