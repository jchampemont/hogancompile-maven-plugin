package net.champemont.jean.hogancompile.helper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class HoganTemplateCompileHelper {
	
	private static String HOGANJS_FILENAME = "hogan-2.0.0.js";
	
	private static String COMPILE_CMD = "var result = Hogan.compile(template, {asString: true});";
	
	public static String compileHoganTemplate(String template, String templateName) throws IOException {
		String result = null;
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();
		Reader hoganJS;
		try {
			hoganJS = new FileReader(new File(HoganTemplateCompileHelper.class.getClassLoader().getResource(HOGANJS_FILENAME).toURI()));
		} catch (URISyntaxException e) {
			throw new IOException(HOGANJS_FILENAME + " was not found on classpath. Aborting");
		}
		cx.evaluateReader(scope, hoganJS, HOGANJS_FILENAME, 1, null);
		scope.put("template", scope, template);
		cx.evaluateString(scope, COMPILE_CMD, "COMPILE_CMD", 1, null);
		
		result = "templates." + templateName + " = new Hogan.Template(" + (String) scope.get("result", scope) + ");";
		return result;
	}
	
	public static String getTemplateHeader() {
		return "var templates = {};";
	}
}
