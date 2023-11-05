package com.example.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.utils.Constants.MOVIES_TABLE

@Entity(tableName = MOVIES_TABLE)
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var poster_path : String = "",
    var title : String = "",
    var release_date: String = ""
)
