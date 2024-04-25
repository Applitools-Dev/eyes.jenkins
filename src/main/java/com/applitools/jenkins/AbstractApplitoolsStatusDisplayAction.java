package com.applitools.jenkins;

import hudson.model.Action;

/**
 * Base class for Applitools status display action.
 */
public abstract class AbstractApplitoolsStatusDisplayAction implements Action {

    public abstract String getIframeText();

    public String getIconFileName() {
        return null;
    }

    public String getDisplayName() {
        return null;
    }

    public String getUrlName() {
        return null;
    }
}
