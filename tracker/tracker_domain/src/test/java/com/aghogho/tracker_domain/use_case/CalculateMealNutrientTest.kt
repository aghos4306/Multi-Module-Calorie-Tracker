package com.aghogho.tracker_domain.use_case

import com.aghogho.core.domain.model.ActivityLevel
import com.aghogho.core.domain.model.Gender
import com.aghogho.core.domain.model.GoalType
import com.aghogho.core.domain.model.UserInfo
import com.aghogho.core.domain.preferences.Preferences
import com.aghogho.tracker_domain.model.MealType
import com.aghogho.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientTest {

    private lateinit var calculateMealNutrient: CalculateMealNutrient

    @Before
    fun setup() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 23,
            weight = 68f,
            height = 168,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f,
        )
        calculateMealNutrient = CalculateMealNutrient(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "Pizza",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                amount = 100,
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()   //add trackedFoods to random meal just to make sure it works for every scenerio
                ),
                //mealType = MealType.Breakfast,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrient(trackedFoods)

        val breakfastCalories = result.mealNutrients.values     //this is the sum of breakfastCalories we have in result
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        val expectedValues = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedValues)
    }

    @Test
    fun `Calories for Lunch properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "Egg Fries",
                carbs = Random.nextInt(150),
                protein = Random.nextInt(150),
                fat = Random.nextInt(150),
                amount = 200,
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                calories = Random.nextInt(3000),
                date = LocalDate.now()
            )
        }

        val result = calculateMealNutrient(trackedFoods)

        val lunchCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.calories }

        val expectedValues = trackedFoods
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.calories }

        assertThat(lunchCalories).isEqualTo(expectedValues)
    }

    @Test
    fun `Calories for Dinner properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "Pounded yam",
                carbs = Random.nextInt(150),
                protein = Random.nextInt(150),
                fat = Random.nextInt(150),
                amount = 200,
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                calories = Random.nextInt(3000),
                date = LocalDate.now()
            )
        }

        val result = calculateMealNutrient(trackedFoods)

        val lunchCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.calories }

        val expectedValues = trackedFoods
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.calories }

        assertThat(lunchCalories).isEqualTo(expectedValues)
    }

    @Test
    fun `Protein for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "Toast",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                amount = 100,
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()   //add trackedFoods to random meal just to make sure it works for every scenerio
                ),
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrient(trackedFoods)

        val breakfastProtein = result.mealNutrients.values     //this is the sum of breakfastCalories we have in result
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.protein }

        val expectedValues = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.protein }

        assertThat(breakfastProtein).isEqualTo(expectedValues)
    }

}
