# Monopoly Refactoring Exercise 

Code base is a fork of https://github.com/stephenoken/Monopoly which features a slightly different version of Player.java than was shared via email.

Goal of the exercise is refactoring [Player.java](src/main/java/edu/ncsu.monopoly/Player.java) based on the following statements: 

*The exercise is constrained to refactoring just this object. Player.java is the only aspect of the enterprise that his team would be responsible for. Hence, the public interface of Player and the interfaces of objects external to Player must be preserved.*

To accomplish this the I did the following: 

 1. Maven-ize the project 
    1. Move *Test classes to src/test/java subdirectory structure, kept same package names. 
    1. pom.xml file that generates a jar with all dependencies - allows for `java -jar Monopoly-1.0-SNAPSHOT-jar-with-dependencies.jar` execution in target directory. 
 1. Refactorings 
    1. http://refactoring.com/catalog/replaceNestedConditionalWithGuardClauses.html
    1. JavaDocs at class, method and variable level. 
    1. Generics to various collections classes (e.g. `new HashTable()` vs. `new HashTable<String, Integer>()`). 
    1. Added string checks on those public methods accepting such parameters to ensure not empty or null. 
    1. Made public methods package when only used by unit tests.
    1. Declared method parameters final to let clients known parameters are not reassigned by the method. 
    1. Inline singly called private methods. 
    1. Reorganize variables and methods by access: public, package, private
    1. Using [Apache Commons](https://commons.apache.org/) implement equals and hashCode methods via reflection. Note, toString excluded because client has a dependency.      
    1. Enum to replace instanceof conditionals for buying and selling OwnedCell instances. 
 1. *null checking*: did not do. This is a bit of a holy war with the inactive JSR-305 and Java 8's `Optional` class. Read world I would incoprorate something after sussing through with a team, but didn't want to get too caught in the weeds here.    
 1. Execute and resolve (real world would probably do this via a parent pom.xml) 
    1. [JaCoCo](http://www.eclemma.org/jacoco/trunk/index.html) unit test code coverage reporting, ensure 80%+ (yes could be higher) - `mvn checkstyle:checkstyle` - ignored following checks for convenience sake:
        1. Line length. 
        1. Design for extension. 
        1. Field shadowing - i.e. set{variable} hides {variable}
        1. Yes I know these can be suppressed by configuration or possibly annotation, but got tired of Googling. 
    1. [Checkstyle](https://maven.apache.org/plugins/maven-checkstyle-plugin/) enforcement - `mvn clean test` & `mvn jacoco:report` - review target/site/jacoco/index.html
    1. [PMD](https://pmd.github.io/) 
 1. Not incorporated but considered:
    1. Sonar or Fortify for vulnerability scanning 
    1. OWASP dependency checker  
 