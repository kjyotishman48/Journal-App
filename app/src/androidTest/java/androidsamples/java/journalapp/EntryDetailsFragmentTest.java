package androidsamples.java.journalapp;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.AccessibilityChecks;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Instrumented tests for the {@link EntryDetailsFragment}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EntryDetailsFragmentTest {

  @Rule
  public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

  @BeforeClass
  public static void enableAccessibilityChecks() {
    androidx.test.espresso.accessibility.AccessibilityChecks.enable();
  }

  @Test
  public void testNavigationToEntryListFragment() {
    // Create a TestNavHostController
    TestNavHostController navController = new TestNavHostController(
        ApplicationProvider.getApplicationContext());

    FragmentScenario<EntryListFragment> entryDetailsFragmentFragmentScenario
        = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_JournalApp, (FragmentFactory) null);

    entryDetailsFragmentFragmentScenario.onFragment(fragment -> {
      // Set the graph on the TestNavHostController
      navController.setGraph(R.navigation.nav_graph);

      // Make the NavController available via the findNavController() APIs
      Navigation.setViewNavController(fragment.requireView(), navController);
    });

    // Verify that performing a click changes the NavController's state
    onView(ViewMatchers.withId(R.id.btn_add_entry)).perform(click());
    assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.entryDetailsFragment));

  }

  @Test
  public void testRecyclerView() throws InterruptedException {
    //At least create one Journal Entry for this test to run
    TestNavHostController navController = new TestNavHostController(
            ApplicationProvider.getApplicationContext());

    FragmentScenario<EntryListFragment> entryDetailsFragmentFragmentScenario
            = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_JournalApp, (FragmentFactory) null);

    entryDetailsFragmentFragmentScenario.onFragment(fragment -> {
      navController.setGraph(R.navigation.nav_graph);
      Navigation.setViewNavController(fragment.requireView(), navController);
    });

    onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

    assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.entryDetailsFragment));

  }
}