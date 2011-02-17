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

package org.apache.easyant.core.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.easyant.core.EasyAntConfiguration;
import org.apache.easyant.core.descriptor.PluginDescriptor;
import org.apache.ivy.util.ContextualSAXHandler;
import org.apache.ivy.util.Message;
import org.apache.ivy.util.XMLHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class EasyAntConfigParser {

	private boolean validate = true;

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public URL getEasyAntConfigSchema() {
		return getClass().getResource(
				"/org/apache/easyant/core/easyant-config.xsd");
	}

	public EasyAntConfiguration parseAndMerge(final URL configUrl,
			final EasyAntConfiguration easyAntConfiguration) throws Exception {
		ConfigParser parser = new ConfigParser();

		URL schemaURL = null;

		if (isValidate()) {
			schemaURL = getEasyAntConfigSchema();
		}
		try {
			parser.setConfigUrl(configUrl);
			parser.setEasyAntConfiguration(easyAntConfiguration);
			XMLHelper.parse(configUrl, schemaURL, parser, null);
			parser.checkErrors();
		} catch (ParserConfigurationException ex) {
			IllegalStateException ise = new IllegalStateException(ex
					.getMessage()
					+ " in " + configUrl);
			ise.initCause(ex);
			throw ise;

		} catch (Exception e) {

			throw new Exception("Can't parse " + configUrl, e);
		}
		return easyAntConfiguration;
	}

	public EasyAntConfiguration parse(URL configUrl) throws Exception {
		EasyAntConfiguration easyAntConfiguration = new EasyAntConfiguration();
		return parseAndMerge(configUrl, easyAntConfiguration);
	}

	public static class ConfigParser extends ContextualSAXHandler {
		private List errors = new ArrayList();
		private URL configUrl;
		private EasyAntConfiguration easyAntConfiguration;

		public URL getConfigUrl() {
			return configUrl;
		}

		public void setConfigUrl(URL configUrl) {
			this.configUrl = configUrl;
		}

		public EasyAntConfiguration getEasyAntConfiguration() {
			return easyAntConfiguration;
		}

		public void setEasyAntConfiguration(
				EasyAntConfiguration easyAntConfiguration) {
			this.easyAntConfiguration = easyAntConfiguration;
		}

		protected void addError(String msg) {
			if (configUrl != null) {
				errors.add(msg + " in " + configUrl + "\n");
			} else {
				errors.add(msg + "\n");
			}
		}

		protected void checkErrors() throws ParseException {
			if (!errors.isEmpty()) {
				throw new ParseException(errors.toString(), 0);
			}
		}

		private String getLocationString(SAXParseException ex) {
			StringBuffer str = new StringBuffer();

			String systemId = ex.getSystemId();
			if (systemId != null) {
				int index = systemId.lastIndexOf('/');
				if (index != -1) {
					systemId = systemId.substring(index + 1);
				}
				str.append(systemId);
			} else if (configUrl != null) {
				str.append(configUrl.toString());
			}
			str.append(':');
			str.append(ex.getLineNumber());
			str.append(':');
			str.append(ex.getColumnNumber());

			return str.toString();

		}

		public void warning(SAXParseException ex) {
			Message.warn("xml parsing: " + getLocationString(ex) + ": "
					+ ex.getMessage());
		}

		public void error(SAXParseException ex) {
			addError("xml parsing: " + getLocationString(ex) + ": "
					+ ex.getMessage());
		}

		public void fatalError(SAXParseException ex) throws SAXException {
			addError("[Fatal Error] " + getLocationString(ex) + ": "
					+ ex.getMessage());
		}

		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {

			super.startElement(uri, localName, name, attributes);
			if ("easyant-config/ivysettings".equals(getContext())) {
				if (attributes.getValue("url") != null
						&& !attributes.getValue("url").equals("")) {
					easyAntConfiguration.setEasyantIvySettingsUrl(attributes
							.getValue("url"));
				}
				if (attributes.getValue("file") != null
						&& !attributes.getValue("file").equals("")) {
					easyAntConfiguration.setEasyantIvySettingsFile(attributes
							.getValue("file"));
				}
			}
			if ("easyant-config/system-plugins/plugin".equals(getContext())) {
				PluginDescriptor pluginDescriptor = new PluginDescriptor();
				String org = attributes.getValue("org") != null ? attributes
						.getValue("org") : attributes.getValue("organisation");
				pluginDescriptor.setOrganisation(org);
				pluginDescriptor.setModule(attributes.getValue("module"));
				String rev = attributes.getValue("rev") != null ? attributes
						.getValue("rev") : attributes.getValue("revision");
				pluginDescriptor.setRevision(rev);
				pluginDescriptor.setMrid(attributes.getValue("mrid"));
				pluginDescriptor.setAs(attributes.getValue("as"));
				boolean mandatory = false;
				if (attributes.getValue("mandatory") != null
						&& attributes.getValue("mandatory").equals("true"))
					mandatory = true;
				;
				pluginDescriptor.setMandatory(mandatory);
				String mode = attributes.getValue("mode");
				if (mode != null) {
					pluginDescriptor.setMode(mode);
				} else
					pluginDescriptor.setMode("include");
				easyAntConfiguration.addSystemPlugin(pluginDescriptor);
			}
			if ("easyant-config/properties/property".equals(getContext())) {
				if (attributes.getValue("file") != null
						|| attributes.getValue("url") != null) {
					Properties properties = new Properties();

					try {
						InputStream is = null;
						if (attributes.getValue("file") != null) {
							File f = new File(attributes.getValue("file"));
							is = new FileInputStream(f);
							properties.load(is);
							is.close();
						} else if (attributes.getValue("url") != null) {
							URL url = new URL(attributes.getValue("url"));
							is = url.openStream();
							properties.load(is);
							is.close();
						}
						for (Iterator iterator = properties.keySet().iterator(); iterator
								.hasNext();) {
							String key = (String) iterator.next();
							easyAntConfiguration.getDefinedProps().put(key,
									properties.get(key));
						}

					} catch (Exception e) {
						if (attributes.getValue("file") != null) {
							throw new SAXException(
									"can't read property file at : "
											+ attributes.getValue("file"));
						} else if (attributes.getValue("url") != null) {
							throw new SAXException(
									"can't read property file at : "
											+ attributes.getValue("url"));
						}

					}
				} else if (attributes.getValue("name") != null) {
					easyAntConfiguration.getDefinedProps().put(
							attributes.getValue("name"),
							attributes.getValue("value"));
				}

			}

		}
	}

}
