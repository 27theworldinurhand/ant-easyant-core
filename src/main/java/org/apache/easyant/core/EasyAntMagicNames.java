/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.easyant.core;

import org.apache.easyant.tasks.CoreRevisionCheckerTask;

/**
 * Magic names used within EasyAnt.
 * 
 * Not all magic names are here yet.
 * 
 * s
 */
public interface EasyAntMagicNames {

    /**
     * property for easyant file name. Value: {@value}
     */
    public static final String EASYANT_FILE = "easyant.file";

    /**
     * Name the default ivy instance name used by easyant Value: {@value}
     */
    public static final String EASYANT_IVY_INSTANCE = "easyant.ivy.instance";

    /**
     * Name of directory to store EasyAnt modules Value: {@value}
     */
    public static final String EASYANT_MODULES_DIR = "easyant.modules.dir";

    /**
     * Name the default ivy instance name used by projects Value: {@value}
     */
    public static final String PROJECT_IVY_INSTANCE = "project.ivy.instance";

    /**
     * Name of the property that contains the project ivy setting file Value: * * {@value}
     */
    public static final String PROJECT_IVY_SETTING_FILE = "project.ivy.settings.file";

    /**
     * Name of the property that contains the project ivy setting url Value: * * {@value}
     */
    public static final String PROJECT_IVY_SETTING_URL = "project.ivy.settings.url";

    /**
     * Name of the property that contains active build configuration Value: * * {@value}
     */
    public static final String ACTIVE_BUILD_CONFIGURATIONS = "active.build.configurations";

    /**
     * Name of the property that contains all active build configuration for the current project Value: {@value}
     */
    public static final String MAIN_CONFS = "main.confs";

    /**
     * Name of the property that contains build configuration available in current project Value: {@value}
     */
    public static final String AVAILABLE_BUILD_CONFIGURATIONS = "available.build.configurations";

    /**
     * Property name used to disable core revision check if this property is set to true it means that corerevision
     * checker feature will be disabled. Value: {@value}
     * 
     * @see CoreRevisionCheckerTask
     */
    public static final String SKIP_CORE_REVISION_CHECKER = "skip.corerevision.checker";

    /**
     * Property name containing the target directory Value: {@value}
     */
    public static final String TARGET = "target";

    /**
     * Name of the plugin service instance Value: {@value}
     */
    public static final String PLUGIN_SERVICE_INSTANCE = "plugin.service.instance";

    /**
     * Name of the property containing the default location of ivysettings file used by easyant ivy instance Value: * *
     * * {@value}
     */
    public static final String EASYANT_DEFAULT_IVYSETTINGS = "easyant.default.ivysettings.url";

    /**
     * Name of the property containing the default location of ivysettings file used by project ivy instance Value: * *
     * * {@value}
     */
    public static final String PROJECT_DEFAULT_IVYSETTINGS = "project.default.ivysettings.url";

    /**
     * Name of the property containing the url of the easyant core jar: * {@value}
     */
    public static final String EASYANT_CORE_JAR_URL = "easyant.core.jar.url";

    /**
     * Name of the property containing the log stategy for easyant modules Value: {@value}
     */
    public static final String MODULE_DOWNLOAD_LOG = "easyant.modules.download.log";

    /**
     * Name of the property containing the build-scoped repository name, if configured Value: {@value}
     */
    public static final String EASYANT_BUILD_REPOSITORY = "easyant.build.repository";

    public static final String META_TARGET = "meta.target";

    /**
     * Name of the property containing the appended to menu generator registry references Value: {@value}
     */
    public static final String MENU_GENERATOR_REGISTRY_REF = "menugen.ref";

    /**
     * Name of the property containing pre module targets. In a multi project context, those targets will be executed
     * before delagating to subprojects. Value : {@value}
     */
    public static final String PRE_MODULE_TARGETS = "pre.module.targets";

    /**
     * Name of the property containing path to user easyant ivysettings file Value: {@value}
     */
    public static final String USER_EASYANT_IVYSETTINGS = "user.easyant.ivysettings.file";

    /**
     * Name of the property containing path to global easyant ivysettings file Value: {@value}
     */
    public static final String GLOBAL_EASYANT_IVYSETTINGS = "global.easyant.ivysettings.file";

    /**
     * Name of the property specifyinf if build repository should be used Value: {@value}
     */
    public static final String USE_BUILD_REPOSITORY = "use.build.repository";

    /**
     * property for easyant offline mode. Value: {@value}
     */
    public static final String EASYANT_OFFLINE = "easyant.offline";

    /**
     * Property specifying if user ivysettings should be ignored Value: {@value}
     */
    public static final String IGNORE_USER_IVYSETTINGS = "ignore.user.ivysettings";

    /***
     * Property specifying offline project resolver name Value: {@value}
     */
    public static final String OFFLINE_PROJECT_RESOLVER = "project.buildscope.resolver";

    /***
     * Property specifying offline easyant resolver name Value: {@value}
     */
    public static final String OFFLINE_EASYANT_RESOLVER = "easyant.buildscope.resolver";

    /**
     * Property specifying offline base directory Value: {@value}
     */
    public static final String OFFLINE_BASE_DIRECTORY = "offline.base.directory";

    /**
     * Specify if easyant is running in audit mode (plugin service for instance) Value: {@value}
     */
    public static final String AUDIT_MODE = "audit.mode";

    /**
     * Property specifying executed targets in current project
     */
    public static final String PROJECT_EXECUTED_TARGETS = "project.executed.targets";

    /**
     * Name of EasyAntEngine reference
     */
    public static final String EASYANT_ENGINE_REF = "easyant.engine.ref";
}
