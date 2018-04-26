package com.afollestad.materialdialogssample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.extensions.icon
import com.afollestad.materialdialogs.extensions.listItems
import com.afollestad.materialdialogs.extensions.message
import com.afollestad.materialdialogs.extensions.singleChoiceListItems
import kotlinx.android.synthetic.main.activity_main.basic
import kotlinx.android.synthetic.main.activity_main.basic_buttons
import kotlinx.android.synthetic.main.activity_main.basic_icon
import kotlinx.android.synthetic.main.activity_main.basic_long
import kotlinx.android.synthetic.main.activity_main.basic_long_buttons
import kotlinx.android.synthetic.main.activity_main.basic_long_titled
import kotlinx.android.synthetic.main.activity_main.basic_long_titled_buttons
import kotlinx.android.synthetic.main.activity_main.basic_titled
import kotlinx.android.synthetic.main.activity_main.basic_titled_buttons
import kotlinx.android.synthetic.main.activity_main.buttons_neutral
import kotlinx.android.synthetic.main.activity_main.buttons_stacked
import kotlinx.android.synthetic.main.activity_main.list
import kotlinx.android.synthetic.main.activity_main.list_buttons
import kotlinx.android.synthetic.main.activity_main.list_checkPrompt
import kotlinx.android.synthetic.main.activity_main.list_checkPrompt_buttons
import kotlinx.android.synthetic.main.activity_main.list_long
import kotlinx.android.synthetic.main.activity_main.list_long_buttons
import kotlinx.android.synthetic.main.activity_main.list_long_items
import kotlinx.android.synthetic.main.activity_main.list_long_items_buttons
import kotlinx.android.synthetic.main.activity_main.list_long_items_titled
import kotlinx.android.synthetic.main.activity_main.list_long_items_titled_buttons
import kotlinx.android.synthetic.main.activity_main.list_long_press
import kotlinx.android.synthetic.main.activity_main.list_long_titled
import kotlinx.android.synthetic.main.activity_main.list_long_titled_buttons
import kotlinx.android.synthetic.main.activity_main.list_titled
import kotlinx.android.synthetic.main.activity_main.list_titled_buttons
import kotlinx.android.synthetic.main.activity_main.multiple_choice
import kotlinx.android.synthetic.main.activity_main.multiple_choice_disabled_items
import kotlinx.android.synthetic.main.activity_main.multiple_choice_limit
import kotlinx.android.synthetic.main.activity_main.multiple_choice_long_items
import kotlinx.android.synthetic.main.activity_main.multiple_choice_min
import kotlinx.android.synthetic.main.activity_main.single_choice
import kotlinx.android.synthetic.main.activity_main.single_choice_buttons
import kotlinx.android.synthetic.main.activity_main.single_choice_disabled_items
import kotlinx.android.synthetic.main.activity_main.single_choice_long_items

/** @author Aidan Follestad (afollestad) */
class MainActivity : AppCompatActivity() {

  private val debugMode = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    basic.setOnClickListener {
      MaterialDialog(this)
          .message(textRes = R.string.shareLocationPrompt)
          .debugMode(debugMode)
          .show()
    }

