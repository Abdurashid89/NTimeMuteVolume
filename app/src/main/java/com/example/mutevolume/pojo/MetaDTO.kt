package pojo
data class MetaDTO(
	val latitude: Any? = null,
	val longitude: Any? = null,
	val timezone: String? = null,
	val method: MethodDTO? = null,
	val latitudeAdjustmentMethod: String? = null,
	val midnightMode: String? = null,
	val school: String? = null,
	val offset: OffsetDTO? = null
)