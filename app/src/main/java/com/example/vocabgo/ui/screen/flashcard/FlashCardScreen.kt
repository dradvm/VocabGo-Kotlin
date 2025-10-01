package com.example.vocabgo.ui.screen.flashcard

import Nunito
import android.util.Log
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.*
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyProgress
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.components.SecondaryButton
import com.example.vocabgo.ui.screen.quest.QuestFriend
import com.example.vocabgo.ui.viewmodel.flashcard.FlashCard
import com.example.vocabgo.ui.viewmodel.flashcard.FlashCardViewModel
import kotlinx.coroutines.delay


@Preview()
@Composable
fun FlashCardScreen (viewModel: FlashCardViewModel = viewModel())   {
    Surface (
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .systemBarsPadding(),
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Column (
            modifier = Modifier.fillMaxSize().padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier.offset(x = 6.dp),
                        onClick = {
//                        navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close",
                            tint = Color.LightGray,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Row() {
                    MyProgress(
                        isBright = false,
                        height = 6.dp,
                        progressColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column (
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    val cards = viewModel.cards
                    Box(
                        modifier = Modifier.weight(1f)
                            .fillMaxWidth()
                            .padding(16.dp, 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        cards.forEachIndexed{index, card ->
                            key(card) {
                                androidx.compose.animation.AnimatedVisibility(
                                    visible = card.visible.value,
                                    exit = if (card.slideToLeft.value) {
                                        slideOutHorizontally { fullWidth -> fullWidth } + fadeOut()
                                    } else {
                                        slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
                                    },
                                    label = "",
                                ) {
                                    FlashCardItem(card )
                                }
                            }
                        }

                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Box(
                            Modifier.weight(1f)
                        ) {
                            PrimaryButton ("Tôi không biết", onClick = { viewModel.onDontKnowClick() }) {

                            }
                        }
                        Box(
                            Modifier.weight(1f)
                        ) {
                            SecondaryButton ("Tôi đã biết", onClick = { viewModel.onKnowClick()} ) {

                            }
                        }

                    }

                }
            }
        }
    }

}
