package pojo

data class DateDTO(
	val readable: String? = null,
	val timestamp: String? = null,
	val gregorian: GregorianDTO? = null,
	val hijri: HijriDTO? = null
)