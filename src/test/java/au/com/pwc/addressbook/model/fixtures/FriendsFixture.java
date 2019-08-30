package au.com.pwc.addressbook.model.fixtures;

import au.com.pwc.addressbook.model.Friend;
import com.google.gson.Gson;

import java.util.TreeSet;

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

    public static String book1AsString() {
        TreeSet<Friend> friends = book1();

        return new Gson().toJson(friends);
    }
}
