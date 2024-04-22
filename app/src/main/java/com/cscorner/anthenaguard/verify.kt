package com.cscorner.anthenaguard

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NavUtils


class Verify : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        // Show the Up button in the action bar.
        setupActionBar()
    }

    fun verify_no(v: View?) {
        val source_no = findViewById<View>(R.id.editText1) as EditText
        val str_source_no = source_no.getText().toString()
        val db: SQLiteDatabase
        db = openOrCreateDatabase("NumDB", MODE_PRIVATE, null)
        //	if(source_no.getText()!=null){
        db.execSQL("CREATE TABLE IF NOT EXISTS source(number VARCHAR);")
        db.execSQL("INSERT INTO source VALUES('$str_source_no');")
        Toast.makeText(applicationContext, "$str_source_no Successfully Saved", Toast.LENGTH_SHORT)
            .show()
        db.close()
        back(v)
        //		}
//		else{
//			Toast.makeText(getApplicationContext(), "Enter Your Number.",Toast.LENGTH_SHORT).show();
        //	}
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar()!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.verify, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun back(v: View?) {
        val i_back = Intent(this@Verify, MainActivity::class.java)
        startActivity(i_back)
    }
}

