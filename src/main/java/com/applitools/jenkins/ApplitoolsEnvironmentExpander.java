package com.applitools.jenkins;
import org.jenkinsci.plugins.workflow.steps.EnvironmentExpander;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import hudson.EnvVars;
import java.io.IOException;

/**
 * Created by addihorowitz on 5/7/17.
 */
public class ApplitoolsEnvironmentExpander extends EnvironmentExpander {
    private static final long serialVersionUID = 1;
    private final Map<String, String> overrides;

    ApplitoolsEnvironmentExpander(HashMap<String, String> overrides) {
        this.overrides = overrides;

    }

    @Override
    public void expand(@Nonnull EnvVars env) throws IOException, InterruptedException {
        env.overrideAll(overrides);
    }
}
