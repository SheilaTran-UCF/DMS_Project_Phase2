Employee Tracker Project - Phase 2
==================================

Author: Minh Tran  
Course: CEN 3024C - Data Management Systems  
Date: June 2025

Description:
------------
This project is a console-based Employee Tracker application built in Java.  
It supports adding, removing, updating employees, saving/loading employee data from a file,  
and generating a tenure report. The project follows object-oriented principles.

How to Run:
------------
1. Ensure you have Java 21 or later installed.
2. Run the executable JAR from the command line:

   java -jar DMS_Project_Phase2-1.0-SNAPSHOT.jar

3. Follow the menu prompts to use the application.

Project Structure:
------------------
- src/main/java/EmployeeTracker: source code files
- target/: contains compiled classes and packaged JAR files
- tests/: JUnit test classes for input validation and service logic

Additional Notes:
-----------------
- The JAR is a “fat” (shaded) JAR that bundles all dependencies.
- Unit tests have been run and passed successfully.
- For any questions, contact Minh Tran.

- feat: Complete Phase 2 - Employee Tracker with full CRUD, file IO, and tenure report

- Implemented EmployeeService with add, remove, update, and load/save from file
- Added InputValidator utility for robust user input handling
- Created MainApp with interactive console menu for user operations
- Configured Maven Shade Plugin for executable fat JAR
- Added comprehensive JUnit tests covering service and input validation
- All tests passing, application runs successfully via JAR


Thank you!
