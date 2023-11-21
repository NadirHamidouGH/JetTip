package com.example.jettip.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon as Icon1

val IconbuttonSizeModifier = Modifier.size(40.dp)
@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: ()-> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    elevation: CardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    )
    ){


    Card(modifier = modifier
        .padding(all = 0.dp)
        .clickable { onClick.invoke() }
        .then(IconbuttonSizeModifier),
        shape = CircleShape,
        elevation = elevation
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Center
        ) {
            Icon1(

                imageVector = imageVector,
                contentDescription = "",
                tint = tint)

        }


    }

}