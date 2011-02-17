/* 
 *  Copyright 2008-2010 the EasyAnt project
 * 
 *  See the NOTICE file distributed with this work for additional information
 *  regarding copyright ownership. 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and 
 *  limitations under the License.
 */

package org.apache.easyant.core.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.ivy.core.cache.ResolutionCacheManager;
import org.apache.ivy.core.report.ConfigurationResolveReport;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.plugins.report.XmlReportWriter;
import org.apache.ivy.util.FileUtil;
import org.apache.ivy.util.Message;

/**
 * A Report outputter implementation using {@link XmlReportWriter} to write xml reports to the
 * resolution cache.
 */
public class XmlEasyAntReportOutputter {
	
    public static final String CONSOLE = "console";

    public static final String XML = "xml";

    private XMLEasyAntReportWriter writer = new XMLEasyAntReportWriter();
    
    public String getName() {
        return XML;
    }

    public void output(
            ResolveReport report, ResolutionCacheManager cacheMgr, ResolveOptions options,EasyAntReport easyAntReport, boolean displaySubProperties) 
            throws IOException {
        String[] confs = report.getConfigurations();
        for (int i = 0; i < confs.length; i++) {
            output(report.getConfigurationReport(confs[i]), report.getResolveId(), confs, cacheMgr,easyAntReport,displaySubProperties);
        }
    }

    public void output(ConfigurationResolveReport report, String resolveId, 
            String[] confs, ResolutionCacheManager cacheMgr, EasyAntReport easyAntReport , boolean displaySubProperties) 
            throws IOException {
    	writer.setDisplaySubProperties(displaySubProperties);
        File reportFile = cacheMgr.getConfigurationResolveReportInCache(
            resolveId, report.getConfiguration());
        File reportParentDir = reportFile.getParentFile();
        reportParentDir.mkdirs();
        OutputStream stream = new FileOutputStream(reportFile);
        writer.output(report, confs, stream,easyAntReport);
        stream.close();

        Message.verbose("\treport for " + report.getModuleDescriptor().getModuleRevisionId()
            + " " + report.getConfiguration() + " produced in " + reportFile);

        File reportXsl = new File(reportParentDir, "ivy-report.xsl");
        File reportCss = new File(reportParentDir, "ivy-report.css");
        if (!reportXsl.exists()) {
            FileUtil.copy(XmlEasyAntReportOutputter.class.getResource("ivy-report.xsl"), reportXsl,
                null);
        }
        if (!reportCss.exists()) {
            FileUtil.copy(XmlEasyAntReportOutputter.class.getResource("ivy-report.css"), reportCss,
                null);
        }
    }
}