    basic_titled.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.useGoogleLocationServices)
          .message(textRes = R.string.useGoogleLocationServicesPrompt)
          .debugMode(debugMode)
          .show()
    }

    basic_buttons.setOnClickListener {
      MaterialDialog(this)
          .message(textRes = R.string.useGoogleLocationServicesPrompt)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    basic_titled_buttons.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.useGoogleLocationServices)
          .message(textRes = R.string.useGoogleLocationServicesPrompt)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    basic_long.setOnClickListener {
      MaterialDialog(this)
          .message(textRes = R.string.loremIpsum)
          .debugMode(debugMode)
          .show()
    }

    basic_long_titled.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.useGoogleLocationServices)
          .message(textRes = R.string.loremIpsum)
          .debugMode(debugMode)
          .show()
    }

    basic_long_buttons.setOnClickListener {
      MaterialDialog(this)
          .message(textRes = R.string.loremIpsum)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    basic_long_titled_buttons.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.useGoogleLocationServices)
          .message(textRes = R.string.loremIpsum)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    basic_icon.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.useGoogleLocationServices)
          .icon(iconRes = R.mipmap.ic_launcher)
          .message(textRes = R.string.loremIpsum)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    // TODO checkbox prompt dialogs

    list.setOnClickListener {
      MaterialDialog(this)
          .listItems(arrayRes = R.array.socialNetworks)
          .debugMode(debugMode)
          .show()
    }

    list_buttons.setOnClickListener {
      MaterialDialog(this)
          .listItems(arrayRes = R.array.socialNetworks)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    list_titled.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.socialNetworks)
          .listItems(arrayRes = R.array.socialNetworks)
          .debugMode(debugMode)
          .show()
    }

    list_titled_buttons.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.socialNetworks)
          .listItems(arrayRes = R.array.socialNetworks)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    list_long.setOnClickListener {
      MaterialDialog(this)
          .listItems(arrayRes = R.array.states)
          .debugMode(debugMode)
          .show()
    }

    list_long_buttons.setOnClickListener {
      MaterialDialog(this)
          .listItems(arrayRes = R.array.states)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    list_long_titled.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.states)
          .listItems(arrayRes = R.array.states)
          .debugMode(debugMode)
          .show()
    }

    list_long_titled_buttons.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.states)
          .listItems(arrayRes = R.array.states)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    list_long_items.setOnClickListener {
      MaterialDialog(this)
          .listItems(arrayRes = R.array.socialNetworks_longItems)
          .debugMode(debugMode)
          .show()
    }

    list_long_items_buttons.setOnClickListener {
      MaterialDialog(this)
          .listItems(arrayRes = R.array.socialNetworks_longItems)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    list_long_items_titled.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.socialNetworks)
          .listItems(arrayRes = R.array.socialNetworks_longItems)
          .debugMode(debugMode)
          .show()
    }

    list_long_items_titled_buttons.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.socialNetworks)
          .listItems(arrayRes = R.array.socialNetworks_longItems)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .debugMode(debugMode)
          .show()
    }

    list_checkPrompt.setOnClickListener {
      // TODO
    }

    list_checkPrompt_buttons.setOnClickListener {
      // TODO
    }

    list_long_press.setOnClickListener {
      // TODO
    }

    single_choice.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.socialNetworks)
          .singleChoiceListItems(arrayRes = R.array.socialNetworks, initialSelection = 1)
          .debugMode(debugMode)
          .show()
    }

    single_choice_buttons.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.socialNetworks)
          .singleChoiceListItems(arrayRes = R.array.socialNetworks, initialSelection = 2)
          .positiveButton(positiveRes = R.string.choose)
          .debugMode(debugMode)
          .show()
    }

    single_choice_long_items.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.socialNetworks)
          .singleChoiceListItems(arrayRes = R.array.socialNetworks_longItems)
          .positiveButton(positiveRes = R.string.choose)
          .debugMode(debugMode)
          .show()
    }

    single_choice_disabled_items.setOnClickListener {
      // TODO
    }

    multiple_choice.setOnClickListener {
      // TODO
    }

    multiple_choice_limit.setOnClickListener {
      // TODO
    }

    multiple_choice_min.setOnClickListener {
      // TODO
    }

    multiple_choice_long_items.setOnClickListener {
      // TODO
    }

    multiple_choice_disabled_items.setOnClickListener {
      // TODO
    }

    // TODO

    buttons_stacked.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.useGoogleLocationServices)
          .message(textRes = R.string.useGoogleLocationServicesPrompt)
          .positiveButton(positiveText = "Hello World")
          .negativeButton(negativeText = "How are you doing?")
          .neutralButton(neutralText = "Testing long buttons")
          .debugMode(debugMode)
          .show()
    }

    buttons_neutral.setOnClickListener {
      MaterialDialog(this)
          .title(textRes = R.string.useGoogleLocationServices)
          .message(textRes = R.string.useGoogleLocationServicesPrompt)
          .positiveButton(positiveRes = R.string.agree)
          .negativeButton(negativeRes = R.string.disagree)
          .neutralButton(neutralRes = R.string.more_info)
          .debugMode(debugMode)
          .show()
    }
  }
}