package com.app.base.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.base.model.database.Order

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Order)

    @Query("SELECT * FROM `order`")
    fun getAllOrder(): LiveData<List<Order>>

    @Query("SELECT * FROM `order` WHERE id = :id")
    fun getOrder(id: String): LiveData<Order>

    @Query("UPDATE `order` SET  quantity = quantity+1  WHERE id = :id")
    fun updateIncreaseQuantity(id: Int)

    @Query(" UPDATE `order` SET  quantity = CASE WHEN quantity < '1' THEN  quantity ELSE quantity-1 END  WHERE id = :id")
    fun updateReduceQuantity(id: Int)

    @Query("DELETE FROM `order`WHERE id = :id")
    fun deleteOrder(id: Int)

    @Query("DELETE FROM `order`")
    fun deleteAll()

}