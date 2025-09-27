package com.example.thirtydaysrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.thirtydaysrecipes.model.Recipe
import com.example.thirtydaysrecipes.model.RecipeRepository
import com.example.thirtydaysrecipes.ui.theme.AppTheme
import com.example.thirtydaysrecipes.ui.theme.shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme () {
                RecipeApp()
            }
        }
    }
}

@Composable
fun RecipeApp() {
    Scaffold(
        topBar = { RecipeTopBar(
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.small_padding),
                bottom =  dimensionResource(R.dimen.small_padding)))
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen.horizontal_pad)),
        ) {
            items(RecipeRepository.recipesList) {
                RecipeItem(
                    it,
                    modifier = Modifier
                        .padding(bottom = dimensionResource(R.dimen.med_padding))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier
    )
}

@Composable
private fun RecipeItem(
    recipe : Recipe,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue =
            if (expanded) MaterialTheme.colorScheme.tertiaryContainer
            else MaterialTheme.colorScheme.surfaceContainerHighest
    )

    Card (modifier = modifier
        .shadow(dimensionResource(R.dimen.card_elevation), shape = shapes.medium)
        .background(color)
    ) {
        Column (
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.med_padding),
                    top = dimensionResource(R.dimen.med_padding),
                    end = dimensionResource(R.dimen.med_padding))
                .animateContentSize(
                    animationSpec = SpringSpec(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            RecipeHeader(
                recipe.recipeName,
                recipe.recipeDay,
                modifier = Modifier.fillMaxWidth()
            )

            Box (
                modifier = Modifier
                    .aspectRatio(16f / 9f),
                contentAlignment = Alignment.Center
            ) {
                RecipeImage(
                    recipe.recipeImage, recipe.recipeName,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = dimensionResource(R.dimen.med_padding),
                            start = dimensionResource(R.dimen.large_padding),
                            end = dimensionResource(R.dimen.large_padding)
                        )
                        .clip(shape = shapes.medium)
                )
            }

            RecipeClickableIcon(
                expanded = expanded,
                onClickAction = { expanded = !expanded }
            )

            if (expanded) {
                RecipeDesc(
                    recipe.recipeDesc,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.large_padding),
                        end = dimensionResource(R.dimen.med_padding))
                )
                ImageCredit(
                    recipe.imageCredit,
                    modifier = Modifier.padding(dimensionResource(R.dimen.med_padding))
                )
            }
        }
    }
}

@Composable
private fun RecipeHeader(
    recipeName : Int,
    recipeDate : Int,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(recipeName),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.day_prompt) + " " + recipeDate,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun RecipeImage(
    @DrawableRes imageRes : Int,
    @StringRes contentDesc : Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = stringResource(contentDesc),
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
private fun RecipeClickableIcon(
    expanded : Boolean,
    onClickAction : () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClickAction,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = stringResource(R.string.expandable_prompt)
        )
    }
}

@Composable
private fun RecipeDesc(
    @StringRes descId : Int,
    modifier : Modifier = Modifier
) {
    Text(
        text = stringResource(descId),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Justify,
        modifier = modifier
    )
}

@Composable
private fun ImageCredit (
    @StringRes creditId : Int,
    modifier : Modifier = Modifier
) {
    Text(
        text = stringResource(creditId),
        modifier = modifier,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodySmall
    )
}

@Preview(showBackground = false)
@Composable
fun LightPreview() {
    AppTheme {
        RecipeApp()
    }
}

@Preview(showBackground = false)
@Composable
fun DarkPreview() {
    AppTheme (darkTheme = true) {
        RecipeApp()
    }
}