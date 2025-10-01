package com.example.vocabgo.ui.screen.quest

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.ProgressIndicator
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyProgress

@Preview()
@Composable
fun QuestScreen() {
    Surface (
        Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .statusBarsPadding()
    ) {
        Column (
            Modifier.verticalScroll(rememberScrollState())
        ) {
            Surface(
                Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary
            ) {
                Column (
                    Modifier.fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row (
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Text(
                                "THÁNG 9",
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(12))
                                    .padding(6.dp, 2.dp),
                                style = TextStyle(fontFamily = Nunito, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onPrimary)
                            )

                            Text(
                                "Vocabcon 2025",
                                style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                            )
                            Row (
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Day",
                                    tint = MyColors.Hare
                                )
                                Text(
                                    "3 NGÀY",
                                    style = TextStyle(fontFamily = Nunito, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Hare)
                                )
                            }
                        }
                        Box (

                        ) {

                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(12.dp, 16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Kiếm 30 điểm nhiệm vụ",
                                    style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        "2",
                                        style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                                    )
                                    Text(
                                        "/ 30",
                                        style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Hare)
                                    )
                                }
                            }
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                MyProgress(
                                    progressShape = RoundedCornerShape(
                                        topStartPercent = 100,
                                        topEndPercent = 0,
                                        bottomStartPercent = 100,
                                        bottomEndPercent = 0
                                    ),
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.lock_solid_full),
                                        contentDescription = "lock",
                                        tint = MyColors.Eel,
                                        modifier = Modifier
                                            .background(MyColors.Swan, RoundedCornerShape(100))
                                            .border(BorderStroke(3.dp, Color.White), RoundedCornerShape(100))
                                            .padding(6.dp)
                                            .size(20.dp)
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.lock_solid_full),
                                        contentDescription = "lock",
                                        tint = MyColors.Eel,
                                        modifier = Modifier
                                            .background(MyColors.Swan, RoundedCornerShape(100))
                                            .border(BorderStroke(3.dp, Color.White), RoundedCornerShape(100))
                                            .padding(6.dp)
                                            .size(20.dp)
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.lock_solid_full),
                                        contentDescription = "lock",
                                        tint = MyColors.Eel,
                                        modifier = Modifier
                                            .background(MyColors.Swan, RoundedCornerShape(100))
                                            .border(BorderStroke(3.dp, Color.White), RoundedCornerShape(100))
                                            .padding(8.dp)
                                            .size(24.dp)
                                    )
                                }

                            }

                        }
                    }
                }
            }
            Surface(
                Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column (
                    Modifier.padding(16.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    QuestDaily()
                    QuestFriend()

                }
            }

        }
    }
}