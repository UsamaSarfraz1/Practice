package com.techyasoft.practice

import android.content.ContentResolver.SCHEME_CONTENT
import android.content.ContentResolver.SCHEME_FILE
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import java.io.File

class Helper {
    fun getFromMediaUri(context: Context, uri: Uri): File?{
        if (SCHEME_FILE == uri.scheme) {
            return File(uri.path!!)
        } else if (SCHEME_CONTENT == uri.scheme){
            val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)
            val cursor =context.contentResolver.query(uri,filePathColumn,null,null,null)
            if (cursor != null && cursor.moveToFirst()){
                val columnIndex = if (uri.toString().startsWith("content://com.google.android.gallery3d"))
                    cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
                else
                    cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
                if (columnIndex != -1) {
                    val filePath = cursor.getString(columnIndex)
                    if (!TextUtils.isEmpty(filePath)) {
                        cursor.close()
                        return File(filePath)
                    }
                }
            }

        }
        return null
    }
}