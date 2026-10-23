package com.foodiebai.app.data

import androidx.compose.ui.graphics.Color

data class FoodItem(val name: String, val price: Int, val emoji: String, val tint: Color)
data class FoodCategory(val label: String, val emoji: String)

val popularItems = listOf(
    FoodItem("Fried Chicken + Fries", 105, "", Color(0xFFF4C27A)),
    FoodItem("Spicy Burger", 99, "", Color(0xFFE8A05A)),
    FoodItem("Matcha Milk Tea", 55, "", Color(0xFFB9D9A8)),
    FoodItem("Grilled Steak", 255, "", Color(0xFFC98A6B)),
    FoodItem("Chocolate Cake", 85, "", Color(0xFFD7A891)),
    FoodItem("Crispy Chicken", 120, "", Color(0xFFF2D69C))
)

val categories = listOf(
    FoodCategory("Burger", ""),
    FoodCategory("Milk Tea", ""),
    FoodCategory("Chicken", ""),
    FoodCategory("Cake", "")
)
