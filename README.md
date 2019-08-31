# pwc-addressbook

## Solution
This project is a solution to the PWC coding challenge, presented under the `Codeing Challenge` heading.

### Driving Instructions
* clone this repo
* run `./gradlew jar`
* take the address-book-1.0.0-SNAPSHOT.jar from build/libs/
* run it in your terminal of choice with: `java -jar build/libs/address-book-1.0.0-SNAPSHOT.jar`
* you may have to `chmod 777` the jar if you get permission denied.

### Scope & Assumptions
* This is a command-line based application
* the data store is in-memory: there is no persistence, so if you exit, depending on how you have invoked this application, you lose what you have done.
* There are no addresses in the address books :)
* Last names are not supported
* No two entries in any address book can have the same name

### Ran Out of Time
As I reached the 8-hour mark, there were still some things I would like to have done:
1. Put unit tests around the FileService - I needed to mock out the file system, which is something that would have needed some test infrastructure.
1. Implement a FormattingService that would have made the Sys.outs a bit nicer.

### Outstanding Bugs
1. As commented under the App.java file, the user has to hit a RETURN before using any of the commands in the program (happy to get any Stack Overflow links that I haven't seen yet)
This only seems to happen if you are running it in an IDE, on the commandline is seems to be OK.

## Coding Challenge 

### Your task
You have been asked to develop an address book that allows a user to store (between successive runs of the program) the name and phone numbers of their friends, with the following functionality:

* To be able to display the list of friends and their corresponding phone numbers sorted by their name.
* Given another address book that may or may not contain the same friends, display the list of friends that are unique to each address book (the union of all the relative complements). For example given:

```text
Book1 = { "Bob", "Mary", "Jane" }
Book2 = { "Mary", "John", "Jane" }
```
The friends that are unique to each address book are: 

```text
Book1 \ Book2 = { "Bob", "John" }
```

### Instructions
Please provide relevant source code of your implementation and any documentation and additional assumptions that you feel are appropriate. Please use the following information to assist you with completing the challenge successfully:

* The intent of the challenge is to provide us with an opportunity to judge your problem solving, design and development skills. It is important to provide a solution that highlights your skills in these areas.
* Develop the system in Java or Node.js.
* Your solution should be high quality, well annotated, and include tests.
* The system should persist information; how you do this is completely up to you, and you
don't have to use a database.
* The simplest solution is often the best. You will be given at least 48 hours’ notice to
complete this task, but it is recommended that no more than 8 hours is spent on the
problem.
* If you can't complete the task in 8 hours reprioritise the functional requirements and
deliver a working version of what you have managed to solve.
* The application must run and be easy to build from source. It also must be easy to
execute for us to determine if the application meets the above requirements.
