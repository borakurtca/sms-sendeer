package com.sf.smssender

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sf.smssender.ui.theme.SMSSenderTheme

class MainActivity : ComponentActivity() {

    private val SMS_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SMS gönderme iznini kontrol et
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), SMS_PERMISSION_CODE)
        }

        // SMS alma iznini kontrol et
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), SMS_PERMISSION_CODE)
        }

        // Ekran içeriğini ayarla
        setContent {
            SMSSenderTheme {
                SimpleMessageScreen("SMS Forwarding Service is Running")
            }
        }
    }

    // onRequestPermissionsResult methodunu override ediyoruz
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Kullanıcı izin verdi
            } else {
                // Kullanıcı izin vermedi
            }
        }
    }
}

@Composable
fun SimpleMessageScreen(message: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = androidx.compose.material3.MaterialTheme.colorScheme.background
    ) {
        Text(
            text = message,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleMessageScreenPreview() {
    SMSSenderTheme {
        SimpleMessageScreen("SMS Forwarding Service is Running")
    }
}
