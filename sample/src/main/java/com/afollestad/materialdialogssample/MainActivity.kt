package com.afollestad.materialdialogssample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.extensions.message
import kotlinx.android.synthetic.main.activity_main.basic
import kotlinx.android.synthetic.main.activity_main.basicLongContent
import kotlinx.android.synthetic.main.activity_main.basicNoTitle

/** @author Aidan Follestad (afollestad) */
class MainActivity : AppCompatActivity() {

  private val debugMode = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    basicNoTitle.setOnClickListener { onClickBasicNoTitle() }
    basic.setOnClickListener { onClickBasic() }
    basicLongContent.setOnClickListener { onClickBasicLongContent() }
  }

  private fun onClickBasicNoTitle() {
    MaterialDialog(this)
        .message(textRes = R.string.shareLocationPrompt)
        .positiveButton(positiveRes = R.string.agree)
        .negativeButton(negativeRes = R.string.disagree)
        .debugMode(debugMode)
        .show()
  }

  private fun onClickBasic() {
    MaterialDialog(this)
        .title(textRes = R.string.useGoogleLocationServices)
        .message(textRes = R.string.useGoogleLocationServicesPrompt)
        .positiveButton(positiveText = "Hello World")
        .negativeButton(negativeText = "How are you doing?")
        .neutralButton(neutralText = "Testing long buttons")
        .debugMode(debugMode)
        .show()
  }

  private fun onClickBasicLongContent() {
    MaterialDialog(this)
        .title(textRes = R.string.useGoogleLocationServices)
        .message(textRes = R.string.loremIpsum)
        .positiveButton(positiveRes = R.string.agree)
        .negativeButton(negativeRes = R.string.disagree)
        .debugMode(debugMode)
        .show()
  }
}