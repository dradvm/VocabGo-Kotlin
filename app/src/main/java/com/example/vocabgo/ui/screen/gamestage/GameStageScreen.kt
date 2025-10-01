package com.example.vocabgo.ui.screen.gamestage

import Nunito
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyButton
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ClipboardList
import compose.icons.fontawesomeicons.solid.List
import compose.icons.fontawesomeicons.solid.ListUl

@Composable
fun GameStageScreen(navController: NavController) {
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp, 0.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            GameStatusBar()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                LazyColumn (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    var isLeft = false
                    val variance = 40
                    item {
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                    items(20) { index ->
                        var offsetX = 0

                        if (index % 4 == 0) {
                            isLeft = !isLeft
                        }
                        if (index % 4 == 2) {
                            offsetX = index % 4 * variance * if (isLeft) -1 else 1
                        }
                        else if (index % 2 == 1) {
                            offsetX = index % 2 * variance * if (isLeft) -1 else 1
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .offset(x = offsetX.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            GameNode()
                        }
                    }
                }
                LevelStageButton(navController)
            }
        }
    }
}
