# Monopoly Refactoring Exercise 

Code base is a fork of https://github.com/stephenoken/Monopoly which features a slightly different version of Player.java than was shared via email.

Goal of the exercise is refactoring [Player.java](src/main/java/edu/ncsu.monopoly/Player.java) based on the following statements. 

*The exercise is constrained to refactoring just this object. Player.java is the only aspect of the enterprise that his team would be responsible for. Hence, the public interface of Player and the interfaces of objects external to Player must be preserved.*

To accomplish this the I did the following: 

 1. Maven-ize the project including migration of all *Test classes to a separate src/test/java subdirectory structure.
 1. Refactorings 
    1. http://refactoring.com/catalog/replaceNestedConditionalWithGuardClauses.html
    1. Made public methods package when only used by unit tests.
    1. Declared method parameters final to let clients they are not reassigned by the method. 
    1. Inline privates methods with a single caller. 
    1. Reorganize variables and methods by access public, package, private
    1. Using [Apache Commons](https://commons.apache.org/) implement toString, equals and hashCode methods via reflection.     
 1. I thought about null checking. This is a bit of a holy war with the inactive JSR-305 and Java 8's `Optional` class. Read world I would incoprorate something after sussing through with a team, but didn't want to get too caught in the weeds here. 
 1. Add data validation and null checking to method parameters.
 1. Return immutable or deep copies of collections where appropriate and document accordingly. 
 1. Incorporate (real world would probably do this via a parent pom.xml) 
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
 