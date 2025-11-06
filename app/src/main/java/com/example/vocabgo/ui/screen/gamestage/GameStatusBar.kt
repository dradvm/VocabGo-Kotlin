package com.example.vocabgo.ui.screen.gamestage

import Nunito
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.data.dto.UserWallet
import com.example.vocabgo.data.dto.UserWalletState


@Composable
fun GameStatusBar (
    navController: NavController,
    handleClickEnergy: (() -> Unit)? = { },
    userWallet: UserWalletState?,
    userStreak: Int
) {
    val interactionSource = remember { MutableInteractionSource() }
    val buttonSize = 26.dp
    val spacing = 6.dp
    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        itemVerticalAlignment = Alignment.CenterVertically,

        ) {

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            itemVerticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    navController.navigate("streak") {
                        launchSingleTop= true
                    }
                }
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.fire_solid_full),
                contentDescription = "streak",
                tint = if (userStreak > 0) MyColors.Fox else MyColors.Swan,
                modifier = Modifier.size(buttonSize)
            )
            Text(
                userStreak.toString(),
                color = if (userStreak > 0) MyColors.Fox else MyColors.Swan,
                style = TextStyle(fontFamily = Nunito, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold )
            )
        }
        FlowRow(

            horizontalArrangement = Arrangement.spacedBy(spacing),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.cubes_solid_full),
                contentDescription = "ruby",
                tint = MyColors.Macaw,
                modifier = Modifier.size(buttonSize)
            )
            Text(
                userWallet?.ruby.toString(),
                color = MyColors.Macaw,
                style = TextStyle(fontFamily = Nunito, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            itemVerticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { handleClickEnergy?.invoke() }
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.bolt_solid_full),
                contentDescription = "energy",
                tint = MyColors.Bee,
                modifier = Modifier.size(buttonSize)
            )
            Text(
                userWallet?.energy?.current.toString(),
                color = MyColors.Bee,
                style = TextStyle(fontFamily = Nunito, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}
