package com.cscorner.anthenaguard

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.EditText
import android.widget.Toast


class Register : Activity() {
    var name: EditText? = null
    var number: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //Toast.makeText(getApplicationContext(), "Activity created",Toast.LENGTH_LONG).show();
    }

    fun display(v: View?) {
        val i_view = Intent(this@Register, Display::class.java)
        startActivity(i_view)
    }

    fun instructions(v: View?) {
        val i_help = Intent(this@Register, Instructions::class.java)
        startActivity(i_help)
    }

    fun storeInDB(v: View?) {
        Toast.makeText(applicationContext, "save started", Toast.LENGTH_LONG).show()
        name = findViewById<View>(R.id.editText1) as EditText
        number = findViewById<View>(R.id.editText2) as EditText
        val str_name = name!!.getText().toString()
        val str_number = number!!.getText().toString()
        val db: SQLiteDatabase
        db = openOrCreateDatabase("NumDB", MODE_PRIVATE, null)
        //Toast.makeText(getApplicationContext(), "db created",Toast.LENGTH_LONG).show();
        db.execSQL("CREATE TABLE IF NOT EXISTS details(name VARCHAR,number VARCHAR);")
        //Toast.makeText(getApplicationContext(), "table created",Toast.LENGTH_LONG).show();
        val c = db.rawQuery("SELECT * FROM details", null)
        if (c.count < 2) {
            db.execSQL("INSERT INTO details VALUES('$str_name','$str_number');")
            Toast.makeText(applicationContext, "Successfully Saved", Toast.LENGTH_SHORT).show()
        } else {
            db.execSQL("INSERT INTO details VALUES('$str_name','$str_number');")
            Toast.makeText(
                applicationContext,
                "Maximun Numbers limited reached. Previous numbers are replaced.",
                Toast.LENGTH_SHORT
            ).show()
        }
        db.close()
    }
}