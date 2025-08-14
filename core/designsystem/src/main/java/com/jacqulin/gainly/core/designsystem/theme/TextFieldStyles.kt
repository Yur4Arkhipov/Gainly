package com.jacqulin.gainly.core.designsystem.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable

val TextInputColors: TextFieldColors
    @Composable
    get() = OutlinedTextFieldDefaults.colors(
        focusedTextColor = GrayText,
        unfocusedTextColor = GrayText,
        disabledTextColor = Red,
        errorTextColor = Red,
        focusedContainerColor = GrayBackground,
        unfocusedContainerColor = GrayBackground,
        disabledContainerColor = Red,
        errorContainerColor = Red,
        cursorColor = GrayText,
        errorCursorColor = Red,
        selectionColors = TextSelectionColors(
            handleColor = GrayHandleSelectedText,
            backgroundColor = GrayBackgroundSelectedText
        ),
        focusedBorderColor = GrayBorder,
        unfocusedBorderColor = GrayBackground,
        disabledBorderColor = Red,
        errorBorderColor = MaterialTheme.colorScheme.error,
        focusedLeadingIconColor = GrayText,
        unfocusedLeadingIconColor = GrayText,
        disabledLeadingIconColor = Red,
        errorLeadingIconColor = Red,
        focusedTrailingIconColor = GrayText,
        unfocusedTrailingIconColor = GrayText,
        disabledTrailingIconColor = Red,
        errorTrailingIconColor = Red,
        focusedLabelColor = GrayText,
        unfocusedLabelColor = GrayText,
        disabledLabelColor = Red,
        errorLabelColor = Red,
        focusedPlaceholderColor = Red,
        unfocusedPlaceholderColor = Red,
        disabledPlaceholderColor = Red,
        errorPlaceholderColor = Red,
        focusedSupportingTextColor = Red,
        unfocusedSupportingTextColor = Red,
        disabledSupportingTextColor = Red,
        errorSupportingTextColor = Red,
        focusedPrefixColor = Red,
        unfocusedPrefixColor = Red,
        disabledPrefixColor = Red,
        errorPrefixColor = Red,
        focusedSuffixColor = Red,
        unfocusedSuffixColor = Red,
        disabledSuffixColor = Red,
        errorSuffixColor = Red
    )



