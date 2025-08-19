package com.jacqulin.gainly.core.util.auth.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.CustomOutlinedTextFieldColors
import com.jacqulin.gainly.core.designsystem.theme.GrayBackground
import com.jacqulin.gainly.core.designsystem.theme.GrayText
import com.jacqulin.gainly.core.designsystem.theme.type.LocalCustomAuthTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    shape: Shape,
    label: String? = null,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focusRequester: FocusRequester = FocusRequester(),
    imeAction: ImeAction = ImeAction.Next,
    onNext: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = GrayText),
        cursorBrush = SolidColor(GrayText),
        modifier = modifier
            .focusRequester(focusRequester)
            .background(GrayBackground, shape),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext?.invoke() ?: focusManager.moveFocus(FocusDirection.Next)
            },
            onDone = {
                focusManager.clearFocus()
            }
        ),
        visualTransformation = visualTransformation
    ) { innerTextField ->

        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            placeholder = null,
            label = {
                label?.let {
                    AnimatedCustomLabel(
                        text = it,
                        interactionSource = interactionSource,
                        value = value
                    )
                }
            },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        painter = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = GrayText
                    )
                }
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            painter = trailingIcon,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = GrayText
                        )
                    }
                }
            },
            supportingText = null,
            colors = CustomOutlinedTextFieldColors,
            contentPadding = OutlinedTextFieldDefaults.contentPadding(),
            container = {
                OutlinedTextFieldDefaults.Container(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = CustomOutlinedTextFieldColors,
                    shape = shape,
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCustomLabel(
    text: String,
    interactionSource: InteractionSource,
    value: String
) {
    val focused by interactionSource.collectIsFocusedAsState()

    val hasText = value.isNotEmpty()
    val targetState = focused || hasText

    val transition = updateTransition(targetState, label = "LabelTransition")

    val scale by transition.animateFloat(label = "LabelScale") { if (it) 0.85f else 1f }

    val alpha by transition.animateFloat(label = "LabelAlpha") { if (it) 0.5f else 0.7f }

    Box(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
    ) {
        Text(
            text = text,
            style = LocalCustomAuthTypography.current.outlinedTextFieldLabel,
        )
    }
}