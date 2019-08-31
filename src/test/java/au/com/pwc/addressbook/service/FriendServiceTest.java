package au.com.pwc.addressbook.service;

import au.com.pwc.addressbook.model.Friend;
import au.com.pwc.addressbook.model.fixtures.FriendsFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class FriendServiceTest {

    @InjectMocks
    FriendService.Default service;

    @Mock
    private FilesService filesService;

    @Test
    public void getFriendsFromBook_returns_friends_when_book_not_empty() {

        // Given
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.book1AsString()));

        // When
        TreeSet<Friend> actual = service.getFriendsFromBook(Paths.get("book1"));

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(3));
        assertThat(actual.first(), is(Friend.of("Bob", "0290824577")));
        assertThat(actual.last(), is(Friend.of("Mary", "0290824566")));
    }

    @Test
    public void getFriendsFromBook_returns_no_friends_when_book_is_empty() {

        // Given
        when(filesService.getReader(any(Path.class))).thenReturn(new StringReader(FriendsFixture.emptyBookAsString()));

        // When
        TreeSet<Friend> actual = service.getFriendsFromBook(Paths.get("emptyBook"));

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(0));
    }

    @Test
    public void books_returns_all_books_when_books_exist() {
        // Given
        // eq(Paths.get("/Users/me/dev/ws-other/pwc-address-book/books"))
        when(filesService.listFiles(any(Path.class)))
                .thenReturn(FriendsFixture.allBooks());

        // When
        TreeSet<String> actual = service.books();

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(3));
    }

    @Test
    public void books_returns_no_books_when_no_books_exist() {
        // Given
        when(filesService.listFiles(any(Path.class)))
                .thenReturn(FriendsFixture.noBooks());

        // When
        TreeSet<String> actual = service.books();

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(0));
    }

    @Test
    public void friends_returns_no_friends_when_book_does_not_exist() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(false);

        // When
        TreeSet<Friend> actual = service.friends("MyBook");

        // Then
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void friends_returns_no_friends_when_book_is_empty() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(true);
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.emptyBookAsString()));

        // When
        TreeSet<Friend> actual = service.friends("MyBook");

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(0));
    }

    @Test
    public void friends_returns_1_friend_when_book_has_1_friend() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(true);
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.singleFriendBookAsString()));

        // When
        TreeSet<Friend> actual = service.friends("MyBook");

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(1));
        assertThat(actual.first(), is(is(Friend.of("Mary", "0290824566"))));
    }

    @Test
    public void friends_returns_3_friends_when_book_has_3_friends() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(true);
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.book1AsString()));

        // When
        TreeSet<Friend> actual = service.friends("MyBook");

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(3));
        assertThat(actual.first(), is(Friend.of("Bob", "0290824577")));
        assertThat(actual.last(), is(Friend.of("Mary", "0290824566")));
    }

    @Test
    public void friend_returns_no_friend_when_book_does_not_exist() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(false);

        // When
        Friend actual = service.friend("Mary", "MyBook");

        // Then
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void friend_returns_no_friend_when_book_is_empty() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(true);
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.emptyBookAsString()));

        // When
        Friend actual = service.friend("Mary", "MyBook");

        // Then
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void friend_returns_no_friend_when_book_does_not_have_friend() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(true);
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.singleFriendBookAsString()));

        // When
        Friend actual = service.friend("Bob", "MyBook");

        // Then
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void friend_returns_friend_when_book_has_friend() {

        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(true);
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.singleFriendBookAsString()));

        // When
        Friend actual = service.friend("Mary", "MyBook");

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getName(), is("Mary"));
    }

    @Test
    public void add_adds_a_new_book() {
        // Given
        when(filesService.createFile(any(Path.class)))
                .thenReturn(Paths.get("/a/path/to/file.json"));

        // When
        boolean actual = service.add("file");

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void add_adds_a_friend_to_an_existing_book() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(true);
        when(filesService.getReader(any(Path.class)))
                .thenReturn(new StringReader(FriendsFixture.singleFriendBookAsString()));
        when(filesService.overwriteFile(any(Path.class), any(byte[].class)))
                .thenReturn(Paths.get("/a/path/to/file.json"));

        // When
        boolean actual = service.add(Friend.of("Bob", "0290824566"),"file");

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void add_does_not_add_a_friend_to_a_book_that_does_not_exist() {
        // Given
        when(filesService.exists(any(Path.class)))
                .thenReturn(false);

        // When
        boolean actual = service.add(Friend.of("Bob", "0290824566"),"file");

        // Then
        assertThat(actual, is(false));
    }

}
