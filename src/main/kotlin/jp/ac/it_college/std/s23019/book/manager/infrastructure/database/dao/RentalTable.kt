package jp.ac.it_college.std.s23019.book.manager.infrastructure.database.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RentalTable : LongIdTable("rental") {
    val book = reference("book_id", BookTable)
    val user = reference("user_id", UserTable)
    val rentalDatetime = datetime("rental_datetime")
    val returnDeadline = datetime("return_deadline")
}