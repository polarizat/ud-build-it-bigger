package com.udacity.gradle.builditbigger;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import static org.junit.Assert.assertNotNull;

@SmallTest
public class EndpointsAsyncTaskTest {

    @Rule
    public final ActivityTestRule<MainActivityFree> activityTestRule =
            new ActivityTestRule<>(MainActivityFree.class);

    @Test
    public void testAsyncTask() throws InterruptedException, ExecutionException {

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        endpointsAsyncTask.execute(activityTestRule.getActivity());

        String joke = endpointsAsyncTask.get();
        assertNotNull(joke);
    }

}