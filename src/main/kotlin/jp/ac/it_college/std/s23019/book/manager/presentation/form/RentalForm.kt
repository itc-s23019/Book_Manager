package jp.ac.it_college.std.s23019.book.manager.presentation.form

import kotlinx.serialization.Serializable


@Serializable
data class RentalStartRequest(
    val bookId: Long
)