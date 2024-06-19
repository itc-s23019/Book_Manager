package jp.ac.it_college.std.s23019.book.manager.presentation.config

import jp.ac.it_college.std.s23019.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s23019.book.manager.application.service.security.BookManagerUserDetailsService
import jp.ac.it_college.std.s23019.book.manager.domain.types.RoleType
import jp.ac.it_college.std.s23019.book.manager.presentation.handler.SecurityHandler
import kotlinx.coroutines.handleCoroutineException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.SecurityNamespaceHandler
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationService: AuthenticationService
) {

    @Bean
    @Order(1)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http{
            authorizeHttpRequests {
                // 認証に関する設定（1）
                authorize("/login", permitAll)
                authorize("/admin/**", hasAuthority(RoleType.ADMIN.toString()))
                authorize(anyRequest, authenticated)
            }
            formLogin {
                // 認証（ログイン）に関する設定（２）
                loginProcessingUrl = "/login"
                usernameParameter = "email"
                passwordParameter = "pass"
                authenticationSuccessHandler = SecurityHandler
                authenticationFailureHandler = SecurityHandler
            }
            csrf {
                // クロスサイトリクエストフォージェリ対策の無効化
                // REST APIでこれがあるとき利用不可になるため無効化
                disable()
            }
            exceptionHandling {
                // 未確認 or 拒否時の設定
                authenticationEntryPoint = SecurityHandler
                accessDeniedHandler = SecurityHandler
            }
            cors {
                // CORSの設定（４）。多分書かなくて大丈夫
            }
        }
        return http.build()
    }

    // パスワードエンコーダを決めるメソッド
    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder(
        16, 32, 1, 19 * 1024, 2
    )

    // 認証処理を実装したクラスの指定
    @Bean
    fun userDetailsService(): UserDetailsService =
        BookManagerUserDetailsService(authenticationService)

    @Bean
     fun corsConfigurationSource(): CorsConfigurationSource {
        // CORS の基本設定
        val config = CorsConfiguration().apply {
            // 許可するHTTPメソッドのリスト。実際使うのはGET POST PATCH
            allowedMethods = listOf(CorsConfiguration.ALL) // * (全部許可)
            // 許可するHTTP リクエストヘッダ。特に制限はしない
            allowedHeaders = listOf(CorsConfiguration.ALL) // * (全部許可)
            // アクセス元として許可する（スキーマ + ドメイン + ポート）
            allowedOrigins = listOf(
                "http://localhost:3000"
            )
            // 資格情報を受け付けるかどうか（セッションで必要になる）
            allowCredentials = true
        }
        // 基本設定を適用するリクエストパスの設定
        val source = UrlBasedCorsConfigurationSource().apply {
            // 全リクエストにCORS設定を適用
            registerCorsConfiguration("/**", config)
        }
        return source
    }
}