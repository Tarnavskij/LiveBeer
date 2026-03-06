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

private val Yellow = Color(0xFFFFE000)
private val YellowOff = Color(0xFFFFF59D)
private val Dark = Color(0xFF07080D)
private val Gray = Color(0xFF8E8E93)
private val Blue = Color(0xFF007AFF)
private val Red = Color(0xFFFF3B30)
private val BorderDefault = Color(0xFFE0E0E0)

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

    val formValid = phone.isNotBlank() && name.isNotBlank() && birthDate.isNotBlank() && agreed

    if (showDatePicker) {
        val pickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { ms ->
                        val cal = java.util.Calendar.getInstance().apply { timeInMillis = ms }
                        val d = cal.get(java.util.Calendar.DAY_OF_MONTH)
                        val m = cal.get(java.util.Calendar.MONTH) + 1
                        val y = cal.get(java.util.Calendar.YEAR)
                        birthDate = "%02d.%02d.%04d".format(d, m, y)
                    }
                    showDatePicker = false
                }) { Text("Готово", color = Blue) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Отмена", color = Blue) }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
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

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Регистрация\nаккаунта",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Dark,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Заполните поля данных ниже", fontSize = 14.sp, color = Gray)

            Spacer(modifier = Modifier.height(28.dp))

            Text("Номер телефона", fontSize = 13.sp, color = Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it; phoneError = false },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("+7", color = Gray) },
                isError = phoneError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Dark,
                    unfocusedBorderColor = BorderDefault,
                    errorBorderColor = Red
                )
            )
            if (phoneError) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Ошибка номера", color = Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Ваше имя", fontSize = 13.sp, color = Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Введите имя", color = Gray) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Dark,
                    unfocusedBorderColor = BorderDefault
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Дата рождения", fontSize = 13.sp, color = Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = birthDate,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                placeholder = { Text("ДД.ММ.ГГГГ", color = Gray) },
                readOnly = true,
                enabled = false,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = BorderDefault,
                    disabledTextColor = Dark,
                    disabledPlaceholderColor = Gray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = agreed,
                    onCheckedChange = { agreed = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Yellow,
                        checkmarkColor = Dark,
                        uncheckedColor = Gray
                    ),
                    modifier = Modifier.size(20.dp).padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Я согласен с ")
                        withStyle(SpanStyle(color = Blue)) {
                            append("условиями обработки персональных данных")
                        }
                    },
                    fontSize = 13.sp,
                    color = Gray,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (phone.isBlank()) { phoneError = true; return@Button }
                    onSuccess()
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                enabled = formValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Dark,
                    disabledContainerColor = YellowOff,
                    disabledContentColor = Gray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Зарегистрироваться", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
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