package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;
}

class RepeatedTimeInterval(var timeInterval: TimeInterval, var times: Int)

operator fun TimeInterval.times(multiplier: Int) = RepeatedTimeInterval(this, multiplier)


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

operator fun MyDate.plus(interval: TimeInterval) = this.addTimeIntervals(interval, 1)
operator fun MyDate.plus(interval: RepeatedTimeInterval) = this.addTimeIntervals(interval.timeInterval, interval.times)

