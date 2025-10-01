package com.kaninitech.salesnote.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaninitech.salesnote.model.SingleProductEntity
import com.kaninitech.salesnote.model.SingleSaleEntity


@Database(entities = [SingleSaleEntity::class, SingleProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun singleSaleDao(): SingleSaleDao
    abstract fun singleSaleProductDao(): SingleSaleProductDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun manageDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "sales_database"
                            ).fallbackToDestructiveMigration(false).build()
                INSTANCE = instance
                instance
            }
        }
    }
}