package com.example.vocabgo.common.helpers

import android.app.Activity
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SnackbarHelper {
    private val BACKGROUND_COLOR = 0xbf323232.toInt()
    private var messageSnackbar: Snackbar? = null
    private enum class DismissBehavior { HIDE, SHOW, FINISH }
    private var maxLines = 2
    private var lastMessage = ""
    private var snackbarView: View? = null

    fun isShowing() = messageSnackbar != null

    fun showMessage(activity: Activity, message: String) {
        if (message.isNotEmpty() && (!isShowing() || lastMessage != message)) {
            lastMessage = message
            show(activity, message, DismissBehavior.HIDE)
        }
    }

    fun showMessageWithDismiss(activity: Activity, message: String) {
        show(activity, message, DismissBehavior.SHOW)
    }

    fun showMessageForShortDuration(activity: Activity, message: String) {
        show(activity, message, DismissBehavior.SHOW, Snackbar.LENGTH_SHORT)
    }

    fun showMessageForLongDuration(activity: Activity, message: String) {
        show(activity, message, DismissBehavior.SHOW, Snackbar.LENGTH_LONG)
    }

    fun showError(activity: Activity, errorMessage: String) {
        show(activity, errorMessage, DismissBehavior.FINISH)
    }

    fun hide(activity: Activity) {
        if (!isShowing()) return
        lastMessage = ""
        val messageSnackbarToHide = messageSnackbar
        messageSnackbar = null
        activity.runOnUiThread { messageSnackbarToHide?.dismiss() }
    }

    fun setMaxLines(lines: Int) {
        maxLines = lines
    }

    fun isDurationIndefinite(): Boolean {
        return isShowing() && messageSnackbar?.duration == Snackbar.LENGTH_INDEFINITE
    }

    fun setParentView(snackbarView: View?) {
        this.snackbarView = snackbarView
    }

    private fun show(activity: Activity, message: String, dismissBehavior: DismissBehavior) {
        show(activity, message, dismissBehavior, Snackbar.LENGTH_INDEFINITE)
    }

    private fun show(
        activity: Activity,
        message: String,
        dismissBehavior: DismissBehavior,
        duration: Int
    ) {
        activity.runOnUiThread {
            messageSnackbar = Snackbar.make(
                snackbarView ?: activity.findViewById(android.R.id.content),
                message,
                duration
            )
            messageSnackbar?.view?.setBackgroundColor(BACKGROUND_COLOR)
            if (dismissBehavior != DismissBehavior.HIDE && duration == Snackbar.LENGTH_INDEFINITE) {
                messageSnackbar?.setAction("Dismiss") { messageSnackbar?.dismiss() }
                if (dismissBehavior == DismissBehavior.FINISH) {
                    messageSnackbar?.addCallback(object :
                        BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            activity.finish()
                        }
                    })
                }
            }
            val textView =
                messageSnackbar?.view?.findViewById<TextView>(
                    com.google.android.material.R.id.snackbar_text
                )
            textView?.maxLines = maxLines
            messageSnackbar?.show()
        }
    }
}