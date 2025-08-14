package com.jacqulin.gainly.core.util.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
) {
    CustomTextField(
        value = email,
        onValueChange = onEmailChange,
        label = "Email",
        leadingIcon = painterResource(id = R.drawable.ic_email),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun EmailTextFieldPreview() {
    GainlyTheme {
        val value = ""
        EmailTextField(
            email = value,
            onEmailChange = { }
        )
    }
}
