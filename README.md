## Hogan Compile Maven Plugin

hogancompile-maven-plugin is a maven plugin for compiling 
[Hogan.js](http://twitter.github.com/hogan.js) templates while building project.

## Usage

This plugin has not yet been published in maven central repository.

In order to use it, proceed as following :
- Clone this respository
- mvn clean install
- Use the plugin in your project as following :
```
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>net.champemont.jean.hogancompile</groupId>
                <artifactId>hogancompile-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>hulk-hogan</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## Configuration

Here is the full configuration options, with default values :

```
<configuration>
    <!-- One or more pattern used to match templates files name and location -->
    <includes>
        <param>**/*.mustaches</param>
    </includes>
    <!-- One or more pattern to exclude files included by the previous option -->
    <excludes></excludes>
    <!-- Encoding used to decode template files. Uses ${project.build.sourceEncoding} if available -->
    <sourceEncoding>UTF-8</sourceEncoding>
    <!-- Encoding used to encode output files. Uses ${project.build.sourceEncoding} if available -->
    <outputEncoding>UTF-8</outputEncoding>
</configuration>
```
## Issues

Have a bug? A pull request? Please create an issue here on GitHub.

## License

Copyright 2014 Jean Champémont
Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
