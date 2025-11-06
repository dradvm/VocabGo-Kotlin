package com.example.vocabgo.ui.screen.streak

import Nunito
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.composeunstyled.Icon
import com.composeunstyled.Text
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.viewmodel.streak.StreakViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StreakScreen(
    navController: NavController,
    streakViewModel: StreakViewModel
) {


    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = MyColors.Canary
    ) {
        Column (
            modifier = Modifier.systemBarsPadding()
        ) {
            val tabs = listOf("CÁ NHÂN", "BẠN BÈ")
            val pagerState = rememberPagerState(pageCount = { tabs.size })
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart).offset(x = 6.dp),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = MyColors.Eel,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    "Streak",
                    modifier = Modifier.align(Alignment.Center),
                    color = MyColors.Eel,
                    style = TextStyle(
                        fontFamily = Nunito,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Column {
                // Thanh Tab
                TabRow (
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier.background(MyColors.Canary),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                                .height(3.dp), // độ dày thanh gạch
                            color = MaterialTheme.colorScheme.primary // 🎨 MÀU THANH GẠCH DƯỚI
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = pagerState.currentPage == index
                        Tab(
                            modifier = Modifier.background(MyColors.Canary),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                // Khi nhấn tab thì cuộn đến trang tương ứng
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    title,
                                    style = TextStyle(
                                        fontFamily = Nunito,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MyColors.Eel
                                    )
                                ) }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> PersonalStreakScreen(streakViewModel)
                        1 -> Text("Nội dung Từ vựng", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PersonalStreakScreen(
    streakViewModel: StreakViewModel
) {


    LaunchedEffect(Unit) {
        streakViewModel.fetchStreakCount()
    }
    val streakCount = streakViewModel.streakCount
    val today = LocalDate.now()

    // State để quản lý tháng/năm hiển thị
    var displayedMonth by remember { mutableStateOf(today.monthValue) }
    var displayedYear by remember { mutableStateOf(today.year) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Box() {
                        Text(
                            text = streakCount.toString(),
                            style = TextStyle(
                                fontFamily = Nunito,
                                fontSize = 64.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                            ),
                        )
                        Text(
                            text = streakCount.toString(),
                            style = TextStyle(
                                fontFamily = Nunito,
                                fontSize = 64.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MyColors.Grizzly,
                                drawStyle = Stroke(width = 10f),
                            ),
                        )
                    }
                    Text(
                        text = "ngày streak!",
                        style = TextStyle(
                            fontFamily = Nunito,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MyColors.Grizzly,
                        ),
                    )
                }
                MyLottie(
                    lottie = "streak_fire.lottie",
                    size = 150.dp
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(16.dp, 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Lịch Streak",
                style = TextStyle(
                    fontFamily = Nunito,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MyColors.Eel
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MyColors.Swan, RoundedCornerShape(12.dp))
                    .padding(20.dp, 12.dp)
            ) {

                // Render calendar với month/year hiện tại
                StreakCalendar(
                    month = displayedMonth,
                    year = displayedYear
                )

                // Nút điều hướng tháng
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {
                            // Giảm 1 tháng
                            if (displayedMonth == 1) {
                                displayedMonth = 12
                                displayedYear -= 1
                            } else {
                                displayedMonth -= 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous")
                    }
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {
                            // Tăng 1 tháng
                            if (displayedMonth == 12) {
                                displayedMonth = 1
                                displayedYear += 1
                            } else {
                                displayedMonth += 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next")
                    }
                }
            }
        }
    }
}


@Composable
fun StreakCalendar(
    month: Int,
    year: Int,
    streakViewModel: StreakViewModel = hiltViewModel()
) {
    LaunchedEffect(month, year) {
        streakViewModel.fetchStreakMonth(month, year)
    }

    val monthStreak = streakViewModel.monthStreak

    // Tạo activeDays và freezeDays trực tiếp từ monthStreak
    val activeDays = monthStreak.filter { !it.isFrozen }
        .map { LocalDate.parse(it.date.substring(0, 10)).dayOfMonth }

    val freezeDays = monthStreak.filter { it.isFrozen }
        .map { LocalDate.parse(it.date.substring(0, 10)).dayOfMonth }

    val yearMonth = YearMonth.of(year, month)
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value

    val dayLabels = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "tháng $month năm $year",
            style = TextStyle(
                fontFamily = Nunito,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MyColors.Eel
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            // Hàng tiêu đề thứ
            items(dayLabels) { label ->
                Text(
                    text = label,
                    modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MyColors.Eel,
                    textAlign = TextAlign.Center
                )
            }

            // Thêm các ô trống trước ngày 1
            items(firstDayOfWeek - 1) {
                Box(modifier = Modifier.size(36.dp))
            }

            // Các ngày trong tháng
            items((1..daysInMonth).toList()) { day ->
                val color = when {
                    activeDays.contains(day) -> MyColors.Fox
                    freezeDays.contains(day) -> MyColors.Macaw
                    else -> Color.Transparent
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(36.dp)
                        .background(
                            color = color,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.toString(),
                        color = if (color == Color.Transparent) MyColors.Hare else Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
