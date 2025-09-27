package com.example.thirtydaysrecipes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.thirtydaysrecipes.R

data class Recipe (
    @StringRes val recipeName : Int,
    @StringRes val recipeDesc : Int,
    val recipeDay : Int,
    @DrawableRes val recipeImage : Int,
    @StringRes val imageCredit : Int
)

object RecipeRepository {
    val recipesList = listOf(
        Recipe(R.string.recipe_name_1, R.string.recipe_desc_1, 1, R.drawable.recipe_image_1, R.string.image_credit_1) ,
        Recipe(R.string.recipe_name_2, R.string.recipe_desc_2, 2, R.drawable.recipe_image_2, R.string.image_credit_2),
        Recipe(R.string.recipe_name_31, R.string.recipe_desc_31, 3, R.drawable.recipe_image_31, R.string.image_credit_31)
    )
}