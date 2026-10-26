# Estate Agent Commission Application

A Java Swing desktop application for calculating estate agent commission.

## Requirements
- Java JDK 11 or later
- JUnit 5 to run the unit tests

## Project structure
- `src/estateagent/IEstateAgent.java` — interface
- `src/estateagent/EstateAgent.java` — data, validation, and commission calculation
- `src/estateagent/EstateAgentGUI.java` — Swing user interface
- `test/estateagent/EstateAgentTest.java` — JUnit 5 tests

## Run from the command line
From the `EstateAgentApplication` folder:

```bash
javac -d out src/estateagent/*.java
java -cp out estateagent.EstateAgentGUI
```

The `report.txt` file is saved to the application's current working directory after choosing **Tools > Save Report**.

## Run in NetBeans
1. Create/open a Java project.
2. Copy the files from `src/estateagent` into the project's Source Packages.
3. Add JUnit 5 to the test dependencies.
4. Copy `EstateAgentTest.java` into the matching test package.
5. Run `EstateAgentGUI.java`, or right-click the project and select Test.

## Example
Property price: `800000`
Commission percentage: `10`
Calculated commission: `R 80,000.00`

Commission = property price × commission percentage / 100.
