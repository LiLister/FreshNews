package com.dream.freshnews.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Created by lixingming on 25/03/2018.
 */
object DialogHelper {

    fun showSimpleInfoDialog(manager: FragmentManager, message: String, title: String = "Info") {

        val dialog = CommonDialog()
        dialog.setTheArguments(title, message, android.R.drawable.ic_dialog_info)

        dialog.show(manager, title)
    }
}

typealias ClickHandler = () -> Unit

class CommonDialog : DialogFragment() {

//    private val KEY_TITLE = "title"
//    private val KEY_MESSAGE = "message"
//    private val KEY_ICON_ID = "icon_id"
//    private val KEY_POSITIVE_BUTTON_TEXT = "positive_button_text"
//    private val KEY_NEGATIVE_BUTTON_TEXT = "negative_button_text"
//    private val KEY_NEED_CANCEL = "need_cancel"

    private lateinit var title: String
    private lateinit var message: String
    private var iconID: Int = 0
    private lateinit var positiveButtonText: String
    private lateinit var negativeButtonText: String
    private var needCancel: Boolean = false

    private var positiveHandler: ClickHandler? = null
    private var negativeHandler: ClickHandler? = null

    fun setTheArguments(title: String, message: String, iconID: Int = 0, positiveButtonText: String = "Ok",
                        negativeButtonText: String = "Cancel", needCancel: Boolean = false) {
//        val bundle = Bundle()
////        bundle.putString(KEY_TITLE, title)
////        bundle.putString(KEY_MESSAGE, message)
////        bundle.putInt(KEY_ICON_ID, iconID)
////        bundle.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText)
////        bundle.putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText)
////        bundle.putBoolean(KEY_NEED_CANCEL, needCancel)
////
////        this.arguments = bundle

        this.title = title
        this.message = message
        this.iconID = iconID
        this.positiveButtonText = positiveButtonText
        this.negativeButtonText = negativeButtonText
        this.needCancel = needCancel
    }

    private fun setPositiveHandler(clickHandler: ClickHandler) {
        positiveHandler = clickHandler
    }

    private fun setNegativeHandler(clickHandler: ClickHandler) {
        negativeHandler = clickHandler
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
//        if (null != arguments) {
//            title = arguments.getString(KEY_TITLE)
//            message = arguments.getString(KEY_MESSAGE)
//            iconID = arguments.getInt(KEY_ICON_ID)
//            positiveButtonText = arguments.getString(KEY_POSITIVE_BUTTON_TEXT)
//            negativeButtonText = arguments.getString(KEY_NEGATIVE_BUTTON_TEXT)
//            needCancel = arguments.getBoolean(KEY_NEED_CANCEL)
//        }

        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(true)

        if (iconID != 0) {
            builder.setIcon(iconID)
        }

        builder.setPositiveButton(positiveButtonText) {
            dialog, which ->
            dialog.dismiss()
            positiveHandler?.invoke()
        }

        if (needCancel) {
            builder.setNeutralButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
                negativeHandler?.invoke()
            }
        }

        return builder.create()
    }
}