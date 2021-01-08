package com.app.base.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.app.base.model.database.Item
import com.app.base.model.database.Product
import com.app.base.model.database.Store
import com.app.base.model.network.records.Record
import com.app.base.model.network.records.RecordServ
import com.app.base.model.network.store.ItemNetwork
import com.app.base.model.network.store.ProductNetwork
import com.app.base.model.network.store.StoreNetwork
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun dateToStringFormat(date: String): String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val stringDate = formatter.format(format.parse(date))
        return stringDate
    }

    @JvmStatic
    fun stringFormatToString(date: String): String {

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(format.parse(date))
    }

    fun getMimeType(context: Context, uri: Uri): String? {
        val extension: String?
        extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.getContentResolver().getType(uri))
        } else {
            MimeTypeMap.getFileExtensionFromUrl(
                Uri.fromFile(File(uri.path)).toString()
            )
        }
        return extension
    }

    fun formatDate(date: String): Long {
        val myDate = date
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = sdf.parse(myDate)
        return date.time
    }

    fun recordToRecordService(record: Record): RecordServ {
        return RecordServ(
            record.title,
            record.description,
            record.start_date,
            record.next_date,
            record.vetstore_id,
            record.pet_id,
            record.record_type_id
        )
    }

    fun stringToDate(date: String): Date {

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val stringDate = formatter.format(format.parse(date))
        return formatter.parse(stringDate)
    }

    @JvmStatic
    fun dateToString(date: Date): String {

        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }

    @JvmStatic
    fun dateFormatToString(date: String): String {

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("E, dd MMMM hh:mm a")
        return formatter.format(format.parse(date))
    }

    @JvmStatic
    fun hourFormatToString(date: String): String {

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("hh:mm a")
        return formatter.format(format.parse(date))
    }

    @JvmStatic
    fun currencyFormat(number: Double): String {
        return "$ ${String.format("%,d", (number).toInt()).replace(",", ".")}"
    }

    fun itemNetworkToItem(itemNetwork: ItemNetwork): Item {
        return Item(
            itemNetwork.id, itemNetwork.price, productNetworkToProduct(itemNetwork.product),
            storeNetworkToStore(itemNetwork.vetstore)
        )

    }

    fun productNetworkToProduct(productNetwork: ProductNetwork): Product {

        return Product(
            productNetwork.id,
            productNetwork.name,
            productNetwork.description,
            productNetwork.picture_url
        )

    }

    fun storeNetworkToStore(storeNetwork: StoreNetwork): Store {

        return Store(
            storeNetwork.id,
            storeNetwork.name,
            storeNetwork.address,
            storeNetwork.phone_number,
            storeNetwork.email,
            storeNetwork.picture_url
        )
    }
}

