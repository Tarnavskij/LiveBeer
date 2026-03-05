package com.example.livebeer.feature.main

import androidx.compose.foundation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livebeer.R
import com.example.livebeer.core.ui.theme.LiveBeerTheme

// ─── Цвета из макета ───────────────────────────────────────
private val YellowPrimary  = Color(0xFFFFE000)
private val DarkCard       = Color(0xFF1C1C1E)
private val TextPrimary    = Color(0xFF07080D)
private val NavBackground  = Color(0xFFFFFFFF)

// ─── UNAUTHORIZED MAIN SCREEN ──────────────────────────────
@Composable
fun MainScreenUnauthorized() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))  // весь экран тёмный
    ) {
        // Белый контент сверху (всё кроме навбара)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {
            // Хмель на весь белый блок
            Image(
                painter = painterResource(id = R.drawable.hops_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.35f,
                modifier = Modifier.fillMaxSize()
            )

            // Иллюстрация снизу белого блока
            Image(
                painter = painterResource(id = R.drawable.beer_illustration),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .align(Alignment.BottomCenter)
            )

            // Текст + кнопка
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 160.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Войдите в\nприложение",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Чтобы копить баллы и литры, вам надо авторизоваться в приложении",
                    fontSize = 14.sp,
                    color = TextPrimary.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowPrimary,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Войти", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Наш тёмный навбар — прибит к низу, поверх всего
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .background(Color(0xFF1A1A1A))
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val items = listOf(
                    NavItem("Главная",    Icons.Outlined.Home),
                    NavItem("Информация", Icons.Outlined.Info),
                    NavItem("Магазины",   Icons.Outlined.ShoppingCart),
                    NavItem("Профиль",    Icons.Outlined.Person),
                )
                items.forEachIndexed { index, item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { }
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (index == 0) YellowPrimary else Color(0xFF9E9E9E),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            color = if (index == 0) YellowPrimary else Color(0xFF9E9E9E)
                        )
                    }
                }
            }
        }
    }
}

// ─── BOTTOM NAVIGATION BAR ─────────────────────────────────
data class NavItem(val label: String, val icon: ImageVector)

@Composable
fun BottomNavigationBar(
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {}
) {
    val items = listOf(
        NavItem("Главная",     Icons.Outlined.Home),
        NavItem("Информация",  Icons.Outlined.Info),
        NavItem("Магазины",    Icons.Outlined.ShoppingCart),
        NavItem("Профиль",     Icons.Outlined.Person),
    )

    NavigationBar(
        containerColor = NavBackground,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor    = YellowPrimary,
                    selectedTextColor    = YellowPrimary,
                    unselectedIconColor  = Color(0xFF9E9E9E),
                    unselectedTextColor  = Color(0xFF9E9E9E),
                    indicatorColor       = Color.Transparent
                )
            )
        }
    }
}

// ─── PREVIEWS ──────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenUnauthorizedPreview() {
    LiveBeerTheme {
        MainScreenUnauthorized()
    }
}