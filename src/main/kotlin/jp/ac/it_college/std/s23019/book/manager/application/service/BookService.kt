package jp.ac.it_college.std.s23019.book.manager.application.service

import jp.ac.it_college.std.s23019.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s23019.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s23019.book.manager.domain.exception.BookNotFoundException
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental>{
        return bookRepository.findAlllWithRental()
    }

    fun getDetail(bookId: Long) : BookWithRental{
        return bookRepository.findWithRental(bookId)
            ?: throw BookNotFoundException("存在しない書籍ID: $bookId")
    }
}