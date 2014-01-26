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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class HoganTemplateCompileHelper {
	
	private static String HOGANJS_FILENAME = "hogan-2.0.0.js";
	
	private static String COMPILE_CMD = "var result = Hogan.compile(template, {asString: true});";
	
	private static String TEMPLATE_HEADER = "var templates = templates || {};";
	
	public static String compileHoganTemplate(String template, String templateName) throws IOException {
		String result = null;
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();
		Reader hoganJS;
		hoganJS = new InputStreamReader(HoganTemplateCompileHelper.class.getClassLoader().getResourceAsStream(HOGANJS_FILENAME));
		cx.evaluateReader(scope, hoganJS, HOGANJS_FILENAME, 1, null);
		scope.put("template", scope, template);
		cx.evaluateString(scope, COMPILE_CMD, "COMPILE_CMD", 1, null);
		
		result  = getTemplateHeader() + "\n";
		result += "templates." + templateName + " = new Hogan.Template(" + (String) scope.get("result", scope) + ");";
		return result;
	}
	
	public static String getTemplateHeader() {
		return TEMPLATE_HEADER;
	}
}
