package com.jacqulin.gainly.feature.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.Black
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.core.designsystem.theme.GrayTextColor
import com.jacqulin.gainly.core.designsystem.theme.Red
import com.jacqulin.gainly.core.designsystem.theme.White


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    isError: Boolean = false,
    textColor: Color = Black,
    backgroundColor: Color = White,
    showEditIcon: Boolean = false,
    maxLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Medium,
) {
    var isEditing by remember { mutableStateOf(false) }

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }

    var hadFocus by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(value) {
        if (!isEditing && textFieldValue.text != value) {
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
            if (!showEditIcon || isEditing) {
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        if (newValue.text.length <= maxLength) {
                            textFieldValue = newValue
                            onValueChange(newValue.text)
                        }
                    },
                    textStyle = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = fontWeight,
                        fontSize = fontSize,
                        lineHeight = fontSize * 1.2,
                        letterSpacing = 0.sp,
                        color = textColor
                    ),
                    cursorBrush = SolidColor(textColor),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (showEditIcon) {
                                isEditing = false
                            }
                            keyboardController?.hide()
                        }
                    ),
                    maxLines = maxLines,
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (showEditIcon) {
                                Modifier
                                    .focusRequester(focusRequester)
                                    .onFocusChanged { focusState ->
                                        if (focusState.isFocused) {
                                            hadFocus = true
                                        } else if (hadFocus) {
                                            hadFocus = false
                                            isEditing = false
                                        }
                                    }
                            } else {
                                Modifier
                            }
                        )
                )
            } else {
                Text(
                    text = value,
                    style = TextStyle(
                        fontFamily = GoogleSansFontFamily,
                        fontWeight = fontWeight,
                        fontSize = fontSize,
                        lineHeight = fontSize * 1.2,
                        letterSpacing = 0.sp,
                        color = textColor
                    ),
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

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
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            textFieldValue = TextFieldValue(
                                text = value,
                                selection = TextRange(value.length)
                            )
                            isEditing = true
                        }
                )
            }
        }
    }
}
