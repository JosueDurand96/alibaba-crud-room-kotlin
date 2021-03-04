package com.example.alibaba.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlibabaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlibaba(alibaba: Alibaba)

//    @Query("DELETE FROM alibaba WHERE id = :id ")
//    fun deleteAlibaba(id: Int?): Alibaba

    @Query("SELECT * FROM alibaba")
    fun getAlibaba(): List<Alibaba>

    @Query("SELECT * FROM alibaba WHERE id = :id ")
    fun getAlibaba_Id(id: Int?): Alibaba

    @Query("DELETE FROM alibaba WHERE id = :id ")
    fun eliminarAlibabaId(id: Int?)
}