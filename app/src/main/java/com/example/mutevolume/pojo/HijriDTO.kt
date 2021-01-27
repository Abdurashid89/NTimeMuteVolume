package pojo
data class HijriDTO(
	val date: String? = null,
	val format: String? = null,
	val day: String? = null,
	val weekday: WeekdayDTO? = null,
	val month: MonthDTO? = null,
	val year: String? = null,
	val designation: DesignationDTO? = null,
	val holidays: List<Any?>? = null
)