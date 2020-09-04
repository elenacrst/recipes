package com.weightwatchers.ww_exercise_01.ui

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.weightwatchers.ww_exercise_01.R
import com.weightwatchers.ww_exercise_01.data.ErrorCode
import com.weightwatchers.ww_exercise_01.data.Result
import com.weightwatchers.ww_exercise_01.util.Event
import com.weightwatchers.ww_exercise_01.util.toast
import timber.log.Timber


open class BaseFragment : Fragment() {
    private var currentAlertDialog: AlertDialog? = null

    private fun handleError(resultError: Result.Error): Boolean {
        return when (resultError.code) {
            ErrorCode.NO_DATA_CONNECTION.code -> {
                requireActivity().toast(getString(R.string.no_data_connection))
                true
            }
            else -> false
        }
    }

    protected fun createLoadingObserver(
            progressListener: () -> Unit = {},
            successListener: (Result<*>?) -> Unit = { },
            errorListener: () -> Unit = { }
    ): Observer<Event<Result<*>>> {
        return Observer { result ->
            when (val value = result.getContentIfNotHandled()) {
                is Result.Success -> {
                    successListener(value)
                }
                is Result.Loading -> progressListener()
                is Result.Error -> {
                    val resultError =
                            result.peekContent() as Result.Error
                    val resultHandled = handleError(resultError)

                    if (!resultHandled) {
                        requireActivity().toast(
                                message = (result.peekContent() as Result.Error).message.toString()
                        )
                    }
                    errorListener()
                }
                else -> Timber.d("Nothing to do here")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        currentAlertDialog?.dismiss()
    }
}
