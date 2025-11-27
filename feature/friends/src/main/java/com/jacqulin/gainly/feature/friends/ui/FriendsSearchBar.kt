package com.jacqulin.gainly.feature.friends.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.feature.friends.viewmodel.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsSearchBar(
    searchQuery: String,
    searchResults: List<Movie>,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = it },
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onSearch = { expanded = true },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search friends") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    trailingIcon = {
                        when {
                            searchQuery.isNotEmpty() -> {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }

                            expanded -> {
                                IconButton(
                                    onClick = {
                                        expanded = false
                                        keyboardController?.hide()
                                    }
                                ) {
                                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Collapse search")
                                }
                            }
                        }
                    }
                )
            },
        ) {
            if (searchResults.isEmpty()) {
                FriendsSearchListEmptyState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(searchResults) { friend ->
                        FriendSearchInfoRow(friend.name)
                    }
                }
            }
        }
    }
}

@Composable
fun FriendSearchInfoRow(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = name)
    }
}


@Composable
fun FriendsSearchListEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No friends found",
        )
        Text(
            text = "Try adjusting your search",
        )
    }
}