package com.jacqulin.gainly.feature.friends.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.GrayText
import com.jacqulin.gainly.core.designsystem.theme.GreenCheckmark
import com.jacqulin.gainly.core.designsystem.theme.TextBlackColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.friends.viewmodel.AddFriendsViewModel

@Composable
fun AddFriendsScreen(
    onBackClick: () -> Unit,
    viewModel: AddFriendsViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroundMain)
    ) {
        FriendsTopBar(
            text = "Найти друга",
            textStyle = TextStyle(
                fontFamily = GainlyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                letterSpacing = 0.sp,
                color = TextBlackColor
            ),
            showBackButton = true,
            showAddFriendsButton = false,
            searchQuery = searchQuery,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onAddFriendsClick = { },
            onBackClick = onBackClick,
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            if (searchQuery.isNotEmpty() && searchResults.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults) { user ->
                        UserSearchResultRow(
                            name = user.username,
                            isRequestSent = user.isRequestSent,
                            onAddClick = {
                                viewModel.sendFriendRequest(user.username)
                                Log.d("Friends", user.username)
                            }
                        )
                    }
                }
            } else if (searchQuery.isNotEmpty() && searchResults.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Пользователей не найдено")
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Введите имя пользователя для поиска")
                }
            }
        }
    }
}

@Composable
fun UserSearchResultRow(
    name: String,
    isRequestSent: Boolean,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(

            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.W500,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.sp
                    )
                )
                Text(
                    text = "@ilusha",
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        letterSpacing = 0.sp,
                        color = GrayText
                    )
                )
            }
        }

        IconButton(
            onClick = onAddClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = GrayBackgroundMain,
                contentColor = GrayIconColor
            ),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        ) {
            Icon(
                painter = if (isRequestSent) painterResource(R.drawable.ic_checkmark)
                    else painterResource(R.drawable.ic_add_friends),
                contentDescription = "Добавить в друзья",
                tint = if (isRequestSent) GreenCheckmark
                    else GrayIconColor
            )
        }
    }
}