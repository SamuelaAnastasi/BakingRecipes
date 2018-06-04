/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes;

import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingrecipes.ui.IngredientsStepsActivity;
import com.example.android.bakingrecipes.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public IntentsTestRule<MainActivity> mRecipeListTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickingRecipe_openIngredientsStepsActivity(){
        onView(withId(R.id.ll_recipes_container))
                .perform(click())
        .check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            onView(allOf(withId(R.id.rv_recipes_list)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(IngredientsStepsActivity.class.getName()));
    }
}
