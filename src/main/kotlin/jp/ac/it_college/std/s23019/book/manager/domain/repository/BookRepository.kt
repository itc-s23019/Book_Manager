package jp.ac.it_college.std.s23019.book.manager.domain.repository

import jp.ac.it_college.std.s23019.book.manager.domain.model.Book
import jp.ac.it_college.std.s23019.book.manager.domain.model.BookWithRental
import kotlinx.datetime.LocalDate

interface BookRepository {
    fun findAlllWithRental(): List<BookWithRental>

    fun findWithRental(id: Long): BookWithRental?

    fun register(book: Book)

    fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?)

    fun delete(id: Long)
}