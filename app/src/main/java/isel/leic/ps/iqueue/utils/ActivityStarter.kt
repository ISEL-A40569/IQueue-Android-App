package isel.leic.ps.iqueue.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import isel.leic.ps.iqueue.*

class ActivityStarter {

    fun startHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startSignInActivity(context: Context) {
        val intent = Intent(context, SignInActivity::class.java)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startMainActivity(context: Context, userId: Int) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("userId", userId)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startContinueWaitingConfirmationActivity(context: Context) {
        val intent = Intent(context, ContinueWaitingConfirmationActivity::class.java)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startCurrentTicketActivity(context: Context) {
        val intent = Intent(context, CurrentTicketActivity::class.java)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startOperatorsActivity(context: Context) {
        val intent = Intent(context, OperatorsActivity::class.java)
        updateIntentFlag(context, intent)
        startActivity(context, Intent(context, OperatorsActivity::class.java), null)

    }

    fun startChangePasswordActivity(context: Context) {
        val intent = Intent(context, ChangePasswordActivity::class.java)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startServiceQueuesActivity(context: Context, operatorId: Int) {
        val intent = Intent(context, ServiceQueuesActivity::class.java)
        intent.putExtra("operatorId", operatorId)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startAttendanceClassificationActivity(context: Context) {
        val intent = Intent(context, AttendanceClassificationActivity::class.java)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }

    fun startNewTicketConfirmationActivity(context: Context, serviceQueueId: Int) {
        val intent = Intent(context, NewTicketConfirmationActivity::class.java)
        intent.putExtra("serviceQueueId", serviceQueueId)
        updateIntentFlag(context, intent)
        startActivity(context, intent, null)
    }


    private fun checkIsActivityContext(context: Context): Boolean {
        if (context is Activity)
            return true

        return false
    }

    private fun updateIntentFlag(context: Context, intent: Intent) {
        if (!checkIsActivityContext(context))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}