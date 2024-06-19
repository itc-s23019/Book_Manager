package jp.ac.it_college.std.s23019.book.manager.domain.model

import kotlinx.datetime.LocalDateTime

data class Rental(
    val bookId: Long,
    val userId: Long,
    val rentalDateTime: LocalDateTime,
    val returnDeadline: LocalDateTime,
)
