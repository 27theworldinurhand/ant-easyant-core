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
package org.apache.easyant.example;

import java.io.IOException;
import java.util.Properties;

public class Example {
	public static void main(String[] args) throws IOException {
		System.out.println(new Example().sayHello("EasyAnt"));
	}

	/* 
	 * @param who Who says hello
	 */
	public String sayHello(String who) throws IOException {
		Properties props = new Properties();
		props.load(Example.class.getResourceAsStream("/main.properties"));
		return props.getProperty("example") + " " + who + "!";
	}
}
