package com.pablogv63.quicklock.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Credential(
    @PrimaryKey(autoGenerate = true) val credentialId: Int = 0,
    val name: String,
    val username: String,
    val password: String,
    @ColumnInfo(name = "expiration_date") val expirationDate: LocalDate?,
    @ColumnInfo(name = "last_access") val lastAccess: LocalDate,
    @ColumnInfo(name = "last_modified") val lastModified: LocalDate
)