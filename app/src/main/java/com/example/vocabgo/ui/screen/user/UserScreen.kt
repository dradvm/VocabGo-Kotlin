package com.example.vocabgo.ui.screen.user

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.common.helpers.DateHelper
import com.example.vocabgo.ui.components.MyProgress
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.components.SecondaryButton
import com.example.vocabgo.ui.screen.quest.QuestDaily
import com.example.vocabgo.ui.screen.quest.QuestFriend
import com.example.vocabgo.ui.viewmodel.user.UserViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular

//@Preview(showBackground = true)
@Composable
fun UserScreen(navController: NavController, userViewModel: UserViewModel) {

    val userProfile by userViewModel.userProfile.collectAsState()
    val userWallet by userViewModel.userWallet.collectAsState()
    LaunchedEffect(Unit) {
        userViewModel.fetchUserProfile()
    }

    Surface (
        Modifier.fillMaxSize()
    ) {
        Column (
            Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(MyColors.Iguana)
                    .statusBarsPadding(),
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        navController.navigate("settings") {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings",
                        tint = MyColors.Eel,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Surface(
                Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier.padding(12.dp, 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column() {
                        userProfile?.givenName?.let {
                            Text(
                                it,
                                style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
                            )
                        }
                        FlowRow (
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            itemVerticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = buildString {
                                    append("@${userProfile?.publicId ?: ""}")
                                    if (!userProfile?.createdAt.isNullOrEmpty()) {
                                        append(" • ") // Dấu chấm giữa, dùng ký tự giữa dòng đẹp hơn "."
                                        append(DateHelper.formatJoinDate(userProfile?.createdAt!!))
                                    }
                                },
                                style = TextStyle(
                                    fontFamily = Nunito,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MyColors.Wolf
                                ),
                                modifier = Modifier.wrapContentWidth(),
                                softWrap = true // Cho phép tự xuống dòng
                            )

                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Column (
                        ) {
                            Text(
                                "1",
                                style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
                            )
                            Text(
                                "Đang theo dõi",
                                style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MyColors.Wolf)
                            )
                        }
                        Box(Modifier.width(2.dp).fillMaxHeight().background(MyColors.Swan)) {}
                        Column (
                        ) {
                            Text(
                                "3",
                                style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
                            )
                            Text(
                                "Người theo dõi",
                                style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MyColors.Wolf)
                            )
                        }
                    }
                    Row() {
                        SecondaryButton(content = "THÊM BẠN BÈ") {
                            Icon(
                                painter = painterResource(R.drawable.user_plus_solid_full),
                                contentDescription = "user add",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(26.dp).padding(2.dp)
                            )
                        }
                    }

//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(10.dp)
//                    ) {
//                        val scrollState = rememberScrollState()
//                        Text(
//                            "Gợi ý kết bạn",
//                            style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
//                        )
//                        Row(
//                            modifier = Modifier.horizontalScroll(scrollState),
//                            horizontalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            for (i in 1..10) {
//                                Box(
//                                    modifier = Modifier
//                                        .width(140.dp)
//                                        .height(160.dp)
//                                        .border(2.dp, MyColors.Swan,  RoundedCornerShape(16.dp))
//                                        .padding(8.dp)
//                                ) {
//
//                                    IconButton(
//                                        modifier = Modifier.size(26.dp).align(Alignment.TopEnd),
//                                        onClick = {
//
//                                        }
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Default.Close,
//                                            contentDescription = "close",
//                                            tint = Color.LightGray,
//                                            modifier = Modifier.size(26.dp)
//                                        )
//                                    }
//                                    Column(
//                                        modifier = Modifier
//                                    ) {
//
//                                    }
//                                }
//                            }
//                        }
//
//                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "Tổng quan",
                            style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
                        )
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(.5f)
                                    .border(2.dp, MyColors.Swan , RoundedCornerShape(16.dp))
                                    .padding(16.dp, 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_home),
                                    contentDescription = "streak",
                                    modifier = Modifier.size(26.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxHeight().weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "1",
                                        style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
                                    )
                                    Text(
                                        "Ngày streak",
                                        style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MyColors.Wolf)
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .weight(.5f)
                                    .border(2.dp, MyColors.Swan , RoundedCornerShape(16.dp))
                                    .padding(16.dp, 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.brain_solid_full),
                                    contentDescription = "streak",
                                    modifier = Modifier.size(26.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Column(
                                    modifier = Modifier.fillMaxHeight().weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "${userWallet?.kpPoints} KP",
                                        style = TextStyle(fontFamily = Nunito, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
                                    )
                                    Text(
                                        "Tổng KP",
                                        style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MyColors.Wolf)
                                    )
                                }
                            }
                        }

                    }

                }

            }

        }
    }
}

