package com.aghogho.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.aghogho.core.R
import com.aghogho.core.util.UiEvent
import com.aghogho.core_ui.LocalSpacing
import com.aghogho.tracker_presentation.components.AddButton
import com.aghogho.tracker_presentation.components.DaySelector
import com.aghogho.tracker_presentation.components.ExpandableMeal
import com.aghogho.tracker_presentation.components.NutrientsHeader

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item { 
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal))
                },
                content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = spacing.spaceSmall)
                        ) {
                            state.trackedFoods.forEach { food ->
                                TrackedFoodItem(
                                    trackedFood = food,
                                    onDeleteClick = {
                                        viewModel.onEvent(
                                            TrackerOverviewEvent.OnDeleteTrackedFoodClick(food)
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(spacing.spaceMedium))  //add after each recycled tracked food,
                            }
                            AddButton(
                                text = stringResource(
                                    id = R.string.add_meal,
                                    meal.name.asString(context)
                                ),
                                onClick = {
                                    viewModel.onEvent(
                                        TrackerOverviewEvent.OnAddFoodClick(meal)
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
