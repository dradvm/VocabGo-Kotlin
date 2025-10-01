package com.example.vocabgo.ui.screen.gamestage

import Nunito
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.composeunstyled.ProgressIndicator
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.MyProgress
import com.example.vocabgo.ui.components.PrimaryButton


@Composable
fun LevelStageScreen (navController: NavController) {
    val items = listOf("A","B","C")
    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
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
                            tint = Color.LightGray,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        "Mức độ từ vựng",
                        modifier = Modifier.align(Alignment.Center),
                        color = MyColors.Hare,
                        style = TextStyle(fontFamily = Nunito, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.LightGray)
                )
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(4, key = {it}) { index ->
                        LevelItem(isDone = index != 3)

                    }

                }
            }
        }

    }
}
@Composable
fun LevelItem(isDone: Boolean = true) {

    MyButton(
        buttonColor = Color.White,
        shadowColor = MyColors.Swan,
        buttonHeight = if (isDone) 96f else 324f,
        shadowBottomOffset = 2f,
        border = BorderStroke(2.dp, MyColors.Swan),
        contentPadding = if (isDone) {
            ButtonDefaults.ContentPadding
        }
        else {
            PaddingValues(0.dp)
        },
        onClick = {
            print("Click")
        },
    ) {
        Column () {
            Surface (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MyColors.Iguana

            ) {

            }
            Column(

                modifier = Modifier
                    .height(100.dp)
                    .padding(if (isDone) {
                        PaddingValues(0.dp)
                    }
                    else {
                        ButtonDefaults.ContentPadding
                    }),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    "A1",
                    style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                )
                Box(
                    modifier = Modifier.width(400.dp).height(48.dp), // khung tổng
                    contentAlignment = Alignment.Center
                ) {
                    MyProgress()

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .align(Alignment.CenterEnd) // nằm cuối, giữa dọc
                            .background(Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.award_solid_full),
                            contentDescription = "award",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }


            }
        }



    }
}