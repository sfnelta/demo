package de.nelta.demo.api.utils;

import java.util.List;

import io.qameta.allure.listener.ContainerLifecycleListener;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.FixtureResult;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.model.TestResult;
import io.qameta.allure.model.TestResultContainer;

public class AllureListener implements TestLifecycleListener, ContainerLifecycleListener {

private boolean checkIfResultContainsServiceHooks(String resultName){
        return resultName.contains("ServiceHooks");
    }

    @Override
    public void beforeTestWrite(final TestResult result) {
        if (result.getStatus().equals(Status.BROKEN))
            result.setStatus(Status.FAILED);

        List<StepResult> steps = result.getSteps();
        for (int i = steps.size() - 1; i >= 0; i--)
            if (checkIfResultContainsServiceHooks(steps.get(i).getName()))
                steps.remove(i);
    }

    @Override
    public void beforeContainerWrite(TestResultContainer container) {
        List<FixtureResult> beforeSteps = container.getBefores();
        for (int i = beforeSteps.size() - 1; i >= 0; i--)
            if (checkIfResultContainsServiceHooks(beforeSteps.get(i).getName()))
                beforeSteps.remove(i);

        List<FixtureResult> afterSteps = container.getAfters();
        for (int i = afterSteps.size() - 1; i >= 0; i--)
            if (checkIfResultContainsServiceHooks(afterSteps.get(i).getName()))
                afterSteps.remove(i);
    }
}