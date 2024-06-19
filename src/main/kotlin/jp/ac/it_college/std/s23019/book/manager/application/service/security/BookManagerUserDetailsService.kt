package jp.ac.it_college.std.s23019.book.manager.application.service.security

import jp.ac.it_college.std.s23019.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s23019.book.manager.domain.model.User
import jp.ac.it_college.std.s23019.book.manager.domain.types.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class BookManagerUserDetailsService(
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        username ?: throw UsernameNotFoundException("メールアドレスが空です")

        val user = authenticationService.findUser(username)
            ?: throw UsernameNotFoundException("$username に該当するユーザーはいません")
        return BookManagerUserDetails(user)
    }

     data class BookManagerUserDetails(
        val id: Long,
        val email: String,
        val pass: String,
        val roleType: RoleType
    ) : UserDetails {
        constructor(user: User) : this(user.id, user.email, user.password, user.roleType)

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return AuthorityUtils.createAuthorityList(roleType.toString())
        }

        override fun getPassword(): String {
            return pass
        }

        override fun getUsername(): String {
            return email
        }
    }

}