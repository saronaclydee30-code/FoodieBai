package com.foodiebai.app.ui.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodiebai.app.ui.components.AuthField
import com.foodiebai.app.ui.components.BackButton
import com.foodiebai.app.ui.components.PrimaryButton
import com.foodiebai.app.ui.components.UnderlinedLink
import com.foodiebai.app.ui.theme.Green
import com.foodiebai.app.ui.theme.HintGray
import com.foodiebai.app.ui.theme.Mist

@Composable
fun RegisterScreen(onBack: () -> Unit, onSignIn: () -> Unit, onRegistered: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(Mist).systemBarsPadding().imePadding().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.fillMaxWidth().height(56.dp), contentAlignment = Alignment.CenterStart) { BackButton(onBack) }
        Spacer(Modifier.height(12.dp))
        Column(Modifier.widthIn(max = 480.dp).fillMaxWidth().clip(RoundedCornerShape(28.dp)).background(Color.White).padding(horizontal = 24.dp, vertical = 26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(54.dp).clip(RoundedCornerShape(18.dp)).background(Color(0xFFE7F2E9)), contentAlignment = Alignment.Center) {
                Text("F", color = Green, fontSize = 29.sp, fontWeight = FontWeight.Black)
            }
            Spacer(Modifier.height(18.dp))
            Text("Create your account", color = Color(0xFF17251D), fontSize = 27.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(6.dp))
            Text("Join us for something delicious.", color = HintGray, fontSize = 15.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(24.dp))
            AuthField(username, { username = it }, "Username", Icons.Filled.Person)
            Spacer(Modifier.height(14.dp))
            AuthField(email, { email = it }, "Email address", Icons.Filled.Email, keyboardType = KeyboardType.Email)
            Spacer(Modifier.height(14.dp))
            AuthField(password, { password = it }, "Password (6+ characters)", Icons.Filled.Lock, isPassword = true)
            Spacer(Modifier.height(22.dp))
            PrimaryButton("Create account", onClick = {
                when {
                    username.isBlank() || email.isBlank() || password.isBlank() -> Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Toast.makeText(context, "Enter a valid email address", Toast.LENGTH_SHORT).show()
                    password.length < 6 -> Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    else -> onRegistered()
                }
            })
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Already have an account?", color = HintGray, fontSize = 14.sp)
                UnderlinedLink("Sign in", onSignIn)
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("A FRESH START", color = Green.copy(alpha = 0.72f), fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.6.sp)
        Spacer(Modifier.height(24.dp))
    }
}

