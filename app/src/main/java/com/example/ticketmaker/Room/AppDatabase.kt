package com.example.ticketmaker.Room
import android.content.Context
import android.os.AsyncTask
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ticketmaker.Class.ProductItem
import com.example.ticketmaker.Room.Dao.ProductDao
import com.example.ticketmaker.Room.Dao.TicketDao
import com.example.ticketmaker.Room.Dataclass.Product
import com.example.ticketmaker.Room.Dataclass.Ticket
import java.text.SimpleDateFormat
import java.util.*


@Database(entities = [Product::class, Ticket::class],
    version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun ticketDao(): TicketDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext, AppDatabase::class.java, "database-tickets"
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build()
                    }
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: AppDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val productDao = db?.productDao()

        private val ticketDao = db?.ticketDao()

        override fun doInBackground(vararg p0: Unit?) {
            val p1 = Product("CODE example 1", 100.5, "ejemplo 1")
            val p2 = Product("CODE example 2", 10.5, "ejemplo 2")
            val products : ArrayList<ProductItem> = ArrayList()
            productDao?.insert(p1)
            productDao?.insert(p2)
            productDao?.insert(Product("CODE example 3", 999.5, "ejemplo 3"))
            products.add(ProductItem(p1.dscr!!, "01", p1.code!!, 3, p1.price, (p1.price * 3)))
            products.add(ProductItem(p2.dscr!!, "01", p2.code!!, 3, p2.price, (p2.price * 3)))

            var total = 0.0
            for (i in 0 until products.size){
                total += products[i].subtotal
            }

            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val date = Date()

            ticketDao?.insertTicket(Ticket(
                "604",
                "S06324",
                date,
                "262776",
                12873,
                14,
                "100926843",
                "PEMAR 7",
                "00012873",
                products,
                total
            ))
        }
    }
}