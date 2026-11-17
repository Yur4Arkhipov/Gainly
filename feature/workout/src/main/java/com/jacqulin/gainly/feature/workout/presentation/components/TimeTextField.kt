package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.Red
import com.jacqulin.gainly.core.designsystem.theme.White
import com.jacqulin.gainly.feature.workout.presentation.util.isValidTimeInput

@Composable
fun TimeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    showEditIcon: Boolean = false,
    isError: Boolean = false,
    textColor: Color = Black,
    backgroundColor: Color = White,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Medium,
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }

    LaunchedEffect(value) {
        if (value != textFieldValue.text) {
            textFieldValue = TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (label != null) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = GrayTextColor
            )

            Spacer(Modifier.height(4.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    val digits = newValue.text
                        .filter(Char::isDigit)
                        .take(4)

                    if (!digits.isValidTimeInput()) return@BasicTextField

                    val formatted = when {
                        digits.length <= 2 -> digits
                        else -> "${digits.take(2)}:${digits.drop(2)}"
                    }

                    val cursorPosition = newValue.selection.start
                        .coerceIn(0, newValue.text.length)

                    val digitsBeforeCursor = newValue.text
                        .take(cursorPosition)
                        .count(Char::isDigit)
                        .coerceAtMost(digits.length)

                    val formattedCursor = when {
                        digitsBeforeCursor <= 2 -> digitsBeforeCursor
                        else -> digitsBeforeCursor + 1
                    }.coerceAtMost(formatted.length)

                    val updatedValue = TextFieldValue(
                        text = formatted,
                        selection = TextRange(formattedCursor)
                    )

                    textFieldValue = updatedValue
                    onValueChange(formatted)
                },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    fontFamily = GoogleSansFontFamily,
                    fontWeight = fontWeight,
                    fontSize = fontSize,
                    lineHeight = fontSize * 1.2,
                    letterSpacing = 0.sp,
                    color = textColor
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )

            if (showEditIcon) {
                Spacer(Modifier.width(8.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Редактировать",
                    tint = if (isError) {
                        Red
                    } else {
                        textColor.copy(alpha = 0.5f)
                    },
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
