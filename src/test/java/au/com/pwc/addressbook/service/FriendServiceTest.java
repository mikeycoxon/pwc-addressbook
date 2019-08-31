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
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
        when(filesService.getReader(any(Path.class))).thenReturn(new StringReader(FriendsFixture.book1AsString()));

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
        // eq(Paths.get("/Users/me/dev/ws-other/pwc-address-book/books"))

        when(filesService.listFiles(any(Path.class)))
                .thenReturn(FriendsFixture.noBooks());

        // When
        TreeSet<String> actual = service.books();

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(0));
    }

}
