package au.com.pwc.addressbook;

import au.com.pwc.addressbook.model.Friend;
import au.com.pwc.addressbook.service.FilesService;
import au.com.pwc.addressbook.service.FriendService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;

public class App {



    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to the Address Book");

        FilesService fileService = new FilesService.Default();

        FriendService service = new FriendService.Default(fileService);



        Scanner in = new Scanner(System.in);

        String cmd = in.nextLine();

        String err = Command.validate(cmd);

        if (err != null) {
            System.out.println(err);
            System.exit(1);
        }

        String[] switches = Command.subjects(cmd);

        switch (Command.read(cmd)) {
            case LIST:
                if (switches.length == 0) {
                    System.out.println(service.books());
                } else {
                    TreeSet<Friend> book = service.getFriendsFromBook(Paths.get(switches[2]));
                    System.out.println(book);
                }
                break;

            case SEE:
                if (switches.length == 1) {

                } else {

                }
                break;

            case ADD:
                if (switches.length == 1) {

                } else {

                }
                break;

        }


    }
}
