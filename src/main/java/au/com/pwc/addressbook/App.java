package au.com.pwc.addressbook;

import au.com.pwc.addressbook.model.Friend;
import au.com.pwc.addressbook.service.FilesService;
import au.com.pwc.addressbook.service.FriendService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeSet;

public class App {



    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to the Address Book");

        System.out.println("Syntax: [list | see | add] [options]");
        System.out.println("---------------------------------------------------------");
        System.out.println("-f: friend's name");
        System.out.println("-b: book name");
        System.out.println("---------------------------------------------------------");
        System.out.println("    list                  list all books");
        System.out.println("    list -b book1         list all friends in 'book1'");
        System.out.println("    see -f Bob -b book1   see 'Bob' in 'book1'");
        System.out.println("    see -b book1          see all friends in 'book1'");
        System.out.println("    add -b book1          add a book 'book1'");
        System.out.println("    add -f Bob -b book1   add the friend 'Bob' to 'book1'");
        System.out.println("    exit                  exit the program");
        System.out.println("---------------------------------------------------------\r\n");
        System.out.println("\r\n");

        // The Scanner sees to fail to pick up any command when the application starts up
        // unless the user enters a return (\r\n). Before you start thinking that its the commonly encountered:
        //    the Scanner.nextXXX method does not read the newline character in your input created by hitting "Enter,"
        //    and so the call to Scanner.nextLine returns after reading that newline, rather than the text you
        //    intended to execute.
        // It's not that. As you can see I don't use any of the Scanner.nextXXX methods, other than Scanner.nextLine
        System.out.println("Please hit RETURN before using any of the above commands (yes, there's a bug).\r\n");

        FilesService fileService = new FilesService.Default();

        FriendService service = new FriendService.Default(fileService);

        Scanner in = new Scanner(System.in);

        String cmd = "list";     // just a default to get things going.

        while (!cmd.equals("exit")) {

            cmd = in.nextLine();

            if (cmd.isEmpty()) {
                // don't process simple returns, just consume them, and wait for something interesting.
                continue;
            }

            String err = Command.validate(cmd);

            if (err != null) {
                System.out.println(err);
                System.exit(1);
            }

            String[] switches = Command.subjects(cmd);

            System.out.println("performing command: " + Command.read(cmd));

            switch (Command.read(cmd)) {
                case LIST:
                    if (switches.length == 0) {
                        System.out.println(service.books());
                    } else {
                        TreeSet<Friend> book = service.getFriendsFromBook(Paths.get(switches[0]));
                        System.out.println(book);
                    }
                    break;

                case SEE:
                    if (switches.length == 1) {
                        System.out.println(service.friends(switches[0]));
                    } else {
                        System.out.println(service.friend(switches[0], switches[1]));
                    }
                    break;

                case ADD:
                    if (switches.length == 1) {
                        service.add(switches[0]);
                        System.out.println("added " + switches[0]);
                    } else {

                    }
                    break;

                case EXIT:
                    System.exit(0);
                    break;
            }

        }

    }
}
