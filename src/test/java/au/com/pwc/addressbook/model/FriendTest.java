package au.com.pwc.addressbook.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class FriendTest {

    @Test
    public void factory_makes_friend_with_valid_name_and_number() {
        Friend friend = Friend.of("Bob", "0400000000");

        assertThat(friend, is(notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void factory_fails_friend_with_empty_name() {
        Friend.of("", "0400000000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void factory_fails_friend_with_null_name() {
        Friend.of(null, "0400000000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void factory_fails_friend_with_empty_number() {
        Friend.of("bob", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void factory_fails_friend_with_null_number() {
        Friend.of("bob", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void factory_fails_friend_with_bad_number() {
        Friend.of("bob", "friday");
    }

}
