package jp.ac.it_college.std.s23019.book.manager.infrastructure.database.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object VBookWithRentalView : LongIdTable("v_book_with_rental") {
    val title = varchar("title", 128)
    val author = varchar("author", 32)
    val releaseDate = date("release_date")
    val userId = long("user_id").nullable()
    val rentalDatetime = datetime("rental_datetime").nullable()
    val renturnDeadline = datetime("return_deadline").nullable()
}