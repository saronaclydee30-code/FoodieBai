package com.foodiebai.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun LoginScreen(onBack: () -> Unit, onSignUp: () -> Unit, onLoggedIn: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(Mist).systemBarsPadding().imePadding().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.fillMaxWidth().height(56.dp), contentAlignment = Alignment.CenterStart) { BackButton(onBack) }
        Spacer(Modifier.height(18.dp))
        Column(Modifier.widthIn(max = 480.dp).fillMaxWidth().clip(RoundedCornerShape(28.dp)).background(Color.White).padding(horizontal = 24.dp, vertical = 28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            BrandMark()
            Spacer(Modifier.height(22.dp))
            Text("Welcome back", color = Color(0xFF17251D), fontSize = 29.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(6.dp))
            Text("Sign in to pick up where you left off.", color = HintGray, fontSize = 15.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(28.dp))
            AuthField(username, { username = it }, "Email or username", Icons.Filled.Person)
            Spacer(Modifier.height(16.dp))
            AuthField(password, { password = it }, "Password", Icons.Filled.Lock, isPassword = true)
            Spacer(Modifier.height(24.dp))
            PrimaryButton("Sign in", onClick = {
                if (username.isBlank() || password.isBlank()) Toast.makeText(context, "Enter your email or username and password", Toast.LENGTH_SHORT).show()
                else onLoggedIn()
            })
            Spacer(Modifier.height(22.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("New to FoodieBai?", color = HintGray, fontSize = 14.sp)
                UnderlinedLink("Create an account", onSignUp)
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("GOOD FOOD, GOOD MOOD", color = Green.copy(alpha = 0.72f), fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.6.sp)
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun BrandMark() {
    Box(Modifier.size(58.dp).clip(RoundedCornerShape(19.dp)).background(Color(0xFFE7F2E9)), contentAlignment = Alignment.Center) {
        Text("F", color = Green, fontSize = 30.sp, fontWeight = FontWeight.Black)
    }
}

