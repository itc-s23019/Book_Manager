package jp.ac.it_college.std.s23019.book.manager.infrastructure.database.dao

import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.LongEntity

class VBookWithRentalEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<VBookWithRentalEntity>(VBookWithRentalView)

    var title by VBookWithRentalView.title
    var author by VBookWithRentalView.author
    var releaseDate by VBookWithRentalView.releaseDate
    var userId by VBookWithRentalView.userId
    var rentalDateTime by VBookWithRentalView.rentalDatetime
    var returnDeadline by VBookWithRentalView.renturnDeadline
}