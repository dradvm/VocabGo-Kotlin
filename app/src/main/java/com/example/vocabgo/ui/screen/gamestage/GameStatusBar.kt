package com.example.vocabgo.ui.screen.gamestage

import Nunito
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabgo.R


@Composable
fun GameStatusBar () {

    val buttonSize = 26.dp
    val spacing = 6.dp
    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(0.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        itemVerticalAlignment = Alignment.CenterVertically,

        ) {

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.fire_solid_full),
                contentDescription = "heart",
                tint = MyColors.Fox,
                modifier = Modifier.size(buttonSize)
            )
            Text(
                "0",
                color = MyColors.Fox,
                style = TextStyle(fontFamily = Nunito, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold )
            )
        }
        FlowRow(

            horizontalArrangement = Arrangement.spacedBy(spacing),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.cubes_solid_full),
                contentDescription = "heart",
                tint = MyColors.Macaw,
                modifier = Modifier.size(buttonSize)
            )
            Text(
                "0",
                color = MyColors.Macaw,
                style = TextStyle(fontFamily = Nunito, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.heart_solid_full),
                contentDescription = "heart",
                tint = MyColors.Cardinal,
                modifier = Modifier.size(buttonSize)
            )
            Text(
                "0",
                color = MyColors.Cardinal,
                style = TextStyle(fontFamily = Nunito, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}
