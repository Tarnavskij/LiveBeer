package com.example.livebeer.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livebeer.R
import com.example.livebeer.core.ui.theme.LiveBeerTheme

private val Yellow = Color(0xFFFFE000)
private val Dark = Color(0xFF07080D)
private val NavBg = Color(0xFF1A1A1A)

data class NavItem(val label: String, val icon: ImageVector)

private val navItems = listOf(
    NavItem("Главная", Icons.Outlined.Home),
    NavItem("Информация", Icons.Outlined.Info),
    NavItem("Магазины", Icons.Outlined.ShoppingCart),
    NavItem("Профиль", Icons.Outlined.Person)
)

@Composable
fun MainScreenUnauthorized() {
    Box(modifier = Modifier.fillMaxSize().background(NavBg)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hops_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.35f,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.beer_illustration),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .align(Alignment.BottomCenter)
            )
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
                    color = Dark,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Чтобы копить баллы и литры, вам надо авторизоваться в приложении",
                    fontSize = 14.sp,
                    color = Dark.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Yellow,
                        contentColor = Dark
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Войти", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .background(NavBg)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { i, item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {}
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (i == 0) Yellow else Color(0xFF9E9E9E),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            color = if (i == 0) Yellow else Color(0xFF9E9E9E)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {}
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth().height(64.dp)
    ) {
        navItems.forEachIndexed { i, item ->
            NavigationBarItem(
                selected = selectedIndex == i,
                onClick = { onItemSelected(i) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = { Text(item.label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Yellow,
                    selectedTextColor = Yellow,
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenUnauthorizedPreview() {
    LiveBeerTheme { MainScreenUnauthorized() }
}