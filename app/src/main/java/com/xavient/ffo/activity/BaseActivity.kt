package com.xavient.ffo.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.xavient.ffo.FCMSenerApplication
import com.xavient.ffo.R
import com.xavient.ffo.dagger.PreferencesService
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null

    @Inject
    lateinit var preferencesService: PreferencesService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as FCMSenerApplication).getAppComponent().inject(this)
        setContentView(getLayout())

        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            val actionbar = supportActionBar
            if (actionbar != null && this !is HomeActivity) {
                actionbar.setDisplayHomeAsUpEnabled(true)
                actionbar.setHomeButtonEnabled(true)
            }
        }

    }


    abstract fun getLayout(): Int

    override fun onBackPressed() {
        super.onBackPressed()
        finishWithSlideAnimation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finishWithSlideAnimation()
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateTitle(title: String) {
        supportActionBar?.title = title
    }

    fun finishWithSlideAnimation() {
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

    }

    fun finishWithFade() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}