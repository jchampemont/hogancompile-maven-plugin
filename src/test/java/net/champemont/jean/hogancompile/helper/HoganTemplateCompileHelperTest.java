/*
 *  Copyright 2014 Jean Champémont
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

package net.champemont.jean.hogancompile.helper;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

public class HoganTemplateCompileHelperTest {
	private String EMPTY_TEMPLATE_NAME       = "empty";
	private String EMPTY_MUSTACHES_FILENAME  = "empty.mustaches";
	private String EMPTY_JS_FILENAME         = "empty.js";
	
	private String SIMPLE_TEMPLATE_NAME      = "simple";
	private String SIMPLE_MUSTACHES_FILENAME = "simple.mustaches";
	private String SIMPLE_JS_FILENAME        = "simple.js";
	
	private String TEMPLATE_HEADER           = "var templates = templates || {};";
	
	@Test
	public void doTestEmpty() throws IOException {
		assertEquals(
				  readFileAsString(EMPTY_JS_FILENAME).trim()
				, HoganTemplateCompileHelper
					.compileHoganTemplate(readFileAsString(EMPTY_MUSTACHES_FILENAME), EMPTY_TEMPLATE_NAME).trim()
				);
	}
	
	@Test
	public void doTestSimple() throws IOException {
		assertEquals(
				  readFileAsString(SIMPLE_JS_FILENAME).trim()
				, HoganTemplateCompileHelper
					.compileHoganTemplate(readFileAsString(SIMPLE_MUSTACHES_FILENAME), SIMPLE_TEMPLATE_NAME).trim()
				);
	}
	
	@Test
	public void doTestTemplateHeader() {
		assertEquals(TEMPLATE_HEADER.trim(), HoganTemplateCompileHelper.getTemplateHeader().trim());
	}
    
	private static String readFileAsString(String fileName) throws IOException{
		InputStream file = HoganTemplateCompileHelperTest.class.getClassLoader().getResourceAsStream(fileName);
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
