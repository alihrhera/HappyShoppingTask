package com.taskapp.happyshoppingapp.util

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.data.call_back.OnItemClick

/**
 * AppDialogs is class for contain any dialog will show in app
 * */


/**
 * show  Error  dialog
 */
fun showErrorDialog(activity: FragmentActivity, title: String? = null, errorMessage: String) {

    val askConfirmation = AlertDialog.Builder(activity)
    askConfirmation.setTitle(title ?: activity.getString(R.string.erro_title))
    askConfirmation.setNegativeButton(R.string.no, null)
    askConfirmation.setMessage(errorMessage)
    askConfirmation.show()

}


/**
 * show  Confirmation dialog to be sure that user didn't delete item by mistake
 */
fun askForConfirmation(
    activity: FragmentActivity,
    title: String? = null,
    message: String,
    onItemClick: OnItemClick
) {
    val askConfirmation = AlertDialog.Builder(activity)
    askConfirmation.setTitle(title)
    askConfirmation.setNegativeButton(R.string.no, null)
    askConfirmation.setPositiveButton(
        R.string.yes
    ) { _, _ ->
        onItemClick.onClick("")
    }
    askConfirmation.setTitle(title ?: activity.getString(R.string.deletConfirmation))

    askConfirmation.setMessage(message)
    askConfirmation.show()

}

