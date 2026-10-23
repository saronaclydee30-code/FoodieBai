package com.foodiebai.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodiebai.app.ui.theme.FieldGreen
import com.foodiebai.app.ui.theme.Green
import com.foodiebai.app.ui.theme.HintGray

/** A decorative circle. x/y are the centre in the 412 x 917 design frame, r is the radius in design dp. */
data class Bubble(val x: Float, val y: Float, val r: Float)

@Composable
fun BubbleBackground(bubbles: List<Bubble>, modifier: Modifier = Modifier) {
    Canvas(modifier.fillMaxSize()) {
        val scale = size.width / 412f
        bubbles.forEach { b ->
            drawCircle(
                color = Green,
                radius = b.r * scale,
                center = androidx.compose.ui.geometry.Offset(
                    b.x / 412f * size.width,
                    b.y / 917f * size.height
                )
            )
        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.heightIn(min = 48.dp).clickable(onClick = onClick, role = Role.Button).padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(36.dp)
                .background(Color.White, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Green,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(Modifier.width(8.dp))
        Text("Back", color = Green, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
    }
}

/** Map-pin logo with the delivery rider inside, plus the wordmark below. */
@Composable
fun PinLogo(width: Dp, modifier: Modifier = Modifier) {
    val height = width * 1.45f
    Box(modifier.size(width, height), contentAlignment = Alignment.TopCenter) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val r = w / 2f
            val cx = w / 2f
            val path = Path().apply {
                moveTo(cx, h)
                cubicTo(cx - r * 0.35f, h - r * 0.8f, 0f, r * 1.7f, 0f, r)
                arcTo(Rect(0f, 0f, w, 2f * r), 180f, 180f, false)
                cubicTo(w, r * 1.7f, cx + r * 0.35f, h - r * 0.8f, cx, h)
                close()
            }
            drawPath(path, Green)
        }
        Icon(
            Icons.Filled.DeliveryDining,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(top = width * 0.2f)
                .size(width * 0.65f)
        )
    }
}

@Composable
fun Wordmark(modifier: Modifier = Modifier) {
    Text(
        "FoodieBai",
        color = Green,
        fontSize = 27.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp,
        modifier = modifier
    )
}

@Composable
fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var visible by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, FieldGreen, shape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.width(48.dp).fillMaxHeight(), contentAlignment = Alignment.Center) {
            Icon(icon, hint, tint = HintGray, modifier = Modifier.size(22.dp))
        }
        Box(
            Modifier
                .width(1.dp)
                .fillMaxHeight(0.7f)
                .background(FieldGreen)
        )
        Box(
            Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(hint, color = HintGray, fontSize = 15.sp)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF17251D)),
                cursorBrush = SolidColor(Green),
                visualTransformation = if (isPassword && !visible) PasswordVisualTransformation()
                else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = hint }
            )
        }
        if (isPassword) {
            Icon(
                if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                contentDescription = if (visible) "Hide password" else "Show password",
                tint = Green,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(48.dp)
                    .clickable(role = Role.Button) { visible = !visible }
            )
        }
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Green)
            .border(BorderStroke(1.dp, Green), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick, role = Role.Button),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OutlineButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(BorderStroke(1.dp, FieldGreen), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick, role = Role.Button),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Green, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RememberMeRow(checked: Boolean, onChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier.heightIn(min = 48.dp).toggleable(value = checked, role = Role.Checkbox, onValueChange = onChange),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (checked) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
            contentDescription = null,
            tint = Green,
            modifier = Modifier.size(26.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text("Remember me", color = Green, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun UnderlinedLink(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Text(
        text,
        color = Green,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
        modifier = modifier.clickable(onClick = onClick)
    )
}

@Composable
fun SocialRow(modifier: Modifier = Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(44.dp)) {
        // Swap these letters for the official Facebook / Google / Apple vector assets when you have them.
        listOf("f", "G", "A").forEach { letter ->
            Box(
                Modifier
                    .size(46.dp)
                    .background(Green, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(letter, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}
