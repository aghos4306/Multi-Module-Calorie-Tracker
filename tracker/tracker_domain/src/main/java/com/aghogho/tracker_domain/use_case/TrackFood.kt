package com.aghogho.tracker_domain.use_case

import com.aghogho.tracker_domain.model.MealType
import com.aghogho.tracker_domain.model.TrackableFood
import com.aghogho.tracker_domain.model.TrackedFood
import com.aghogho.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(private val repository: TrackerRepository) {
    suspend operator fun invoke(
        food: TrackableFood,
        mealType: MealType,
        amount: Int,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPer100g / 100f) * amount).roundToInt(), //total carbs tracked for this food. Get the amount of carb per gram and multiple by the total amount of gram of carb in the meal
                amount = amount,
                protein = ((food.proteinPer100g / 100f) * amount).roundToInt(),
                fat = ((food.fatPer100g / 100f) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                calories = ((food.caloriesPer100g / 100f) * amount).roundToInt(),
                date = date
            )
        )
    }
}
