package com.example.livebeer.feature.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livebeer.core.ui.theme.LiveBeerTheme

private val YellowPrimary  = Color(0xFFFFE000)
private val YellowDisabled = Color(0xFFFFF59D)
private val TextPrimary    = Color(0xFF07080D)
private val TextGray       = Color(0xFF8E8E93)
private val BlueLink       = Color(0xFF007AFF)
private val ErrorRed       = Color(0xFFFF3B30)
private val FieldBorder    = Color(0xFFE0E0E0)
private val FieldBorderErr = Color(0xFFFF3B30)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    var phone by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }

    var phoneError by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val isFormValid = phone.isNotBlank() && name.isNotBlank() &&
            birthDate.isNotBlank() && agreed

    // ── DatePicker ────────────────────────────────────────
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val cal = java.util.Calendar.getInstance().apply {
                            timeInMillis = millis
                        }
                        val day   = cal.get(java.util.Calendar.DAY_OF_MONTH)
                        val month = cal.get(java.util.Calendar.MONTH) + 1
                        val year  = cal.get(java.util.Calendar.YEAR)
                        birthDate = "%02d.%02d.%04d".format(day, month, year)
                    }
                    showDatePicker = false
                }) {
                    Text("Готово", color = BlueLink)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Отмена", color = BlueLink)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Top bar ──────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = BlueLink
                )
            }
            Text(
                text = "Назад",
                color = BlueLink,
                fontSize = 16.sp,
                modifier = Modifier.clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // ── Заголовок ─────────────────────────────────
            Text(
                text = "Регистрация\nаккаунта",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Заполните поля данных ниже",
                fontSize = 14.sp,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── Телефон ───────────────────────────────────
            Text(
                text = "Номер телефона",
                fontSize = 13.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = false
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("+7", color = TextGray) },
                isError = phoneError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TextPrimary,
                    unfocusedBorderColor = FieldBorder,
                    errorBorderColor = FieldBorderErr
                )
            )
            if (phoneError) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Ошибка номера", color = ErrorRed, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Имя ───────────────────────────────────────
            Text(
                text = "Ваше имя",
                fontSize = 13.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Введите имя", color = TextGray) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TextPrimary,
                    unfocusedBorderColor = FieldBorder
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── Дата рождения ─────────────────────────────
            Text(
                text = "Дата рождения",
                fontSize = 13.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = birthDate,
                onValueChange = { },   // только через DatePicker
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                placeholder = { Text("ДД.ММ.ГГГГ", color = TextGray) },
                readOnly = true,
                enabled = false,      // делаем кликабельным через Box, disable убирает фокус
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = FieldBorder,
                    disabledTextColor = TextPrimary,
                    disabledPlaceholderColor = TextGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Чекбокс согласия ──────────────────────────
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = agreed,
                    onCheckedChange = { agreed = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = YellowPrimary,
                        checkmarkColor = TextPrimary,
                        uncheckedColor = TextGray
                    ),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Я согласен с ")
                        withStyle(SpanStyle(color = BlueLink)) {
                            append("условиями обработки персональных данных")
                        }
                    },
                    fontSize = 13.sp,
                    color = TextGray,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Кнопка регистрации ────────────────────────
            Button(
                onClick = {
                    if (phone.isBlank()) {
                        phoneError = true
                        return@Button
                    }
                    onSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowPrimary,
                    contentColor = TextPrimary,
                    disabledContainerColor = YellowDisabled,
                    disabledContentColor = TextGray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    LiveBeerTheme { RegisterScreen() }
}