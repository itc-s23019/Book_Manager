package jp.ac.it_college.std.s23019.book.manager.domain.model

import jp.ac.it_college.std.s23019.book.manager.domain.types.RoleType

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val roleType: RoleType
)
