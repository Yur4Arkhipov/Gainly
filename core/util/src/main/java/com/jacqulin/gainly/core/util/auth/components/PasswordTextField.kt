package com.jacqulin.gainly.core.util.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    var hidePassword by remember { mutableStateOf(true) }
    val trailingIcon =
        if (hidePassword) R.drawable.ic_eye_close
        else R.drawable.ic_eye_close

    val visualTransformation =
        if (hidePassword) PasswordVisualTransformation()
        else VisualTransformation.None

    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        leadingIcon = painterResource(id = R.drawable.ic_lock),
        trailingIcon = painterResource(id = trailingIcon),
        onTrailingIconClick = { hidePassword = !hidePassword },
        label = "Password",
        shape = RoundedCornerShape(16.dp),
        visualTransformation = visualTransformation,
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    GainlyTheme {
        PasswordTextField(
            password = "",
            onPasswordChange = {},
        )
    }
}