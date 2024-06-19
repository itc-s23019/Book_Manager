package jp.ac.it_college.std.s23019.book.manager.infrastructure.database.dao

import jp.ac.it_college.std.s23019.book.manager.domain.types.RoleType
import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable("user") {
    val email = varchar("email", 256).uniqueIndex()
    val password = varchar("password", 128)
    val name = varchar("name", 32)
    val roleType = enumerationByName("role_type", 5, RoleType::class)
}