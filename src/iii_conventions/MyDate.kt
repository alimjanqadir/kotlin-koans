package iii_conventions

import java.sql.Time

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval(var multiplier: Int = 1) {
    DAY(multiplier = 1),
    WEEK(multiplier = 1),
    YEAR(multiplier = 1)
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun contains(value: MyDate): Boolean = start <= value && value <= endInclusive
    override fun iterator(): Iterator<MyDate> = DataRangeIterator(this)
}

class DataRangeIterator(private val dateRange: DateRange) : Iterator<MyDate> {
    private var current: MyDate = dateRange.start

    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}

operator fun MyDate.plus(interval: TimeInterval): MyDate {
    return this.addTimeIntervals(interval, interval.multiplier)
}
