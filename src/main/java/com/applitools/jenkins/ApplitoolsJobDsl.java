package com.applitools.jenkins;

import hudson.Extension;
import javaposse.jobdsl.dsl.helpers.wrapper.WrapperContext;
import javaposse.jobdsl.plugin.ContextExtensionPoint;
import javaposse.jobdsl.plugin.DslExtensionMethod;

@Extension(optional = true)
public class ApplitoolsJobDsl extends ContextExtensionPoint {
    @DslExtensionMethod(context = WrapperContext.class)
    public Object applitools(String serverUrl) {
        return applitools(serverUrl, ApplitoolsCommon.NOTIFY_BY_COMPLETION);
    }

    @DslExtensionMethod(context = WrapperContext.class)
    public Object applitools(String serverUrl, boolean notifyByCompletion) {
        return new ApplitoolsBuildWrapper(serverUrl, notifyByCompletion, null);
    }

    @DslExtensionMethod(context = WrapperContext.class)
    public Object applitools(String serverUrl, boolean notifyByCompletion, String apiKey) {
        return new ApplitoolsBuildWrapper(serverUrl, notifyByCompletion, apiKey);
    }


    @DslExtensionMethod(context = WrapperContext.class)
    public Object applitools(String serverUrl, String apiKey) {
        return new ApplitoolsBuildWrapper(serverUrl, true, apiKey);
    }

    @DslExtensionMethod(context = WrapperContext.class)
    public Object applitools() {
        return new ApplitoolsBuildWrapper(ApplitoolsCommon.APPLITOOLS_DEFAULT_URL, ApplitoolsCommon.NOTIFY_BY_COMPLETION, null);
    }

}
