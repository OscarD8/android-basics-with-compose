package com.example.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonadeapp.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    val lemonadeSteps = listOf(
        LemonadeStep(R.drawable.lemon_tree, R.string.lemon_tree_img_desc, R.string.lemon_tree),
        LemonadeStep(R.drawable.lemon_squeeze, R.string.lemon_img_desc, R.string.lemon),
        LemonadeStep(R.drawable.lemon_drink, R.string.lemonade_img_desc, R.string.lemonade),
        LemonadeStep(R.drawable.lemon_restart, R.string.empty_glass_img_desc, R.string.empty_lemonade)
    )

    var currentStepIndex by remember { mutableIntStateOf(0) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.lemonade_header) ,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { innerPadding ->
        Surface (
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentStepIndex) {
                0 -> LemonadeImageWithText(
                    lemonadeStep = lemonadeSteps[currentStepIndex]
                ) {
                    squeezeCount = (2..4).random()
                    currentStepIndex += 1
                }

                1 -> LemonadeImageWithText(
                    lemonadeStep = lemonadeSteps[currentStepIndex]
                ) {
                    squeezeCount -= 1
                    if (squeezeCount <= 0) {
                        currentStepIndex += 1
                    }
                }

                2 -> LemonadeImageWithText(
                    lemonadeStep = lemonadeSteps[currentStepIndex]
                ) { currentStepIndex += 1 }

                3 -> LemonadeImageWithText(
                    lemonadeStep = lemonadeSteps[currentStepIndex]
                ) { currentStepIndex = 0 }
            }
        }
    }
}

@Composable
fun LemonadeImageWithText(
    lemonadeStep: LemonadeStep,
    modifier: Modifier = Modifier,
    onImageClick: () -> Unit
) {
    Box (
        modifier = modifier
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                border = BorderStroke(
                    width = (dimensionResource(R.dimen.button_border_stroke_width)),
                    color = MaterialTheme.colorScheme.outline)
            ) {
                Image(
                    painter = painterResource(lemonadeStep.imgRefId),
                    contentDescription = stringResource(lemonadeStep.imgDescId),
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.image_width))
                        .height(dimensionResource(R.dimen.image_height))
                        .padding(dimensionResource(R.dimen.button_interior_padding))
                )
            }
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.vertical_padding)))
            Text(
                text = stringResource(lemonadeStep.textPromptId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}