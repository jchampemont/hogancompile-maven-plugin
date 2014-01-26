package net.champemont.jean.hogancompile.maven.plugins;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.champemont.jean.hogancompile.helper.HoganTemplateCompileHelper;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * Goal which compiles Hogan.js templates.
 *
 * @goal hulk-hogan
 * 
 * @phase generate-sources
 */
public class HoganTemplateCompilerMojo extends AbstractMojo {
    /**
     * Where to scan for templates
     *
     * @parameter default-value="${project}"
     */
    private MavenProject project;
    
    /**
     * Defines files in the source directories to include (all .mustaches files by default).
     * @parameter
     */
    private String[] includes = {"**/*.mustaches"};
    
    /**
     * Defines which of the included files in the source directories to exclude (none by default).
     * @parameter
     */
    private String[] excludes;
    
    /**
     * Defines source files encoding
     * @parameter expression="${project.build.sourceEncoding}" default-value="UTF-8"
     */
    private String sourceEncoding;
    
    /**
    * Defines output files encoding
    * @parameter expression="${project.build.sourceEncoding}" default-value="UTF-8"
    */
    private String outputEncoding;
    
    public void execute() throws MojoExecutionException {
    	Log log = getLog();
        String root = project.getBasedir().getPath();
    	String[] matchingFiles = scan(new File(root));
    	for(String matchingFile : matchingFiles) {
    		log.info("compiling '" + matchingFile + "'...");
    		Path sourceFile = Paths.get(matchingFile);
    		try {
    			//Reading template file
    			byte[] fileBytes = Files.readAllBytes(sourceFile);
    			String template = Charset.forName(sourceEncoding).decode(ByteBuffer.wrap(fileBytes)).toString();
    			//Creating template name
    			int templateNameExtensionIndex = sourceFile.getFileName().toString().lastIndexOf('.');
    			String templateName = templateNameExtensionIndex > 0 ? 
    					  sourceFile.getFileName().toString().substring(0, templateNameExtensionIndex) 
    					: sourceFile.getFileName().toString();
    			//Compiling template
    			String result = HoganTemplateCompileHelper.compileHoganTemplate(template, templateName);
    			//Writing output file
    			fileBytes = Charset.forName(outputEncoding).encode(result).array();
    			Path outputFilePath = sourceFile.getParent().resolve(templateName + ".js");
    			log.info("writing '" + outputFilePath.toString() + "'");
    			Files.write(outputFilePath, fileBytes, StandardOpenOption.CREATE);
    		} catch(IOException e) {
    			throw new MojoExecutionException("Error while compiling Hogan Template", e);
    		}
    	}
    }
    
	private String[] scan(File root) throws MojoExecutionException {
		Log log = getLog();
		if (!root.exists()) {
			return new String[]{};
		}

		log.info("scanning source file directory '" + root + "'");

		final DirectoryScanner directoryScanner = new DirectoryScanner();
		directoryScanner.setIncludes(includes);
		directoryScanner.setExcludes(excludes);
		directoryScanner.setBasedir(root);
		directoryScanner.scan();
		return directoryScanner.getIncludedFiles();
	}
}
