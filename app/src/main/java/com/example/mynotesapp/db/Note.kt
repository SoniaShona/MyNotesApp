package com.example.mynotesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note (
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "note") var note : String
) : Serializable {
    @PrimaryKey(autoGenerate = true) var id : Int=0
}