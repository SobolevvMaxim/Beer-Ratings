package com.sobolev.beerratings.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sobolev.beerratings.R
import com.sobolev.beerratings.ui.theme.LightGray
import com.sobolev.beerratings.ui.theme.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(onLogin: (username: String) -> Unit, onSignUp: () -> Unit, onSkipped: () -> Unit) {
    MainTheme {
        Scaffold {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f)
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    style = MaterialTheme.typography.headlineMedium
                )

                Card(
                    Modifier
                        .background(color = Color.Transparent)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(
                            top = 53.dp,
                            bottom = 40.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        val username = remember { mutableStateOf("") }

                        Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                            TopBodyTitles(bigTitle = "Welcome back!", hint = "Log in")
                            InputFields(label = "Username", username)
                        }

                        ContinueButtons(onLogin, onSignUp, onSkipped, username)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBodyTitles(bigTitle: String, hint: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Text(text = bigTitle, style = MaterialTheme.typography.titleLarge)
        Text(text = hint, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun InputFields(label: String, inputState: MutableState<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        TextField(
            value = inputState.value,
            onValueChange = { inputState.value = it },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                backgroundColor = LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ContinueButtons(
    onLogin: (username: String) -> Unit,
    onSignUp: () -> Unit,
    onSkipped: () -> Unit,
    username: MutableState<String>
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = { onLogin(username.value) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
        Button(
            onClick = { onSignUp() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Enter without logging in",
            style = MaterialTheme.typography.labelMedium.copy(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .clickable {
                    onSkipped()
                }
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    showSystemUi = true,
    apiLevel = Build.VERSION_CODES.S,
//    device = Devices.PIXEL_XL
)
@Composable
fun PreviewStartScreen() {
    MainTheme {
        StartScreen(onLogin = {}, onSignUp = { /*TODO*/ }) {

        }
    }
}