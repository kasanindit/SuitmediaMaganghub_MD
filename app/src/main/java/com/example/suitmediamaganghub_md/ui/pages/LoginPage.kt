package com.example.suitmediamaganghub_md.ui.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.suitmediamaganghub_md.R
import com.example.suitmediamaganghub_md.ui.common.TextFieldCustom


@Composable
fun LoginPage(onNextClick: (String) -> Unit) {

    var name by rememberSaveable { mutableStateOf("") }
    var palindrome by remember { mutableStateOf("") }
    val context = LocalContext.current

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var dialogMessage by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_gradient),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_photo_3x),
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(32.dp))

            TextFieldCustom(
                value = name,
                onValueChange = { name = it },
                placeholder = "Name"
            )

            Spacer(Modifier.height(8.dp))

            TextFieldCustom(
                value = palindrome,
                onValueChange = { palindrome = it },
                placeholder = "Palindrome"
            )

            Spacer(Modifier.height(32.dp))

            Button(onClick = {
                if (palindrome.isBlank()) {
                    dialogMessage = "Please enter a text"
                } else {
                    dialogMessage = if (isPalindrome(palindrome)) {
                        "isPalindrome"
                    } else {
                        "Not Palindrome"
                    }
                }

                showDialog = true
            }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Text("CHECK")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    if (name.isBlank()) {
                        Toast.makeText(context, "Please input your name first", Toast.LENGTH_SHORT).show()
                    } else {
                        onNextClick(name)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("NEXT")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text("Palindrome Check")
            },
            text = {
                Text(dialogMessage)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

fun isPalindrome(text: String): Boolean {
    val cleanText = text
        .filter { it.isLetterOrDigit() }
        .lowercase()

    return cleanText == cleanText.reversed()
}

@Preview(showBackground = true, widthDp = 411, heightDp = 891)
@Composable
fun LoginPagePreview() {
    LoginPage(onNextClick = {})
}