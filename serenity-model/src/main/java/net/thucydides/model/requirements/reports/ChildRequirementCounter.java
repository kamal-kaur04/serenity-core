package net.thucydides.model.requirements.reports;

import net.thucydides.model.domain.TestResult;
import net.thucydides.model.reports.TestOutcomes;
import net.thucydides.model.requirements.model.Requirement;

public class ChildRequirementCounter implements RequirmentCalculator {

    private final Requirement requirement;
    private final TestOutcomes testOutcomes;

    public ChildRequirementCounter(Requirement requirement, TestOutcomes testOutcomes) {
        this.requirement = requirement;
        this.testOutcomes = testOutcomes;
    }


    @Override
    public long countAllSubrequirements() {
        return requirement.getChildren().size();
    }

    @Override
    public long countSubrequirementsWithResult(TestResult expectedResult) {
        return requirement.getChildren().stream()
                .filter(req -> testResultFor(req) == expectedResult)
                .count();
    }

    @Override
    public long countSubrequirementsWithNoTests() {
        return requirement.getChildren().stream()
                .filter(req -> testOutcomes.forRequirement(req).getOutcomes().isEmpty())
                .count();
    }

    private TestResult testResultFor(Requirement req) {

        if (testOutcomes.forRequirement(req).getOutcomes().isEmpty()) {
            return TestResult.UNDEFINED;
        }

        return testOutcomes.forRequirement(req).getResult();
    }
}
