package com.frost_fox.jenkins.job_addon.addon.execution;


import com.frost_fox.jenkins.job_addon.Result;
import com.frost_fox.jenkins.job_addon.addon.AddonRepository;
import com.frost_fox.jenkins.job_addon.addon.AddonRepositoryException;
import com.frost_fox.jenkins.job_addon.description.FakeJobDescriptionFactories;
import com.frost_fox.jenkins.job_addon.description.JobDescriptionFactory;
import com.frost_fox.jenkins.job_addon.description.NoSuchAddon;
import com.frost_fox.jenkins.job_addon.description.NoSuchBuild;
import com.frost_fox.jenkins.job_addon.jenkins.JenkinsJob;
import com.frost_fox.jenkins.job_addon.jenkins.Jobs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.frost_fox.jenkins.job_addon.ResultAssert.assertThat;
import static com.frost_fox.jenkins.job_addon.jenkins.Jobs.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class AddonExecuteUseCaseTest {

    private static final String EXCEPTION_MESSAGE = "exception message";
    @Mock
    private AddonRepository addonRepository;
    @Mock
    private AddonExecutionManager executionManager;
    private JobDescriptionFactory factory;
    private AddonExecuteUseCase useCase;

    @Test
    public void returnsJobIdIfSuchJobExistsIn() throws AddonExecutionException {
        JenkinsJob job = Jobs.common();

        doReturn("id").when(executionManager).startAndGetId(any(), any());

        Result<String> result = useCase.execute(BUILD_ID, ADDON_ID, job);

        assertThat(result).successfulWith("id");
    }

    @Test
    public void returnsNoSuchBuildMessageIfSuchBuildNotExists(){
        JenkinsJob job = Jobs.common();

        Result<String> result = useCase.execute(NO_SUCH_BUILD_ID, ADDON_ID, job);

        assertThat(result).failedWith(NoSuchBuild.MESSAGE);
    }

    @Test
    public void returnsNoSuchAddonMessageIfSuchAddonNotExists(){
        JenkinsJob job = Jobs.common();

        Result<String> result = useCase.execute(BUILD_ID, NO_SUCH_ADDON_ID, job);

        assertThat(result).failedWith(NoSuchAddon.MESSAGE);
    }

    @Test
    public void returnsRepositoryErrorMessageIfRepositoryCantSaveAddonExecution() {
        JenkinsJob job = Jobs.common();

        doThrow(new AddonRepositoryException(EXCEPTION_MESSAGE)).when(addonRepository).save(any(AddonExecution.class));

        Result<String> result = useCase.execute(BUILD_ID, ADDON_ID, job);

        assertThat(result).failedWith(EXCEPTION_MESSAGE);
    }

    @Test
    public void returnsExecutionErrorMessageIfAddonCannotBeExecuted() throws AddonExecutionException {
        JenkinsJob job = Jobs.common();

        doThrow(new AddonExecutionException(EXCEPTION_MESSAGE)).when(executionManager).startAndGetId(any(), any());

        Result<String> result = useCase.execute(BUILD_ID, ADDON_ID, job);

        assertThat(result).failedWith(EXCEPTION_MESSAGE);
    }

    @Test
    public void returnsGenericErrorMessageIfSomethingWentWrong() throws AddonExecutionException {
        JenkinsJob job = Jobs.common();

        doThrow(new RuntimeException()).when(executionManager).startAndGetId(any(), any());

        Result<String> result = useCase.execute(BUILD_ID, ADDON_ID, job);

        assertThat(result).failedWith(AddonExecuteUseCase.GENERIC_ERROR);
    }

    @Before
    public void setUp() {
        factory = FakeJobDescriptionFactories.get().withManager(executionManager).one();
        useCase = new AddonExecuteUseCase(addonRepository, factory);
    }

}
