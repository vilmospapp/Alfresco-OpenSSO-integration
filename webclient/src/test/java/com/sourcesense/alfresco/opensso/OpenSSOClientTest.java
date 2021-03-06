/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sourcesense.alfresco.opensso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.iplanet.am.util.SystemProperties;


public class OpenSSOClientTest {

	private OpenSSOClient openSSOClient;
	public static HashSet<String> cngroups;

	@Before
	public void setUp() throws Exception {
		openSSOClient = OpenSSOClient.instance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldExtractGroups() throws Exception {
		cngroups = new HashSet<String>();
		cngroups.add("cn=administration,ou=groups,dc=opensso,dc=java,dc=net");
		cngroups.add("cn=marketing,ou=groups,dc=opensso,dc=java,dc=net");
		cngroups.add("cn=RH,ou=groups,dc=opensso,dc=java,dc=net");
		
		List<String> groupNames = openSSOClient.extractGroupNameFromFQGroup(cngroups);
		
		assertEquals(3, groupNames.size());
		assertTrue(groupNames.contains("administration"));
		assertTrue(groupNames.contains("marketing"));
		assertTrue(groupNames.contains("RH"));
		
	}
	
	@Test
	public void shouldGetLoginURL() throws Exception {
		SystemProperties.initializeProperties("com.iplanet.am.naming.url","http://sample.com/ctx/namingservice");
		String loginURL = openSSOClient.getOpenSSOLoginURL();
		assertEquals("http://sample.com/ctx/UI/Login", loginURL);
	}

}
