package com.foodiebai.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.foodiebai.app.R
import com.foodiebai.app.data.FoodItem
import com.foodiebai.app.data.categories
import com.foodiebai.app.data.popularItems

private val Ink = Color(0xFF17251D)
private val Brand = Color(0xFF176B45)
private val Canvas = Color(0xFFF6F7F4)
private val Muted = Color(0xFF65736A)
private val Accent = Color(0xFFE7774B)
private val PaleGreen = Color(0xFFE8F2E9)

@Composable
fun HomeScreen() {
    var tab by remember { mutableIntStateOf(0) }
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("All") }
    val cart = remember { mutableStateMapOf<String, Int>() }
    var lastOrder by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    val itemCount = cart.values.sum()
    fun addItem(name: String) { cart[name] = (cart[name] ?: 0) + 1 }
    fun removeItem(name: String) {
        val next = (cart[name] ?: 0) - 1
        if (next <= 0) cart.remove(name) else cart[name] = next
    }

    Scaffold(
        containerColor = Canvas,
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                val labels = listOf("Home", "Menu", "Cart", "Orders", "Profile")
                val icons = listOf(Icons.Default.Home, Icons.Default.Restaurant, Icons.Default.ShoppingBag, Icons.Default.ReceiptLong, Icons.Default.Person)
                labels.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = tab == index,
                        onClick = { tab = index },
                        icon = {
                            BadgedBox(badge = { if (index == 2 && itemCount > 0) Badge { Text(itemCount.toString()) } }) {
                                Icon(icons[index], contentDescription = label)
                            }
                        },
                        label = { Text(label, fontSize = 12.sp, fontWeight = if (tab == index) FontWeight.SemiBold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = Brand, selectedTextColor = Brand, indicatorColor = PaleGreen, unselectedIconColor = Muted, unselectedTextColor = Muted)
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        Box(Modifier.fillMaxSize().padding(scaffoldPadding)) {
            when (tab) {
                0, 1 -> MenuPage(
                    isHome = tab == 0, query = query, onQuery = { query = it }, category = category,
                    onCategory = { category = it }, cart = cart, onAdd = { addItem(it.name) }, onMenu = { tab = 1 }
                )
                2 -> CartPage(
                    cart = cart,
                    onIncrease = ::addItem,
                    onDecrease = ::removeItem,
                    onBrowse = { tab = 1 },
                    onCheckout = { lastOrder = cart.toMap(); cart.clear(); tab = 3 }
                )
                3 -> OrdersPage(lastOrder, onBrowse = { tab = 1 })
                else -> ProfilePage()
            }
        }
    }
}

