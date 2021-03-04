package com.example.alibaba.ui.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alibaba")
class Alibaba(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val nombre: String,
    val imagen: ByteArray,
    val stock: String,
    val descripcion: String,
    val favoritos: String
) {
    constructor(
        nombre: String,
        imagen: ByteArray,
        stock: String,
        descripcion: String,
        favoritos: String
    ) : this(
        0, nombre, imagen,
        stock, descripcion,
        favoritos
    )

}