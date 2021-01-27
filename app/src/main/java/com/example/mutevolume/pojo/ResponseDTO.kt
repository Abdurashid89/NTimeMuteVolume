package com.example.mutevolume.pojo

import pojo.DataDTO

data class ResponseDTO(
	val code: Int? = null,
	val status: String? = null,
	val data: List<DataDTO?>? = null
)
