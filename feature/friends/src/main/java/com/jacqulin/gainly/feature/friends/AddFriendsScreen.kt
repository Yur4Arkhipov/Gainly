package com.jacqulin.gainly.feature.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AddFriendsScreen() {

    Column() {
        TopFriendsBar(
            text = "Добавление друзей",
            onBack = { }
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f).fillMaxSize()
        ) {
            Text("Экран добавления друзей")
        }
    }
}

/*@Composable
fun TopFriendsBar(
    onBack: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
            )
        }

        Text(
            text = text,
        )
    }
}*/

/*
@Preview(showBackground = true)
@Composable
fun TopFriendsBarPreview() {
    TopFriendsBar(
        text = "Список друзей",
        onBack = { }
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun FriendsScreenPreview() {
    FriendsScreen()
}*/
