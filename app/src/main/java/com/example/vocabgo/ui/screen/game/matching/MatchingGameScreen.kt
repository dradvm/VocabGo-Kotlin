package com.example.vocabgo.ui.screen.game.matching

import Nunito
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.SecondaryButton
import com.example.vocabgo.ui.screen.game.GameButton
import com.example.vocabgo.ui.viewmodel.game.matching.MatchingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
fun MatchingGameScreen(viewModel: MatchingViewModel = viewModel()) {
    Column (
        Modifier.fillMaxSize()
    ) {
        Text(
            "Nhấn vào các cặp tương ứng",
            style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
        )
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                val items = viewModel.items
                val matchedItems = viewModel.matchedItems
                val meaningItems = viewModel.meaningItems
                val wordItems = viewModel.wordItems
                val selectedMeaning by viewModel.selectedMeaning
                val selectedWord by viewModel.selectedWord
                val wrongSelectedMeaning by viewModel.wrongSelectedMeaning
                val wrongSelectedWord by viewModel.wrongSelectedWord
                val isWrong by viewModel.isWrong
                Column (
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    meaningItems.forEach { item ->
                        GameButton(
                            item.meaning,
                            selectedMeaning == item,
                            matchedItems.contains(item),
                            wrongSelectedMeaning == item,
                            matchedItems.contains(item) ||  isWrong,
                            { viewModel.selectMeaning(
                            item
                        ) })
                    }
                }
                Column (
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    wordItems.forEach { item ->
                        GameButton(
                            item.word,
                            selectedWord == item,
                            matchedItems.contains(item),
                            wrongSelectedWord == item,
                            matchedItems.contains(item) || isWrong,
                            { viewModel.selectWord(
                            item
                        ) })
                    }
                }
            }
        }
    }
//    val sheetState = rememberModalBottomSheetState()
//    var isSheetOpen by remember { mutableStateOf(true) }
//    if (isSheetOpen) {
//        ModalBottomSheet(
//            onDismissRequest = { isSheetOpen = false },
//            sheetState = sheetState,
//            shape = RectangleShape,
//            dragHandle = null,
//            scrimColor = Color.Transparent
//        ) {
//            Column(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text("Đây là BottomSheet")
//                Spacer(Modifier.height(8.dp))
//                Button(onClick = { isSheetOpen = false }) {
//                    Text("Đóng")
//                }
//            }
//        }
//    }
}
