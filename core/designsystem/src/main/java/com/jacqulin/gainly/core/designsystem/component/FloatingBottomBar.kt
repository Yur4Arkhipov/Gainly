package com.jacqulin.gainly.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.BottomNavBarAddButtonContainer
import com.jacqulin.gainly.core.designsystem.theme.BottomNavBarContainer
import com.jacqulin.gainly.core.designsystem.theme.SelectedBottomBarItem
import com.jacqulin.gainly.core.designsystem.theme.UnselectedBottomBarItem

data class BottomBarItem(
    val icon: Painter,
    val contentDescription: String,
    val selected: Boolean,
    val onClick: () -> Unit
)

@Composable
fun FloatingBottomBar(
    items: List<BottomBarItem>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .height(80.dp)
            .widthIn(max = 400.dp),
        containerColor = BottomNavBarContainer.copy(alpha = 0.9f),
    ) {
        val centerIndex = items.size / 2
        items.forEachIndexed { index, item ->
            if (index == centerIndex) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(BottomNavBarAddButtonContainer)
                            .clickable { onAddClick() }
                            .height(48.dp)
                            .width(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Add",
                            tint = BottomNavBarContainer
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BottomBarNavigationItem(item)
            }
        }
    }
}

@Composable
private fun BottomBarNavigationItem(
    item: BottomBarItem
) {
    val offsetY by animateDpAsState(
        targetValue = if (item.selected) (-4).dp else 0.dp,
        label = "offset"
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(
                onClick = item.onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = item.icon,
            contentDescription = item.contentDescription,
            tint = if (item.selected) SelectedBottomBarItem
                else UnselectedBottomBarItem,
            modifier = Modifier.offset(y = offsetY)
        )

        AnimatedVisibility(item.selected) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(SelectedBottomBarItem)
            )
        }
    }
}