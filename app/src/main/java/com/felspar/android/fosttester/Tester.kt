package com.felspar.android.fosttester

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem

import java.io.File

import kotlinx.android.synthetic.main.activity_tester.*
import kotlinx.android.synthetic.main.content_tester.*

import com.felspar.android.Setting


class Tester : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataroot = getFilesDir()
        val logloc = File(dataroot, "logs")
        logloc.mkdirs()
        Setting.fromString("getFilesDir()", "Log sinks", "Log files directory", logloc.getAbsolutePath())

        setContentView(R.layout.activity_tester)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Running...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            runTests()
            test_results.text = stringFromJNI()
        }

        test_results.text = stringFromJNI()
        test_results.movementMethod = ScrollingMovementMethod()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_tester, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun runTests(): Boolean

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
