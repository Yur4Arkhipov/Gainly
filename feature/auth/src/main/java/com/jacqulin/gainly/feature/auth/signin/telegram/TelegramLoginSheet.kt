package com.jacqulin.gainly.feature.auth.signin.telegram

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jacqulin.gainly.core.designsystem.theme.ModalBottomSheetContainer

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelegramLoginSheet(
    onAuthAccess: (json: String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = ModalBottomSheetContainer,
    ) {
        Box(
            modifier = Modifier
                .height(500.dp),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true

                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        setInitialScale(1)

                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        addJavascriptInterface(object {
                            @JavascriptInterface
                            fun onTelegramAuth(userJson: String) {
                                Log.d("onTelegramAuth", "userJson: $userJson")
                                onAuthAccess(userJson)
                                onDismissRequest()
                            }
                        }, "Android")

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ) = false
                        }

                        loadUrl("https://mini-app-gefee.netlify.app/tg-auth.html")
                    }
                },
                update = {}
            )
        }
    }
}