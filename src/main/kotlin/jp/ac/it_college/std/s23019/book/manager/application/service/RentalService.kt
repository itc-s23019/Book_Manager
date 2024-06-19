package jp.ac.it_college.std.s23019.book.manager.application.service

import jp.ac.it_college.std.s23019.book.manager.domain.exception.BookNotAvailableException
import jp.ac.it_college.std.s23019.book.manager.domain.exception.RentalStateException
import jp.ac.it_college.std.s23019.book.manager.domain.model.Rental
import jp.ac.it_college.std.s23019.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s23019.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s23019.book.manager.domain.repository.UserRepository
import kotlinx.datetime.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


private const val RENTAL_TERM_DAYS = 14L

@Service
class RentalService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val rentalRepository: RentalRepository,
){
    @Transactional
    fun startRental(bookId: Long, userId: Long) {
        // ユーザーが存在するか確認
        userRepository.find(userId)
            ?: RentalStateException("該当するユーザーがいません")
        // 本が存在するか確認
        val book = bookRepository.findWithRental(bookId)
            ?: throw RentalStateException("該当する書籍がありません")
        // 貸し出し中チェック
        if (book.isRental) {
            throw BookNotAvailableException("貸し出し中です")
        }

        val current = Clock.System.now()
        val rentalDateTime = current.toLocalDateTime(TimeZone.currentSystemDefault())
        val returnDeadline = current.plus(
            RENTAL_TERM_DAYS, DateTimeUnit.DAY, TimeZone.currentSystemDefault()
        ).toLocalDateTime(TimeZone.currentSystemDefault())
        val rental = Rental(bookId, userId, rentalDateTime, returnDeadline)

        rentalRepository.startRental(rental)
    }

    @Transactional
    fun endRental(bookId: Long, userId: Long) {
        userRepository.find(userId)
            ?: throw RentalStateException("該当するユーザーが存在しません userId: ${userId}")
        val book = bookRepository.findWithRental(bookId)
            ?: throw RentalStateException("該当する書籍が存在しません bookId: ${bookId}")

        // 貸出中のチェック
        if (!book.isRental) {
            throw RentalStateException("未貸出の商品です bookId: ${bookId}")
        }
        if (book.rental!!.userId != userId) {
            throw RentalStateException("他のユーザーが貸出中の商品です userId: ${bookId}")
        }
        rentalRepository.endRental(bookId)
    }
}