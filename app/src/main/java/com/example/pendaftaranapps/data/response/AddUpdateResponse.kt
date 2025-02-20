package com.example.pendaftaranapps.data.response

import com.google.gson.annotations.SerializedName

data class AddUpdateResponse(

	@field:SerializedName("data")
	val data: Data?,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("agama")
	val agama: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("sekolah_asal")
	val sekolahAsal: String,

	@field:SerializedName("alamat")
	val alamat: String
)