@Composable
private fun MenuPage(
    isHome: Boolean,
    query: String,
    onQuery: (String) -> Unit,
    category: String,
    onCategory: (String) -> Unit,
    cart: Map<String, Int>,
    onAdd: (FoodItem) -> Unit,
    onMenu: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val filtered = popularItems.filter { food ->
        food.name.contains(query.trim(), ignoreCase = true) && (category == "All" || foodCategory(food) == category)
    }
    LazyColumn(contentPadding = PaddingValues(bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item {
            Box(Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(Brand, Color(0xFF0D5438))))) {
                Box(Modifier.align(Alignment.TopEnd).padding(end = 22.dp, top = 4.dp).size(116.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.045f)))
                Box(Modifier.align(Alignment.TopEnd).padding(end = 104.dp, top = 66.dp).size(34.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.06f)))
                Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(top = 18.dp, bottom = 20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(if (isHome) "GOOD FOOD, GOOD MOOD" else "MADE FRESH FOR YOU", color = Color(0xFFCBE4D1), fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                            Spacer(Modifier.height(7.dp))
                            Text(if (isHome) "What sounds good?" else "Explore the menu", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp)
                            Spacer(Modifier.height(5.dp))
                            Text("Your next favorite is a tap away", color = Color.White.copy(alpha = 0.78f), fontSize = 13.sp)
                        }
                        Box(Modifier.size(54.dp).clip(RoundedCornerShape(18.dp)).background(Color.White), contentAlignment = Alignment.Center) {
                            Image(painterResource(R.drawable.ic_launcher), contentDescription = "FoodieBai logo", modifier = Modifier.fillMaxSize())
                        }
                    }
                    Spacer(Modifier.height(18.dp))
                    Row(Modifier.fillMaxWidth().height(54.dp).clip(RoundedCornerShape(18.dp)).background(Color.White).padding(start = 15.dp, end = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Muted)
                        Spacer(Modifier.width(10.dp))
                        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                            if (query.isEmpty()) Text("Search dishes", color = Muted, fontSize = 15.sp)
                            BasicTextField(
                                value = query,
                                onValueChange = onQuery,
                                singleLine = true,
                                textStyle = TextStyle(color = Ink, fontSize = 16.sp),
                                cursorBrush = SolidColor(Brand),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
                                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Search dishes" }
                            )
                        }
                        if (query.isNotEmpty()) IconButton(onClick = { onQuery("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search", tint = Muted)
                        }
                    }
                }
            }
        }
        if (isHome) item {
            Surface(Modifier.fillMaxWidth().padding(horizontal = 18.dp), shape = RoundedCornerShape(24.dp), color = Color.Transparent, shadowElevation = 3.dp) {
                Box(Modifier.background(Brush.linearGradient(listOf(Color(0xFFFFE6D2), Color(0xFFFFF5EA)))).padding(18.dp)) {
                    Box(Modifier.align(Alignment.TopEnd).size(102.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.35f)))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("TODAY'S LITTLE JOY", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 10.sp, letterSpacing = 1.3.sp)
                            Spacer(Modifier.height(7.dp))
                            Text("A little treat,\nmade just for you.", color = Ink, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 27.sp)
                            Spacer(Modifier.height(10.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onMenu).padding(vertical = 5.dp, horizontal = 2.dp)) {
                                Text("Explore the menu", color = Brand, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(Modifier.width(4.dp))
                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Brand, modifier = Modifier.size(17.dp))
                            }
                        }
                        Box(Modifier.padding(start = 8.dp).size(86.dp).clip(CircleShape).background(Color(0xFFFFD2B5)), contentAlignment = Alignment.Center) {
                            Text("\uD83C\uDF54", fontSize = 50.sp)
                        }
                    }
                }
            }
        }
        item {
            Column(Modifier.fillMaxWidth()) {
                Text("Find your craving", color = Ink, fontWeight = FontWeight.Bold, fontSize = 19.sp, modifier = Modifier.padding(horizontal = 18.dp))
                Text("Something delicious for every mood", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 18.dp, vertical = 3.dp))
                Spacer(Modifier.height(7.dp))
                Row(Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 18.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (listOf("All") + categories.map { it.label } + "Steak").forEach { label ->
                        FilterChip(selected = category == label, onClick = { onCategory(label) },
                            label = { Text(label, fontWeight = if (category == label) FontWeight.Bold else FontWeight.Medium) },
                            leadingIcon = { Text(categoryEmoji(label), fontSize = 15.sp) },
                            shape = RoundedCornerShape(24.dp), colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Brand, selectedLabelColor = Color.White))
                    }
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth().padding(horizontal = 18.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(if (isHome) "Popular today" else "All dishes", color = Ink, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("Made fresh when you order", color = Muted, fontSize = 13.sp)
                }
                Text("${filtered.size} items", color = Brand, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(PaleGreen).padding(horizontal = 11.dp, vertical = 7.dp))
            }
        }
        if (filtered.isEmpty()) item { EmptyState("No dishes found", "Try another search or category.") }
        items(filtered.chunked(2)) { row ->
            Row(Modifier.fillMaxWidth().padding(horizontal = 18.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
                row.forEach { food -> FoodCard(food, cart[food.name] ?: 0, Modifier.weight(1f)) { onAdd(food) } }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun FoodCard(item: FoodItem, quantity: Int, modifier: Modifier = Modifier, onAdd: () -> Unit) {
    Surface(modifier, shape = RoundedCornerShape(22.dp), color = Color.White, shadowElevation = 3.dp) {
        Column {
            Box(Modifier.fillMaxWidth().height(132.dp).background(Brush.linearGradient(listOf(item.tint.copy(alpha = 0.72f), item.tint)))) {
                Box(Modifier.align(Alignment.Center).size(104.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.20f)))
                Box(Modifier.align(Alignment.Center).size(82.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.55f)), contentAlignment = Alignment.Center) {
                    Text(foodEmoji(item), fontSize = 54.sp)
                }
                Text("★ 4.8", color = Ink, fontSize = 10.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart).padding(9.dp).clip(RoundedCornerShape(20.dp)).background(Color.White.copy(alpha = 0.94f)).padding(horizontal = 8.dp, vertical = 5.dp))
                Text(foodCategory(item).uppercase(), color = Brand, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.4.sp,
                    modifier = Modifier.align(Alignment.TopEnd).padding(9.dp).clip(RoundedCornerShape(20.dp)).background(Color.White.copy(alpha = 0.88f)).padding(horizontal = 7.dp, vertical = 5.dp))
            }
            Column(Modifier.padding(horizontal = 12.dp, vertical = 12.dp)) {
                Text(item.name, color = Ink, fontSize = 14.sp, fontWeight = FontWeight.Bold, minLines = 2, maxLines = 2, lineHeight = 18.sp)
                Spacer(Modifier.height(2.dp))
                Text("Fresh · made to order", color = Muted, fontSize = 10.sp, maxLines = 1)
                Spacer(Modifier.height(9.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("FROM", color = Muted, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
                        Text("₱${item.price}", color = Brand, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Box {
                        FilledIconButton(onClick = onAdd, modifier = Modifier.size(38.dp), shape = CircleShape, colors = IconButtonDefaults.filledIconButtonColors(containerColor = Brand, contentColor = Color.White)) {
                            Icon(Icons.Default.Add, contentDescription = "Add ${item.name} to cart", modifier = Modifier.size(20.dp))
                        }
                        if (quantity > 0) Text(quantity.toString(), color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd).offset(x = 4.dp, y = (-3).dp).clip(CircleShape).background(Accent).padding(horizontal = 5.dp, vertical = 3.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CartPage(
    cart: Map<String, Int>,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onBrowse: () -> Unit,
    onCheckout: () -> Unit
) {
    val selected = popularItems.filter { (cart[it.name] ?: 0) > 0 }
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        PageTitle("Your cart", "A delicious choice is just ahead")
        if (selected.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                EmptyState("Your cart is empty", "Pick something delicious from the menu to get started.", action = "Browse menu", onAction = onBrowse)
            }
        } else {
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(PaleGreen).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocalCafe, contentDescription = null, tint = Brand)
                Spacer(Modifier.width(10.dp))
                Text("Freshly prepared after you place your order", color = Brand, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(top = 14.dp, bottom = 12.dp)) {
                items(selected) { food ->
                    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 2.dp) {
                        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(66.dp).clip(RoundedCornerShape(15.dp)).background(Brush.linearGradient(listOf(food.tint.copy(alpha = 0.7f), food.tint))), contentAlignment = Alignment.Center) { Text(foodEmoji(food), fontSize = 36.sp) }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(food.name, color = Ink, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 2)
                                Spacer(Modifier.height(4.dp))
                                Text("₱${food.price} each", color = Muted, fontSize = 12.sp)
                                Text("₱${food.price * (cart[food.name] ?: 0)}", color = Brand, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                            QuantityControl(cart[food.name] ?: 0, onDecrease = { onDecrease(food.name) }, onIncrease = { onIncrease(food.name) })
                        }
                    }
                }
            }
            val total = selected.sumOf { it.price * (cart[it.name] ?: 0) }
            Surface(Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 16.dp), shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 2.dp) {
                Column(Modifier.padding(16.dp)) {
                    SummaryLine("Subtotal", "₱$total")
                    Spacer(Modifier.height(7.dp))
                    SummaryLine("Delivery", "Free", valueColor = Brand)
                    HorizontalDivider(Modifier.padding(vertical = 12.dp), color = Color(0xFFE8ECE8))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) { Text("Total", color = Muted, fontSize = 13.sp); Text("₱$total", color = Ink, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
                        Button(onClick = onCheckout, shape = RoundedCornerShape(14.dp), modifier = Modifier.height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Brand)) {
                            Text("Place order", fontWeight = FontWeight.Bold); Spacer(Modifier.width(6.dp)); Icon(Icons.Default.ChevronRight, null)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuantityControl(quantity: Int, onDecrease: () -> Unit, onIncrease: () -> Unit) {
    Row(Modifier.clip(RoundedCornerShape(14.dp)).background(Canvas), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDecrease, modifier = Modifier.size(40.dp)) { Icon(Icons.Default.Remove, "Remove one", tint = Brand) }
        Text(quantity.toString(), color = Ink, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        IconButton(onClick = onIncrease, modifier = Modifier.size(40.dp)) { Icon(Icons.Default.Add, "Add one", tint = Brand) }
    }
}

@Composable
private fun OrdersPage(order: Map<String, Int>, onBrowse: () -> Unit) {
    val orderedItems = popularItems.filter { (order[it.name] ?: 0) > 0 }
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        PageTitle("Your orders", "Everything you have ordered")
        if (orderedItems.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                EmptyState("No orders yet", "Your next favorite meal is waiting to be discovered.", action = "Explore menu", onAction = onBrowse)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(bottom = 24.dp)) {
                item {
                    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(22.dp), color = Color.White, shadowElevation = 1.dp) {
                        Column(Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(44.dp).clip(CircleShape).background(PaleGreen), contentAlignment = Alignment.Center) { Icon(Icons.Default.Check, null, tint = Brand) }
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) { Text("Order received", color = Ink, fontWeight = FontWeight.Bold, fontSize = 17.sp); Text("Saved on this device", color = Muted, fontSize = 13.sp) }
                                Text("NOW", color = Brand, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            }
                            HorizontalDivider(Modifier.padding(vertical = 16.dp), color = Color(0xFFE8ECE8))
                            Text("ORDER SUMMARY", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Spacer(Modifier.height(10.dp))
                            orderedItems.forEach { food ->
                                Row(Modifier.fillMaxWidth().padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("${order[food.name]} ×", color = Brand, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.width(38.dp))
                                    Text(food.name, color = Ink, fontSize = 14.sp, modifier = Modifier.weight(1f))
                                    Text("₱${food.price * (order[food.name] ?: 0)}", color = Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                            HorizontalDivider(Modifier.padding(vertical = 10.dp), color = Color(0xFFE8ECE8))
                            SummaryLine("Order total", "₱${orderedItems.sumOf { it.price * (order[it.name] ?: 0) }}", valueColor = Brand)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfilePage() {
    var selectedSection by remember { mutableStateOf<String?>(null) }
    val details = mapOf(
        "Delivery addresses" to "Address management will be available here.",
        "Payment methods" to "Secure payment options are not available in this demo yet.",
        "Dietary preferences" to "Preference controls will be available here.",
        "Help & support" to "Support details will be available here."
    )
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { PageTitle("Your profile", "Your FoodieBai space") }
        item {
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Brush.linearGradient(listOf(Brand, Color(0xFF31865C)))).padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(60.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.17f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(34.dp))
                }
                Spacer(Modifier.width(14.dp))
                Column { Text("FoodieBai member", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("Good taste starts here", color = Color(0xFFD8EDDE), fontSize = 13.sp) }
            }
        }
        item { Text("ACCOUNT", color = Muted, fontWeight = FontWeight.Bold, fontSize = 11.sp, letterSpacing = 1.2.sp, modifier = Modifier.padding(top = 10.dp, start = 4.dp)) }
        items(details.keys.toList()) { label ->
            Surface(Modifier.fillMaxWidth().clickable { selectedSection = label }, shape = RoundedCornerShape(19.dp), color = Color.White, shadowElevation = 1.dp) {
                Row(Modifier.padding(horizontal = 16.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(40.dp).clip(RoundedCornerShape(13.dp)).background(PaleGreen), contentAlignment = Alignment.Center) {
                        Icon(profileIcon(label), contentDescription = null, tint = Brand, modifier = Modifier.size(21.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(label, color = Ink, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, contentDescription = "Open $label", tint = Muted)
                }
            }
        }
        item { Text("Version 1.0 · Made with care", color = Muted, fontSize = 12.sp, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center) }
    }
    selectedSection?.let { section ->
        AlertDialog(
            onDismissRequest = { selectedSection = null },
            title = { Text(section, fontWeight = FontWeight.Bold) },
            text = { Text(details[section].orEmpty(), color = Muted) },
            confirmButton = { TextButton(onClick = { selectedSection = null }) { Text("Got it", color = Brand) } },
            containerColor = Color.White
        )
    }
}

@Composable
private fun PageTitle(title: String, subtitle: String) {
    Column(Modifier.fillMaxWidth().padding(top = 22.dp, bottom = 12.dp)) {
        Text(title, color = Ink, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, color = Muted, fontSize = 14.sp)
    }
}

@Composable
private fun SummaryLine(label: String, value: String, valueColor: Color = Ink) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Muted, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Text(value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun EmptyState(title: String, message: String, action: String? = null, onAction: (() -> Unit)? = null) {
    Column(Modifier.fillMaxWidth().padding(top = 42.dp, start = 16.dp, end = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(76.dp).clip(CircleShape).background(PaleGreen), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.ShoppingBag, null, tint = Brand, modifier = Modifier.size(32.dp))
        }
        Spacer(Modifier.height(16.dp))
        Text(title, color = Ink, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(message, color = Muted, fontSize = 14.sp, lineHeight = 20.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        if (action != null && onAction != null) {
            Spacer(Modifier.height(18.dp))
            OutlinedButton(onClick = onAction, shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = Brand)) { Text(action, fontWeight = FontWeight.Bold) }
        }
    }
}

private fun foodCategory(item: FoodItem): String = when {
    item.name.contains("burger", true) -> "Burger"
    item.name.contains("tea", true) -> "Milk Tea"
    item.name.contains("chicken", true) -> "Chicken"
    item.name.contains("cake", true) -> "Cake"
    item.name.contains("steak", true) -> "Steak"
    else -> "All"
}

private fun categoryEmoji(label: String): String = when (label) {
    "Burger" -> "\uD83C\uDF54"
    "Milk Tea" -> "\uD83E\uDDCB"
    "Chicken" -> "\uD83C\uDF57"
    "Cake" -> "\uD83C\uDF70"
    "Steak" -> "\uD83E\uDD69"
    else -> "\uD83C\uDF7D"
}

private fun foodEmoji(item: FoodItem): String = when {
    item.name.contains("burger", true) -> "\uD83C\uDF54"
    item.name.contains("tea", true) -> "\uD83E\uDDCB"
    item.name.contains("chicken", true) -> "\uD83C\uDF57"
    item.name.contains("steak", true) -> "\uD83E\uDD69"
    item.name.contains("cake", true) -> "\uD83C\uDF70"
    else -> "\uD83C\uDF7D"
}

private fun profileIcon(label: String) = when (label) {
    "Delivery addresses" -> Icons.Default.Place
    "Payment methods" -> Icons.Default.CreditCard
    "Dietary preferences" -> Icons.Default.Tune
    else -> Icons.Default.HelpOutline
}
