package com.pablogv63.composeroomtemplate.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pablogv63.composeroomtemplate.data.data_source.example_person.ExamplePersonConverters
import com.pablogv63.composeroomtemplate.data.data_source.example_person.ExamplePersonDao
import com.pablogv63.composeroomtemplate.domain.model.ExamplePerson

@Database(
    entities = [ExamplePerson::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ExamplePersonConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun examplePersonDao(): ExamplePersonDao

    companion object {
        const val DATABASE_NAME = "compose_room_template_db"
    }
}