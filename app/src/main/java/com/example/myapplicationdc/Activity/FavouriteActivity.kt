package com.example.myapplicationdc.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationdc.Adapter.FavoriteDoctorAdapter
import com.example.myapplicationdc.Domain.DoctorModel
import com.example.myapplicationdc.R
import com.example.myapplicationdc.ViewModel.favouriteViewModel
import com.google.android.material.textview.MaterialTextView

class FavouriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: MaterialTextView
    private lateinit var adapter: FavoriteDoctorAdapter
    private var favoriteDoctors: MutableList<DoctorModel> = mutableListOf()
    private val favouriteViewModel: favouriteViewModel by viewModels()
    var patientId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        recyclerView = findViewById(R.id.recyclerView)
        emptyView = findViewById(R.id.emptyFavoriteView)

        adapter = FavoriteDoctorAdapter(this, favoriteDoctors) { doctor ->
            removeDoctorFromFavorites(doctor)
        }

        patientId = intent.getIntExtra("patientId", -1)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadFavorites()
    }

    private fun loadFavorites() {
        favoriteDoctors = favouriteViewModel.loadFavoriteDoctors(this, patientId).toMutableList()
        adapter.updateList(favoriteDoctors)
        updateEmptyView()
    }

    private fun updateEmptyView() {
        if (favoriteDoctors.isEmpty()) {
            emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun removeDoctorFromFavorites(doctor: DoctorModel) {
        favoriteDoctors = favouriteViewModel.removeDoctorFromFavorites(this, patientId, doctor).toMutableList()
        adapter.updateList(favoriteDoctors)
        updateEmptyView()
    }
}
