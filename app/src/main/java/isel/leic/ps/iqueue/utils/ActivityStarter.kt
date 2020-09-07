package isel.leic.ps.iqueue.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import isel.leic.ps.iqueue.*

class ActivityStarter() {

    fun startHomeActivity(context: Context) {
        startActivity(context, Intent(context, HomeActivity::class.java), null)
    }

    fun startSignInActivity(context: Context) {
        startActivity(context, Intent(context, SignInActivity::class.java), null)
    }

    fun startMainActivity(context: Context, userId: Int) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(context, intent, null)
    }

    fun startContinueWaitingConfirmationActivity(context: Context) {
        startActivity(context, Intent(context, ContinueWaitingConfirmationActivity::class.java), null)
    }

    fun startCurrentTicketActivity(context: Context) {
        startActivity(context, Intent(context, CurrentTicketActivity::class.java), null)
    }

    fun startOperatorsActivity(context: Context) {
        startActivity(context, Intent(context, OperatorsActivity::class.java), null)

    }

    fun startChangePasswordActivity(context: Context) {
        startActivity(context, Intent(context, ChangePasswordActivity::class.java), null)
    }

    fun startServiceQueuesActivity(context: Context, operatorId: Int) {
        val intent = Intent(context, ServiceQueuesActivity::class.java)
        intent.putExtra("operatorId", operatorId)
        startActivity(context, intent, null)
    }

    fun startAttendanceClassificationActivity(context: Context) {
        startActivity(context, Intent(context, AttendanceClassificationActivity::class.java), null)

    }

    fun startNewTicketConfirmationActivity(context: Context, serviceQueueId: Int) {
        val intent = Intent(context, NewTicketConfirmationActivity::class.java)
        intent.putExtra("serviceQueueId", serviceQueueId)
        startActivity(context, intent, null)
    }

}