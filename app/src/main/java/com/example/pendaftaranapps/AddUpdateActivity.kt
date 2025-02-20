package com.example.pendaftaranapps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pendaftaranapps.data.response.AddUpdateResponse
import com.example.pendaftaranapps.data.response.DataItem
import com.example.pendaftaranapps.data.response.DeleteResponse
import com.example.pendaftaranapps.data.retrofit.ApiConfig
import com.example.pendaftaranapps.databinding.ActivityAddUpdateBinding
import com.example.pendaftaranapps.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateBinding
    private var siswa: DataItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        siswa = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, DataItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        if (siswa != null) {
            binding.btnSaveUpdate.text = getString(R.string.update)
            binding.btnDelete.visibility = View.VISIBLE

            binding.etNama.setText(siswa!!.nama)
            binding.etAlamat.setText(siswa!!.alamat)
            binding.etJk.setText(siswa!!.jenisKelamin)
            binding.etAgama.setText(siswa!!.agama)
            binding.etSekolahAsal.setText(siswa!!.sekolahAsal)

            binding.btnDelete.setOnClickListener {
                siswa?.id?.let { id ->
                    deleteSiswa(id.toInt())
                }
            }
        } else {
            binding.btnSaveUpdate.text = getString(R.string.save)
            binding.btnDelete.visibility = View.GONE
        }

        binding.btnSaveUpdate.setOnClickListener {
            saveOrUpdateSiswa()
        }
    }

    private fun saveOrUpdateSiswa() {
        val nama = binding.etNama.text.toString()
        val alamat = binding.etAlamat.text.toString()
        val jenisKelamin = binding.etJk.text.toString()
        val agama = binding.etAgama.text.toString()
        val sekolahAsal = binding.etSekolahAsal.text.toString()

        if (nama.isEmpty() || alamat.isEmpty() || jenisKelamin.isEmpty() || agama.isEmpty() || sekolahAsal.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        if (siswa == null) {
            insertSiswa(nama, alamat, jenisKelamin, agama, sekolahAsal)
        } else {
            siswa?.id?.let { id ->
                updateSiswa(id.toInt(), nama, alamat, jenisKelamin, agama, sekolahAsal)
            }
        }
    }

    private fun deleteSiswa(id: Int) {
        showLoading(true)
        val client = ApiConfig.getApiSevice().deleteSiswa(id)
        client.enqueue(object : Callback<DeleteResponse> {
            override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Toast.makeText(this@AddUpdateActivity, responseBody?.message ?: "Berhasil menghapus!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this@AddUpdateActivity, "Gagal menghapus data!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateSiswa(id: Int, nama: String, alamat: String, jenisKelamin: String, agama: String, sekolahAsal: String) {
        showLoading(true)
        val client = ApiConfig.getApiSevice().updateSiswa(id, nama, alamat, jenisKelamin, agama, sekolahAsal)
        client.enqueue(object : Callback<AddUpdateResponse> {
            override fun onResponse(call: Call<AddUpdateResponse>, response: Response<AddUpdateResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    Toast.makeText(this@AddUpdateActivity, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this@AddUpdateActivity, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun insertSiswa(nama: String, alamat: String, jenisKelamin: String, agama: String, sekolahAsal: String) {
        showLoading(true)
        val client = ApiConfig.getApiSevice().addSiswa(nama, alamat, jenisKelamin, agama, sekolahAsal)
        client.enqueue(object : Callback<AddUpdateResponse> {
            override fun onResponse(call: Call<AddUpdateResponse>, response: Response<AddUpdateResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    Toast.makeText(this@AddUpdateActivity, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this@AddUpdateActivity, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddUpdateResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@AddUpdateActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToMain() {
        val intent = Intent(this@AddUpdateActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
