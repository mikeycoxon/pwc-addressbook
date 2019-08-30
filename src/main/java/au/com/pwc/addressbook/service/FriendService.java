package au.com.pwc.addressbook.service;

import au.com.pwc.addressbook.BookException;
import au.com.pwc.addressbook.model.Friend;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public interface FriendService {

    /**
     * Presents the names of all books currently saved.
     * @return a set of books in alphabetical order.
     */
    TreeSet<String> books();

    /**
     * Display the list of {@link Friend}s that are unique to each address book (the union of all
     * the relative complements).
     * @return the {@link Friend}s that are unique to each book.
     */
    TreeSet<Friend> friendsUniqueToEachBook();

    /**
     * Displays all {@link Friend}s listed in a given book.
     * @param book the name of the book to search
     * @return all {@link Friend} instances in name-alphabetical order.
     */
    TreeSet<Friend> friends(String book);

    /**
     * Displays the {@link Friend}
     * @param name the name of the {@link Friend}.
     * @param book the name of the book to search.
     * @return the {@link Friend} in detail.
     */
    Friend friend(String name, String book);

    /**
     * Creates a new empty book.
     * @param book the name of the book to be created
     * @return true if the operation was successful, otherwise false.
     */
    boolean add(String book);

    /**
     * Creates a new {@link Friend} and adds them to the specified book
     * @param friend the {@link Friend} to be added.
     * @param book the book the {@link Friend} will be added to.
     * @return true if the operation was successful, otherwise false.
     */
    boolean add(Friend friend, String book);

    TreeSet<Friend> getFriendsFromBook(Path b);

    class Default implements FriendService {

        private static final Type FRIEND_TYPE = new TypeToken<Set<Friend>>() {}.getType();

        private FilesService filesService;
        private Path booksPath;

        public Default(FilesService filesService) {
            this.filesService = filesService;

            booksPath = Paths.get(System.getProperty("user.dir") + "/book");

            filesService.createMissingDirectory(booksPath);
        }

        @Override
        public TreeSet<String> books() {
            TreeSet<String> books = filesService.listFiles(booksPath)
                        .map(Path::toString)
                        .collect(Collectors.toCollection(TreeSet::new));
            return books;
        }

        @Override
        public TreeSet<Friend> friendsUniqueToEachBook() {
            TreeSet<Friend> unique = new TreeSet<>();

            filesService.listFiles(booksPath).forEach(b -> {
                    TreeSet<Friend> currentBook = getFriendsFromBook(b);

                    currentBook.forEach(f -> {
                        if (unique.contains(f)) {
                            unique.remove(f);
                        } else {
                            unique.add(f);
                        }
                    });
                });


            return unique;
        }


        @Override
        public TreeSet<Friend> friends(String book) {

            Optional<Path> bookPath = filesService.listFiles(booksPath)
                    .filter(b -> !b.endsWith(book))
                    .findFirst();

            return getFriendsFromBook(bookPath.get());
        }

        @Override
        public Friend friend(String name, String book) {

            TreeSet<Friend> friends = friends(book);

            return friends
                    .stream()
                    .filter(f -> f.getName().equals(name))
                    .findFirst().orElse(null);
        }

        @Override
        public boolean add(String book) {
            return false;
        }

        @Override
        public boolean add(Friend friend, String book) {
            return false;
        }

        @Override
        public TreeSet<Friend> getFriendsFromBook(Path b) {
            Gson gson = new Gson();

            JsonReader reader = new JsonReader(filesService.getReader(b));

            Set<Friend> data = gson.fromJson(reader, FRIEND_TYPE);

            return new TreeSet<>(data);
        }

    }
}
