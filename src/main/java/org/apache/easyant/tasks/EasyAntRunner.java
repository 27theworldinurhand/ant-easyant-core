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

package org.apache.easyant.tasks;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.easyant.core.EasyAntConfiguration;
import org.apache.easyant.core.EasyAntEngine;
import org.apache.easyant.core.factory.EasyantConfigurationFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class EasyAntRunner extends Task {

	private boolean fork = true;

	private EasyAntConfiguration easyantConfiguration = new EasyAntConfiguration();

	@Override
	public void execute() throws BuildException {
		EasyAntEngine eaEngine = new EasyAntEngine(getEasyantConfiguration());
		if (fork)  {
			eaEngine.doBuild();
		} else {
			eaEngine.initProject(getProject());
			getProject().executeTargets(getEasyantConfiguration().getTargets());
		}
	}

	public boolean isFork() {
		return fork;
	}

	public void setFork(boolean fork) {
		this.fork = fork;
	}

	public void setTargets(String targets) {
		String[] targetsArray = targets.split(",");
		for (int i = 0; i < targetsArray.length; i++) {
			getEasyantConfiguration().getTargets().add(targetsArray[i]);
		}
	}

	public void setConfigurationFile(String configurationFile) {
		File f = new File(configurationFile);
		try {
			EasyantConfigurationFactory.getInstance()
					.createConfigurationFromFile(getEasyantConfiguration(),
							f.toURL());
		} catch (Exception e) {
			throw new BuildException(
					"Can't create easyantConfiguration from File "
							+ configurationFile, e);
		}
	}

	public void setConfigurationUrl(String configurationUrl) {
		try {
			URL url = new URL(configurationUrl);
			EasyantConfigurationFactory
					.getInstance()
					.createConfigurationFromFile(getEasyantConfiguration(), url);

		} catch (Exception e) {
			throw new BuildException(
					"Can't create easyantConfiguration from URL "
							+ configurationUrl, e);
		}
	}

	public void setBuildConfiguration(String buildConfiguration) {
		String[] buildConfs = buildConfiguration.split(",");
		Set<String> buildConfigurations = new HashSet<String>();
		for (String conf : buildConfs) {
			buildConfigurations.add(conf);
		}
		getEasyantConfiguration().setActiveBuildConfigurations(
				buildConfigurations);
	}

	public void setModuleIvy(String moduleIvy) {
		File f = new File(moduleIvy);
		getEasyantConfiguration().setBuildModule(f);
	}

	public void setModuleAnt(String moduleAnt) {
		File f = new File(moduleAnt);
		getEasyantConfiguration().setBuildFile(f);
	}

	public EasyAntConfiguration getEasyantConfiguration() {
		return easyantConfiguration;
	}

	public void setEasyantConfiguration(
			EasyAntConfiguration easyantConfiguration) {
		this.easyantConfiguration = easyantConfiguration;
	}

}
