package dk.cphbusiness.template

import android.Manifest.permission.READ_SMS
import android.Manifest.permission.RECEIVE_SMS
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.telephony.SmsMessage
import android.widget.Toast
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.newTask

class SmsReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    val extras = intent.extras ?: return

    val pdus = extras.get("pdus") as Array<Any>
    for (pdu in pdus) {
      val sms = SmsMessage.createFromPdu(pdu as ByteArray)
      val text = """
          |Org. Adrr: ${sms.displayOriginatingAddress}
          |Message:   ${sms.messageBody}
          """.trimMargin()
      context.longToast(text)

      if (sms.messageBody.startsWith("X")) {
        abortBroadcast()
        context.startActivity(context.intentFor<MainActivity>().newTask())
        }

      }

    }

  }
