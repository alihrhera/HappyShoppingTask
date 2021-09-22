package com.taskapp.happyshoppingapp.util

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import com.taskapp.happyshoppingapp.R


/**
 *   This Class For Change App Method To
 *   **[AppStatus.loading]**
 *   Or
 *   **[AppStatus.normal]**
 * */

object AppStatus {


        private lateinit var loadingDialog: Dialog

        /**
         * Init the Dialog that need an Activity to show on It
         * */
        fun init(act: Activity) {
            loadingDialog = object : Dialog(act) {
                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContentView(R.layout.dialog_loading)
                    window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
            loadingDialog.setCancelable(false)
        }

        /**
         * Will Call This while App Is Loading Data In Background
         * will block the main screen by calling the dialog
         * **[AppStatus.loadingDialog]**
         * where it Cannot be manually canceled
         * */
        fun loading() {
            if (!loadingDialog.isShowing) loadingDialog.show()
        }


        /**
         * Will Call This while App in normal and user browsing the app
         * */
        fun normal() {
            if (loadingDialog.isShowing) loadingDialog.dismiss()
        }


    }


