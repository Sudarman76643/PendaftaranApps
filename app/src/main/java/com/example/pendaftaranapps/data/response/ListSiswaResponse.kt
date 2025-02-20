package com.example.pendaftaranapps.data.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListSiswaResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("response")
	val response: Int,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("nama")
	val nama: String?,

	@field:SerializedName("agama")
	val agama: String?,

	@field:SerializedName("id")
	val id: String?,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String?,

	@field:SerializedName("sekolah_asal")
	val sekolahAsal: String?,

	@field:SerializedName("alamat")
	val alamat: String?
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(nama)
		parcel.writeString(agama)
		parcel.writeString(id)
		parcel.writeString(jenisKelamin)
		parcel.writeString(sekolahAsal)
		parcel.writeString(alamat)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<DataItem> {
		override fun createFromParcel(parcel: Parcel): DataItem {
			return DataItem(parcel)
		}

		override fun newArray(size: Int): Array<DataItem?> {
			return arrayOfNulls(size)
		}
	}
}
