package com.example.vocabgo.ui.screen.game.FillInBlankScreen

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.screen.game.GameButton
import com.example.vocabgo.ui.screen.game.rememberKeyboardOpenState
import com.example.vocabgo.ui.viewmodel.game.fill_in_blank.FillInBlankViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FillInBlankScreen(viewModel : FillInBlankViewModel = viewModel()) {
    val isFocusTextField by rememberKeyboardOpenState()

    val sentenceWords = viewModel.sentenceWord
    val sentenceViWords = viewModel.sentenceViWord
    val word = viewModel.word

    var text by remember { mutableStateOf("") }
    val textMeasurer = rememberTextMeasurer()
    val style = TextStyle(fontFamily = Nunito, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = MyColors.Eel)

    val result = textMeasurer.measure(
        text = AnnotatedString(word.word),
        style = style
    )
    val space = textMeasurer.measure(
        text = AnnotatedString(" "),
        style = style
    )
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Text(
            "Hoàn tất bản dịch",
            style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
        )
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Row (
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyLottie("happy_dog.lottie", 150.dp)
                FlowRow(
                    modifier = Modifier.offset(y = -20.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(BorderStroke(2.dp, MyColors.Swan), RoundedCornerShape(16.dp))
                        .padding(20.dp, 12.dp),
                    horizontalArrangement = Arrangement.spacedBy((space.size.width/2).dp)
                ) {
                    sentenceViWords.forEach { item ->
                        Text(
                            item,
                            style = style
                        )
                    }

                }

            }
        }

        Box(
            modifier = Modifier.weight(1f).fillMaxWidth()
                .background(MyColors.Polar, RoundedCornerShape(12.dp))
                .border(2.dp,  MyColors.Swan, RoundedCornerShape(12.dp), )
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {


            FlowRow (
                horizontalArrangement = Arrangement.spacedBy((space.size.width/2).dp)
            ) {

                sentenceWords.forEach { item ->
                    if (item == word.word) {
                        Column(
                            modifier = Modifier
                                .width((result.size.width/2).dp)
                        ) {
                            BasicTextField(
                                value = text,
                                onValueChange = { text = it },
                                singleLine = true,
                                textStyle = style.copy(color = if (isFocusTextField) {
                                    MaterialTheme.colorScheme.primary
                                } else {MyColors.Eel}),
                                cursorBrush = SolidColor( MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 2.dp),
                            )

                            HorizontalDivider(
                                thickness = 2.dp,
                                color = if (isFocusTextField) {
                                    MaterialTheme.colorScheme.primary
                                } else {MyColors.Eel}
                            )
                        }
                    }
                    else {
                        Text(
                            item,
                            style = style
                        )
                    }
                }
            }

        }
    }
}