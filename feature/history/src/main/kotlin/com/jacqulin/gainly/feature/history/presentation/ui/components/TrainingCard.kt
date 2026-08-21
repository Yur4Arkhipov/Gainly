package com.jacqulin.gainly.feature.history.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayBackgroundMain
import com.jacqulin.gainly.core.designsystem.theme.GrayIconColor
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.history.presentation.model.TrainingInfo

@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    trainingInfoList: List<TrainingInfo>
) {

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            IconButton(
                onClick = { },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = GrayBackgroundMain,
                    contentColor = GrayIconColor
                ),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            ) {
                Column {
                    Text(
                        text = "60",
                        style = TextStyle(
                            color = Black,
                            fontFamily = GoogleSansFontFamily,
                            fontWeight = FontWeight.W500,
                            fontStyle = FontStyle.Normal,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp
                        )
                    )
                    Text(
                        text = "ккал",
                        style = TextStyle(
                            fontFamily = GoogleSansFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontSize = 8.sp,
                            letterSpacing = 0.sp
                        )
                    )
                }
            }

            IconButton(
                onClick = {
                    isExpanded = !isExpanded
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = GrayBackgroundMain,
                    contentColor = GrayIconColor
                ),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            ) {
                if (isExpanded) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_top),
                        contentDescription = "Скрыть меню",
                        tint = GrayIconColor
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_bottom),
                        contentDescription = "Раскрыть меню",
                        tint = GrayIconColor
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            trainingInfoList.forEach { trainingInfo ->
                TrainingInfo(
                    title = trainingInfo.title,
                    isExpanded = isExpanded
                ) {
                    TrainingInfoExplain()
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun TrainingCardPreview() {
//    GainlyTheme {
//        TrainingCard(
//            modifier = Modifier.width(170.dp)
//        )
//    }
//}