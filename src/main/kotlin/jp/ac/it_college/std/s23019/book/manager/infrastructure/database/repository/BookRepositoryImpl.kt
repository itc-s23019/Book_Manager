package jp.ac.it_college.std.s23019.book.manager.infrastructure.database.repository

import jp.ac.it_college.std.s23019.book.manager.domain.model.Book
import jp.ac.it_college.std.s23019.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s23019.book.manager.domain.model.Rental
import jp.ac.it_college.std.s23019.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s23019.book.manager.infrastructure.database.dao.BookEntity
import jp.ac.it_college.std.s23019.book.manager.infrastructure.database.dao.VBookWithRentalEntity
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl : BookRepository {
    override fun findAlllWithRental(): List<BookWithRental> {
        return transaction {
            VBookWithRentalEntity.all().map (::toModel)
        }
    }

    override fun findWithRental(id: Long): BookWithRental? {
        return transaction {
            VBookWithRentalEntity.findById(id)?.let(::toModel)
        }
    }

    override fun register(book: Book) {
        transaction {
            BookEntity.new(book.id) {
                title = book.title
                author = book.author
                releaseDate = book.releaseDate
            }
        }
    }

    override fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        BookEntity.findById(id)?.apply {
            title?.let { this.title = it }
            author?.let { this.author = it }
            releaseDate?.let { this.releaseDate = it }
        }
    }

    override fun delete(id: Long) {
        BookEntity.findById(id)?.delete()
    }

    private fun toModel(entity: VBookWithRentalEntity): BookWithRental {
        val book = Book(
            entity.id.value,
            entity.title,
            entity.author,
            entity.releaseDate
        )
        val rental = entity.userId?.let { userId ->
            Rental(
                entity.id.value,
                userId,
                entity.rentalDateTime
                    ?: throw IllegalStateException("userId, is not null, but for some reason releaseDate is null."),
                entity.returnDeadline
                    ?: throw IllegalStateException("userId, is not null, but for some reason returnDeadline is null."),
            )
        }
        return BookWithRental(book, rental)
    }
}