<?xml version="1.0"?>

	<!--
		Generate xooki documentation from a buildtype ant file. Stylesheet
		parameters are passed in by eadoc.ant at runtime.
	-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ea="antlib:org.apache.easyant"
	xmlns:exsl="http://exslt.org/common" extension-element-prefixes="exsl"
	exclude-result-prefixes="#all">

	<xsl:param name="pluginOrganisation">org.apache.easyant.plugins</xsl:param>
	<xsl:param name="typeOrganisation">org.apache.easyant.buildtypes</xsl:param>

	<!-- xooki TOC level for the output HTML document -->
	<xsl:param name="tocLevel">2</xsl:param>

	<xsl:output method="html" indent="yes" omit-xml-declaration="yes"
		doctype-public="-//W3C//DTD HTML 4.01//EN" doctype-system="http://www.w3.org/TR/html4/strict.dtd" />

	<xsl:template match="/ivy-report/dependencies/module/revision">
		<xsl:variable name="moduleVersion" select="@name" />
		<xsl:variable name="moduleDescription" select="description" />
		<xsl:variable name="typeName" select="../@name" />
		<xsl:variable name="moduleOrganisation" select="../@organisation"/>
		<xsl:comment>
			Licensed to the Apache Software Foundation (ASF) under one
			or more contributor license agreements. See the NOTICE file
			distributed with this work for additional information
			regarding copyright ownership. The ASF licenses this file
			to you under the Apache License, Version 2.0 (the
			"License"); you may not use this file except in compliance
			with the License. You may obtain a copy of the License at

			http://www.apache.org/licenses/LICENSE-2.0

			Unless required by applicable law or agreed to in writing,
			software distributed under the License is distributed on an
			"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
			KIND, either express or implied. See the License for the
			specific language governing permissions and limitations
			under the License.
		</xsl:comment>
		<xsl:text>
		</xsl:text>
		<xsl:comment>
			<xsl:value-of select="$typeName" />
			documentation autogenerated by EADoc
		</xsl:comment>
		<xsl:text>
		</xsl:text>
		<html>
			<head>
				<title>
					EasyAnt BuildType <xsl:value-of select="$typeName" /><xsl:text> </xsl:text><xsl:value-of select="concat('v', $moduleVersion)" />
				</title>
				<script type="text/javascript">
					var xookiConfig = {level:<xsl:value-of select="$tocLevel" />};
				</script>
				<script type="text/javascript" src="../../xooki/xooki.js">
					<xsl:text></xsl:text>
				</script>
			</head>
			<body>
				<textarea id="xooki-source">

					<xsl:call-template name="outputHeader">
						<xsl:with-param name="typeName" select="$typeName" />
						<xsl:with-param name="typeVersion" select="$moduleVersion" />
					</xsl:call-template>

					<xsl:if test="$moduleDescription">
						<h2>Overview</h2>
						<p>
							<xsl:copy-of select="$moduleDescription"/>
						</p>
					</xsl:if>
					
					<div id="sample">
						<h2>Example</h2>
						You can use this buildtype by adding the following code in your module.ivy (or module.ant) file
						<code type="xml">
							<ea:build>
								<xsl:attribute name="organisation">
									<xsl:value-of select="$moduleOrganisation"/>
								</xsl:attribute>
								<xsl:attribute name="module">
									<xsl:value-of select="$typeName"/>
								</xsl:attribute>
								<xsl:attribute name="rev">
									<xsl:value-of select="$moduleVersion"/>
								</xsl:attribute>
							</ea:build>
						</code>
						<xsl:if test="$moduleOrganisation='org.apache.easyant.buildtypes'">
							<div id="note">Note: The organisation argument in <b>ea:build</b> tag is optional. If not specified easyant will use the default one (org.apache.easyant.buildtypes).</div>					
							Shorter form : 
							<code type="xml">
								<ea:build>
									<xsl:attribute name="module">
										<xsl:value-of select="$typeName"/>
									</xsl:attribute>
									<xsl:attribute name="rev">
										<xsl:value-of select="$moduleVersion"/>
									</xsl:attribute>
								</ea:build>
							</code>
						</xsl:if>
					</div>
					<xsl:call-template name="outputPhaseDescriptions" />
					<xsl:call-template name="outputParameterTable" />


				</textarea>
				<script type="text/javascript">xooki.postProcess();</script>
			</body>
		</html>

	</xsl:template>
	

	<!-- Output type name and generate links to extended types -->
	<xsl:template name="outputHeader">
		<xsl:param name="typeName" />
		<xsl:param name="typeVersion" />
		<h1>
			BuildType
			<xsl:value-of select="$typeName" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="concat('v', $typeVersion)" />
		</h1>
		<xsl:if test="easyant/imports/import[@organisation = $typeOrganisation]">
			<div class="parent-types">
				extends
				<xsl:for-each
					select="easyant/imports/import[@organisation = $typeOrganisation]">
					<xsl:variable name="parentName" select="@name" />
					<xsl:element name="a">
						<xsl:attribute name="href"><xsl:value-of
							select="concat($parentName, '.html')" /></xsl:attribute>
						<xsl:value-of select="$parentName" />
					</xsl:element>
				</xsl:for-each>
			</div>
		</xsl:if>
		<hr />
	</xsl:template>

	<!-- Generate an HTML table summarizing parameters used by the type -->
	<xsl:template name="outputParameterTable">
		<xsl:if test="easyant/properties/property">
			<h2>Parameters</h2>
			<p>The following property values can be changed to tune the behavior of this buildtype.
				Required parameters must be defined upon import, or the build will fail.</p>
			<table class="sortable" id="type-parameters">
				<thead>
					<tr>
						<th>Parameter</th>
						<th>Required?</th>
						<th class="unsortable">Description</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="easyant/properties/property">
						<xsl:sort select="@name" />
						<tr>
							<td>
								<xsl:value-of select="@name" />
							</td>
							<td>
								<xsl:choose>
									<xsl:when test="@required = 'true'">Yes</xsl:when>
									<xsl:otherwise>No</xsl:otherwise>
								</xsl:choose>
							</td>
							<td>
								<xsl:if test="@description">
									<xsl:value-of select="@description" /><br/>
								</xsl:if>
								<xsl:if test="not(@value) and @default">
									<span class="bold">Default: </span><span id="defaultValue"><xsl:value-of select="@default" /></span><br/>
								</xsl:if> 
								<xsl:if test="@value">
									<span class="bold">BuildType value: </span><span id="buildTypeValue"><xsl:value-of select="@value" /></span>
								</xsl:if>
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</xsl:if>
	</xsl:template>

	<!--
		Generate a list describing what the type does during each build phase
	-->
	<xsl:template name="outputPhaseDescriptions">

		<!--
			an XML fragment that condenses nested phases and targets into a flat
			list, so that we can then make an output summary table.
		-->
		<xsl:variable name="typeSummary">
			<project>
				<xsl:call-template name="add-plugin-targets">
					<xsl:with-param name="as" select="" />
				</xsl:call-template>
			</project>
		</xsl:variable>
		<xsl:variable name="phaseTargets" select="exsl:node-set($typeSummary)/project" />

		<h2>Plugins</h2>
		<p>
			This build type loads each of the following	<a href="../plugins/plugins.html">plugins</a>:
		</p>
		<div class="type-plugins">
			<xsl:for-each select="easyant/imports/import[@organisation=$pluginOrganisation]">
				<xsl:variable name="pluginName" select="@name"/>
				<xsl:if test="position() &gt; 1"><xsl:text>, </xsl:text></xsl:if>
				<a href="{concat('../plugins/', $pluginName, '.html')}">
					<xsl:value-of select="$pluginName"/>
				</a>
			</xsl:for-each>
		</div>
		

		<h2>Build Phases</h2>
		<p>
			This build type takes the following actions during each	<a href="../phases.html">build phase</a> :
		</p>
		<table id="buildtype-phases">
		<thead>
			<tr class="header-row">
				<th>Phase</th>
				<th>Description</th>
			</tr>
		</thead>
		<tbody>
			<xsl:for-each
					select="$phaseTargets/phase[not(@name = preceding::phase/@name)]">
					<xsl:variable name="currentPhase" select="string(@name)" />
					<xsl:variable name="currentPhaseTargets"
						select="$phaseTargets/target[@phase=$currentPhase]" />
					<xsl:if test="$currentPhaseTargets">
						<tr class="phase-row">
							<td>
								<span class="phase-name">
									<xsl:value-of select="$currentPhase" />
								</span>
							</td>
							<td>
								<span class="phase-description">
									<xsl:value-of select="@description" />
								</span>
								<table class="sortable" id="type-phases">
									<thead>
										<tr class="header-row">
											<th>Target</th>
											<th>Description</th>
											<th>From Plugin</th>
										</tr>
									</thead>
									<tbody>
										<xsl:for-each select="$currentPhaseTargets">
											<tr>
												<td class="target-name">
													<xsl:value-of select="@name" />
												</td>
												<td>
													<xsl:value-of select="@description" />
												</td>
													<td>
														<xsl:if test="@plugin">
															<a>
																<xsl:attribute name="href"><xsl:value-of
																	select="concat('../plugins/', @plugin, '.html')" /></xsl:attribute>
																<xsl:value-of select="@plugin" />
															</a>
														</xsl:if>
													</td>
												
											</tr>
										</xsl:for-each>
									</tbody>
								</table>
							</td>
						</tr>
	
					</xsl:if>
				</xsl:for-each>
			</tbody>
		</table>

		<xsl:if test="$phaseTargets/target[not(@phase or starts-with(@name, '-'))]">
			<h2>Extra Targets</h2>
			<p>
				This type defines some extra Ant targets not attached to any <a href="../phases.html">build phase</a> :
			</p>
			<table class="sortable" id="type-phases">
				<thead>
					<tr class="header-row">
						<th>Target</th>
						<th class="unsortable">Description</th>
						<th>From Plugin</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each
						select="$phaseTargets/target[not(@phase or starts-with(@name, '-'))]">
						<tr>
							<td class="target-name">
								<xsl:value-of select="@name" />
							</td>
							<td>
								<xsl:value-of select="@description" />
							</td>
							<xsl:if test="@plugin">
								<td>
									<a>
										<xsl:attribute name="href"><xsl:value-of
											select="concat('../plugins/', @plugin, '.html')" /></xsl:attribute>
										<xsl:value-of select="@plugin" />
									</a>
								</td>
							</xsl:if>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</xsl:if>
	</xsl:template>

	<!--
		for the current module, output one 'target' element for each target in
		that module, recursively adding targets for imported plugins. The
		optional parameter 'as' is prepended to the name of each target.
	-->
	<xsl:template name="add-plugin-targets">
		<xsl:param name="as" />
		<xsl:for-each
			select="easyant/imports/import[@organisation=$pluginOrganisation]">
			<xsl:variable name="pluginName" select="@name" />
			<xsl:variable name="prefix" select="concat($as, @as)" />
			<xsl:for-each select="easyant/phases/phase">
				<phase>
					<xsl:attribute name="name"><xsl:value-of
						select="@name" /></xsl:attribute>
					<xsl:attribute name="description"><xsl:value-of
						select="@description" /></xsl:attribute>
				</phase>
			</xsl:for-each>
			<xsl:for-each select="easyant/targets/target">
				<target>
					<xsl:attribute name="name"><xsl:value-of
						select="concat($prefix, @name)" /></xsl:attribute>
					<xsl:attribute name="plugin"><xsl:value-of
						select="$pluginName" /></xsl:attribute>
					<xsl:for-each select="@*[name() != 'name']">
						<xsl:copy-of select="." />
					</xsl:for-each>
				</target>
			</xsl:for-each>
			<xsl:call-template name="add-plugin-targets">
				<xsl:with-param name="as" select="$prefix" />
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template match="text()"/>

</xsl:stylesheet>
