package com.taskapp.happyshoppingapp.util

import android.app.Activity
import android.app.AlertDialog
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.data.models.Item
import com.taskapp.happyshoppingapp.data.models.OnItemClick

/**
 * AppDialogs is class for contain any dialog will show in app
 * */
class AppDialogs {


    object Instance {
        private lateinit var askConfirmation: AlertDialog.Builder

        /**
         * Init the Dialog that need an Activity to show on It
         * */
        fun init(act: Activity) {
            askConfirmation = AlertDialog.Builder(act)
            askConfirmation.setTitle(act.getString(R.string.deletConfirmation))
            askConfirmation.setNegativeButton(R.string.no, null)

        }


        /**
         * show  Confirmation dialog to be sure that user didn't delete item by mistake
         */
        fun askForConfirmation(text: String, onItemClick: OnItemClick) {
            askConfirmation.setPositiveButton(
                R.string.yes
            ) { _, _ ->
                onItemClick.onClick("")
            }

            askConfirmation.setMessage(text)
            askConfirmation.show()

        }

    }


}