/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import com.example.android.bakingrecipes.data.Recipe;
import com.example.android.bakingrecipes.ui.IngredientsStepsActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IngredientsStepsActivityTest {
    private Recipe recipe = Recipe.dummyObject();

    @Rule
    public ActivityTestRule<IngredientsStepsActivity> mActivityRule =
            new ActivityTestRule<IngredientsStepsActivity>(IngredientsStepsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent intent = new Intent(targetContext, IngredientsStepsActivity.class);
                    intent.putExtra(IngredientsStepsActivity.KEY_CURRENT_RECIPE, recipe);
                    return intent;
                }
            };

    @Test
    public void clickingStepList_DisplaysSelectedStep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


            int clickedStep = 0;
        // Check steps Recycler is displayed
        onView(withId(R.id.rv_steps_list)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_steps_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(clickedStep, click()));
        // Check root view of step is displayed after click
        onView(withId(R.id.ll_step_content)).check(matches(isDisplayed()));
        // Check step's long description matches dummy step long description
        onView(withId(R.id.tv_step_longDescr)).check(matches(withText(recipe.getSteps()
                .get(clickedStep).getDescription())));
    }
}
