package com.foodiebai.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodiebai.app.ui.components.OutlineButton
import com.foodiebai.app.ui.components.PrimaryButton
import com.foodiebai.app.ui.theme.Green
import com.foodiebai.app.ui.theme.Mist

@Composable
fun WelcomeScreen(onSignIn: () -> Unit, onCreateAccount: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(Mist).systemBarsPadding().imePadding().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(36.dp).clip(RoundedCornerShape(12.dp)).background(Green), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Restaurant, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(9.dp))
            Text("FoodieBai", color = Green, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(36.dp))
        Box(Modifier.fillMaxWidth().heightIn(min = 245.dp).clip(RoundedCornerShape(32.dp)).background(Color(0xFFE8F1E9)), contentAlignment = Alignment.Center) {
            Box(Modifier.size(186.dp).clip(CircleShape).background(Color(0xFFF5D9B8)), contentAlignment = Alignment.Center) {
                Box(Modifier.size(142.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) {
                    Box(Modifier.size(112.dp).clip(CircleShape).background(Color(0xFF85A968)), contentAlignment = Alignment.Center) {
                        Box(Modifier.size(72.dp).clip(CircleShape).background(Color(0xFFF4BD68)))
                    }
                }
            }
            Text("MADE FRESH", color = Green, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 22.dp).clip(CircleShape).background(Color.White).padding(horizontal = 14.dp, vertical = 8.dp))
            Box(Modifier.align(Alignment.TopEnd).padding(24.dp).size(42.dp).clip(CircleShape).background(Color(0xFFFFE6D7)), contentAlignment = Alignment.Center) {
                Text("+", color = Color(0xFFB75834), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
        }
        Spacer(Modifier.height(30.dp))
        Text("Good food,\nright to your door.", color = Color(0xFF17251D), fontSize = 34.sp, lineHeight = 40.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        Text("Find something delicious from local favorites and enjoy a fresh meal without the fuss.",
            color = Color(0xFF5E6D63), fontSize = 16.sp, lineHeight = 24.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(Modifier.height(28.dp))
        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(Color.White).padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            WelcomePerk("Fresh", "made to order")
            WelcomePerk("Local", "nearby favorites")
            WelcomePerk("Easy", "simple checkout")
        }
        Spacer(Modifier.height(28.dp))
        PrimaryButton("Get started", onCreateAccount)
        Spacer(Modifier.height(10.dp))
        OutlineButton("I already have an account", onSignIn)
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun WelcomePerk(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = Green, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(Modifier.height(3.dp))
        Text(subtitle, color = Color(0xFF69756D), fontSize = 10.sp)
    }
}
