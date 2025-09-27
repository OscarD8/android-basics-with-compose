package com.example.thirtydaysrecipes.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val shapes = Shapes(
    small = RoundedCornerShape(50.dp),
    medium = RoundedCornerShape(
        bottomStart = 16.dp,
        bottomEnd = 0.dp,
        topStart = 0.dp,
        topEnd = 16.dp
    )
)