package com.applitools.jenkins;

import hudson.model.Run;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Encapsulates the Applitools' status display action.
 */
public class ApplitoolsStatusDisplayAction extends AbstractApplitoolsStatusDisplayAction {
    private static final String TIMESTAMP_PATTERN = "yyyyMMddHHmmss";
    private final String projectName;
    private final int buildNumber;
    private final Calendar buildTimestamp;
    private String serverURL;
    @SuppressWarnings("rawtypes")
    private final Run build;
    private Map<String, String> applitoolsValuesFromArtifacts;
    private static final Logger logger = Logger.getLogger(ApplitoolsStatusDisplayAction.class.getName());

    @SuppressWarnings("rawtypes")
    public ApplitoolsStatusDisplayAction(Run build) {
        this.projectName = build.getParent().getDisplayName();
        this.buildNumber = build.getNumber();
        this.buildTimestamp = build.getTimestamp();
        this.serverURL = null;
        this.build = build;
        this.applitoolsValuesFromArtifacts = new HashMap<>();
        for (Object property : build.getParent().getAllProperties()) {
            if (property instanceof ApplitoolsProjectConfigProperty) {
                this.serverURL = ((ApplitoolsProjectConfigProperty) property).getServerURL();
                break;
            }
        }
//        this.iframeText = getIframeText();
    }


    @Override
    public String getIframeText() {
        this.applitoolsValuesFromArtifacts = 
            ApplitoolsCommon.checkApplitoolsArtifacts(
                this.build.getArtifacts(), 
                this.build.getArtifactManager().root());
        try {
            String iframeURL = generateIframeURL();
            if (iframeURL == null)
            {
                // In case Applitools support has been removed from the project,
                // remove iframes from old reports
                return "";
            }

            return "<iframe id=\"frame\" src=\"" + iframeURL +
                    "\" style=\"overflow:hidden;overflow-x:hidden;overflow-y:hidden;height:710px;width:1024px;max-width:100%;resize:vertical;\"></iframe>\n";
        }
        catch (Exception ex)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            logger.warning(sw.toString());
            return "";
        }
    }

    private String generateBatchId()
    {
        return generateBatchId(this.projectName, this.buildNumber, this.buildTimestamp, this.applitoolsValuesFromArtifacts);
    }

    private String generateIframeURL()
    {
        if (serverURL == null || serverURL.isEmpty())
        {
            // In case Applitools support has been removed from the project
            return null;
        }

        return serverURL + "/app/batchesnoauth/?startInfoBatchId=" + generateBatchId() + "&hideBatchList=true&intercom=false&agentId=eyes-jenkins-1.15.2";
    }

    public static String generateBatchId(String projectName, int buildNumber, Calendar buildTimestamp) {
      return generateBatchId(projectName, buildNumber, buildTimestamp, null);
    }

    public static String generateBatchId(String projectName, int buildNumber, Calendar buildTimestamp,
                                         Map<String, String> applitoolsValuesFromArtifacts)
    {
        if (applitoolsValuesFromArtifacts != null &&
                applitoolsValuesFromArtifacts.containsKey(ApplitoolsEnvironmentUtil.APPLITOOLS_BATCH_ID)) {
            return applitoolsValuesFromArtifacts.get(ApplitoolsEnvironmentUtil.APPLITOOLS_BATCH_ID);
        } else if (System.getenv().containsKey("APPLITOOLS_BATCH_ID")) {
            return System.getenv().get("APPLITOOLS_BATCH_ID");
        } else {
            final String BATCH_ID_PREFIX = "jenkins";
            SimpleDateFormat buildDate = new SimpleDateFormat(TIMESTAMP_PATTERN);
            buildDate.setTimeZone(buildTimestamp.getTimeZone());

            return BATCH_ID_PREFIX + "-" + projectName + "-" + buildNumber + "-" + buildDate.format(buildTimestamp.getTime());
        }
    }


}

