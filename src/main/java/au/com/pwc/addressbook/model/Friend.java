package au.com.pwc.addressbook.model;

import com.google.gson.Gson;

import java.util.Objects;

public class Friend implements Comparable<Friend> {

    private String name;
    private String number;

    private Friend() {
    }

    public static Friend of(String name, String number) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Your friend must have a name");
        }

        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Your friend must have a number");
        }

        if (number.matches("\\d*")) {
            Friend friend = new Friend();
            friend.name = name;
            friend.number = number;
            return friend;
        } else {
            throw new IllegalArgumentException("Your friend's number must be digits");
        }
    }

    public int compareTo(Friend other)
    {
        return this.name.compareTo(other.name);
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friend)) return false;
        Friend friend = (Friend) o;
        return Objects.equals(getName(), friend.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
