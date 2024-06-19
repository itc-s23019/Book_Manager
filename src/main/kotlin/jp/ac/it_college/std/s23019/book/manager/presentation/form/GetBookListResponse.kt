package jp.ac.it_college.std.s23019.book.manager.presentation.form

import jp.ac.it_college.std.s23019.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s23019.book.manager.domain.model.Rental
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetBookListResponse(val bookList: List<BookInfo>)

@Serializable
data class BookInfo(
    val id: Long,
    val title: String,
    val author: String,
    val isRental: Boolean
){
    constructor(model: BookWithRental) : this(
        model.book.id,
        model.book.title,
        model.book.author,
        model.isRental,
    )
}

@Serializable
data class GetBookDetailResponse(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate,
    val rentalInfo: RentalInfo?
){
    constructor(model: BookWithRental) : this(
        model.book.id,
        model.book.title,
        model.book.author,
        model.book.releaseDate,
        model.rental?.let { RentalInfo(it) }
    )
}


@Serializable
data class RentalInfo(
    val userId: Long,
    val rentalDateTime: LocalDateTime,
    val returnDeadline: LocalDateTime,
) {
    constructor(model: Rental) : this(
        model.userId,
        model.rentalDateTime,
        model.returnDeadline
    )
}

@Serializable
data class RegisterBookRequest(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)

@Serializable
data class UpdateBookRequest(
    val id: Long,
    val title: String?,
    val author: String?,
    val releaseDate: LocalDate?,
)