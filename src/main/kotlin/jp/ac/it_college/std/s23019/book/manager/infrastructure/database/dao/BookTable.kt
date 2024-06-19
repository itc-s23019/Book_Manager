package jp.ac.it_college.std.s23019.book.manager.infrastructure.database.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object BookTable : LongIdTable("book"){
    val title = varchar("title", 128)
    val author = varchar("author", 32)
    val releaseDate = date("release_date")
}