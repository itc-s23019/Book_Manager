package jp.ac.it_college.std.s23019.book.manager.presentation.controller

import jp.ac.it_college.std.s23019.book.manager.application.service.RentalService
import jp.ac.it_college.std.s23019.book.manager.application.service.security.BookManagerUserDetailsService
import jp.ac.it_college.std.s23019.book.manager.presentation.form.RentalStartRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("rental")
@CrossOrigin
class RentalController (
    private val rentalService: RentalService
) {
    @PostMapping("/start")
    fun startRental(
        @RequestBody request: RentalStartRequest,
        @AuthenticationPrincipal user: BookManagerUserDetailsService.BookManagerUserDetails
    ) {
        rentalService.startRental(request.bookId, user.id)
    }

    @DeleteMapping("/end/{book_id}")
    fun endRental(
        @PathVariable ("book_id") bookId: Long,
        @AuthenticationPrincipal user: BookManagerUserDetailsService.BookManagerUserDetails
    )
    {
        rentalService.endRental(bookId, user.id)
    }
}