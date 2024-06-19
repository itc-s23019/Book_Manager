package jp.ac.it_college.std.s23019.book.manager.domain.repository

import jp.ac.it_college.std.s23019.book.manager.domain.model.Rental

interface RentalRepository {
    fun startRental(rental: Rental)

    fun endRental(bookId: Long)
}