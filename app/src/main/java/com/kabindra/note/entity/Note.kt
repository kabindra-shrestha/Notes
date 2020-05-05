package com.kabindra.note.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @PrimaryKey var noteId: Int,
    @ColumnInfo(name = "noteContent") var noteContent: String? = "",
    @ColumnInfo(name = "noteCreatedAt") var noteCreatedAt: Long? = 0

)