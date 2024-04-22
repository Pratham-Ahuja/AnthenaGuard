package com.cscorner.anthenaguard

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View

class Display : Activity() {
    var c: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val db: SQLiteDatabase
        db = openOrCreateDatabase("NumDB", MODE_PRIVATE, null)
        c = db.rawQuery("SELECT * FROM details", null)
        val cursorCopy = c // Create a non-null copy here
        if (cursorCopy != null && cursorCopy.count == 0) {
            showMessage("Error", "No records found.")
            return
        }
        val buffer = StringBuffer()
        while (cursorCopy!!.moveToNext()) {
            buffer.append("Name: " + cursorCopy.getString(0) + "\n")
            buffer.append("Number: " + cursorCopy.getString(1) + "\n")
        }
        showMessage("Details", buffer.toString())
        val i_startservice = Intent(this@Display, BgService::class.java)
        startService(i_startservice)
    }

    fun showMessage(title: String?, message: String?) {
        val builder = AlertDialog.Builder(this@Display)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

    fun back(v: View?) {
        val i_back = Intent(this@Display, MainActivity::class.java)
        startActivity(i_back)
    }
}