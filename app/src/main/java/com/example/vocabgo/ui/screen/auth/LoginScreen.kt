package com.example.vocabgo.ui.screen.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.data.datastore.DataStoreManager
import com.example.vocabgo.repository.AuthRepository
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.components.SecondaryButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    suspend fun test(): String {
        return authRepository.test()
    }
    suspend fun test2(context: Context): Exception? {
        return authRepository.googleSignIn(context)
    }
}
@Composable
fun LoginScreen(navController: NavController, viewModel: MyViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStoreManager = DataStoreManager(context)

    val accessTokenFlow : Flow<String> = dataStoreManager.getAcessToken()
    val refreshTokenFlow : Flow<String> = dataStoreManager.getRefreshToken()

    val accessToken by accessTokenFlow.collectAsState(initial = "")
    val refreshToken by refreshTokenFlow.collectAsState(initial = "")

    var result by remember { mutableStateOf("") }

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (
            modifier = Modifier.fillMaxSize().systemBarsPadding().padding(16.dp, 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
            ) {
                Column {
                    Text(text = "Access Token: $accessToken")
                    Text(text = "Refresh Token: $refreshToken")
                    Text(text = "Result: $result")
                }
            }
            Column () {
                PrimaryButton (content = "Test", onClick = {
                    result = "Loading..."
                    scope.launch {
                        val response = viewModel.test()
                        result = response
                    }
                }) {  }
                SecondaryButton (
                    content = "GOOGLE",
                    onClick = {
                        scope.launch {
//                            AuthModule.googleSignIn(context)
                            viewModel.test2(context)
                        }

                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}