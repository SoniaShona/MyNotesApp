package com.example.mynotesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock


@Database (
    entities = [Note::class],
    version = 1
)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object {
        @Volatile private var instance : NoteDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDataBase::class.java,
            "notedatabase"
        ).build()
    }
}