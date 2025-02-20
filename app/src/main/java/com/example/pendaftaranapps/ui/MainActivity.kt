package com.example.pendaftaranapps.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pendaftaranapps.data.response.DataItem
import com.example.pendaftaranapps.data.response.ListSiswaResponse
import com.example.pendaftaranapps.data.retrofit.ApiConfig
import com.example.pendaftaranapps.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rcListSiswa.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rcListSiswa.addItemDecoration(itemDecoration)

            findAllSiswa()

            binding.fabAdd.setOnClickListener {
                val intent = Intent(this@MainActivity, AddUpdateActivity::class.java)
                startActivity(intent)
            }
        }

    private fun findAllSiswa(){
        showLoading(true)
        val client = ApiConfig.getApiSevice().getAllSiswa()
        client.enqueue(object : Callback<ListSiswaResponse> {
            override fun onResponse(
                call: Call<ListSiswaResponse>,
                response: Response<ListSiswaResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        setSiswaData(responseBody.data)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ListSiswaResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()

            }

        })
    }

    private  fun setSiswaData(data: List<DataItem>) {
        val adapter = ListSiswaAdapter()
        adapter.submitList(data)
        binding.rcListSiswa.adapter = adapter

    }
    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}