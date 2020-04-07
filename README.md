Add plugin
-----
```xml
<plugin>
    <groupId>org.sterzhen</groupId>
    <artifactId>jpackage-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <phase>verify</phase>
            <goals>
                <goal>createpackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Run: 
`mvn clean install `

Requirit `jpackage.exe` from `java 14`

For Windows you need to install [WiX Toolset](https://wixtoolset.org/)
