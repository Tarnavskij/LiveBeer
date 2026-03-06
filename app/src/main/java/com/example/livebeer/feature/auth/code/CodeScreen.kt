package com.example.livebeer.feature.auth.code

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livebeer.core.ui.theme.LiveBeerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val Yellow = Color(0xFFFFE000)
private val YellowOff = Color(0xFFFFF59D)
private val Dark = Color(0xFF07080D)
private val Gray = Color(0xFF8E8E93)
private val Blue = Color(0xFF007AFF)
private val KeyBg = Color(0xFFF2F2F7)

@Composable
fun CodeScreen(
    onSuccess: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var code by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(47) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (timer > 0) { delay(1000); timer-- }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Blue)
            }
            Text("Назад", color = Blue, fontSize = 16.sp, modifier = Modifier.clickable { onBack() })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            Text(
                text = "Введите номер\nактивации",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Dark,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text("Мы выслали его на номер +7 (913) 210 ** **", fontSize = 14.sp, color = Gray)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            repeat(4) { i ->
                CodeCell(
                    digit = code.getOrNull(i)?.toString(),
                    isError = isError,
                    isFilled = i < code.length
                )
            }
        }

        if (isError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Неверный код", color = Color.Red, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                isLoading = true
                isError = false
                scope.launch {
                    delay(1500)
                    if (code == "1111") onSuccess() else isError = true
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(52.dp),
            enabled = code.length == 4 && !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow,
                contentColor = Dark,
                disabledContainerColor = YellowOff,
                disabledContentColor = Gray
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Dark, strokeWidth = 2.dp, modifier = Modifier.size(22.dp))
            } else {
                Text("Войти в систему", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (timer > 0) {
            Text(
                text = "Отправить код повторно можно через 00:${timer.toString().padStart(2, '0')}",
                fontSize = 13.sp,
                color = Gray
            )
        } else {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Blue)) { append("↻ Отправить код повторно") }
                },
                fontSize = 14.sp,
                modifier = Modifier.clickable { timer = 47; code = ""; isError = false }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        CodeKeyboard(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            onNumberClick = { digit ->
                if (code.length < 4) { code += digit; isError = false }
            },
            onBackspace = { if (code.isNotEmpty()) code = code.dropLast(1) }
        )
    }
}

@Composable
private fun CodeCell(digit: String?, isError: Boolean, isFilled: Boolean) {
    val lineColor = when {
        isError -> Color.Red
        isFilled -> Dark
        else -> Color(0xFFCCCCCC)
    }

    Box(
        modifier = Modifier
            .size(56.dp)
            .drawBehind {
                drawLine(
                    color = lineColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (digit != null) {
            Text(
                text = digit,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = if (isError) Color.Red else Dark
            )
        } else {
            Text("•", fontSize = 28.sp, color = Color(0xFFCCCCCC))
        }
    }
}

@Composable
private fun CodeKeyboard(
    modifier: Modifier = Modifier,
    onNumberClick: (String) -> Unit,
    onBackspace: () -> Unit
) {
    val rows = listOf(
        listOf("1" to "", "2" to "ABC", "3" to "DEF"),
        listOf("4" to "GHI", "5" to "JKL", "6" to "MNO"),
        listOf("7" to "PQRS", "8" to "TUV", "9" to "WXYZ"),
        listOf("+ * #" to "", "0" to "", "⌫" to "")
    )

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        rows.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                row.forEach { (digit, letters) ->
                    KeyboardKey(digit = digit, letters = letters, onClick = {
                        when (digit) {
                            "⌫" -> onBackspace()
                            "+ * #" -> {}
                            else -> onNumberClick(digit)
                        }
                    })
                }
            }
        }
    }
}

@Composable
private fun KeyboardKey(digit: String, letters: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 60.dp)
            .background(
                color = if (digit == "⌫" || digit == "+ * #") Color.Transparent else KeyBg,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = digit,
                fontSize = if (digit.length > 1) 16.sp else 24.sp,
                fontWeight = FontWeight.Normal,
                color = Dark
            )
            if (letters.isNotEmpty()) {
                Text(
                    text = letters,
                    fontSize = 9.sp,
                    color = Color(0xFF3C3C43).copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CodeScreenPreview() {
    LiveBeerTheme { CodeScreen() }
}