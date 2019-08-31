package au.com.pwc.addressbook.model.fixtures;

import au.com.pwc.addressbook.model.Friend;
import com.google.gson.Gson;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class FriendsFixture {

    public static TreeSet<Friend> book1() {
        TreeSet<Friend> friends = new TreeSet<>();

        friends.add(Friend.of("Bob", "0410100100"));
        friends.add(Friend.of("Mary", "0410100200"));
        friends.add(Friend.of("Jane", "0410100300"));

        return friends;
    }

    public static TreeSet<Friend> book2() {
        TreeSet<Friend> friends = new TreeSet<>();

        friends.add(Friend.of("Mary", "0410100200"));
        friends.add(Friend.of("John", "0410100400"));
        friends.add(Friend.of("Jane", "0410100300"));

        return friends;
    }

    public static TreeSet<Friend> emptyBook() {
        TreeSet<Friend> friends = new TreeSet<>();

        return friends;
    }

    public static String book1AsString() {
        TreeSet<Friend> friends = book1();

        return new Gson().toJson(friends);
    }

    public static String emptyBookAsString() {
        TreeSet<Friend> friends = emptyBook();

        return new Gson().toJson(friends);
    }

    public static Stream<Path> allBooks() {
        Set<Path> books = new HashSet<>();

        books.add(Paths.get("/Users/me/dev/ws-other/pwc-address-book/books/book1.json"));
        books.add(Paths.get("/Users/me/dev/ws-other/pwc-address-book/books/book2.json"));
        books.add(Paths.get("/Users/me/dev/ws-other/pwc-address-book/books/emptyBook.json"));

        return books.stream();

    }

    public static Stream<Path> noBooks() {
        Set<Path> books = new HashSet<>();

        return books.stream();
    }

}
