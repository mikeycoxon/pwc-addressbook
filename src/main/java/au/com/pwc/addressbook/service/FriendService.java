package au.com.pwc.addressbook.service;

import au.com.pwc.addressbook.BookException;
import au.com.pwc.addressbook.model.Friend;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.stream.Collectors;

public interface FriendService {

    /**
     * Presents the names of all books currently saved.
     * <p>
     *     Note that names of the books correspond to the names of the files without the json extension.
     * </p>
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
     * Displays the {@link Friend} corresponding to the supplied name in the book of the supplied name
     * @param name the name of the {@link Friend}.
     * @param book the name of the book to search.
     * @return the {@link Friend} in detail, or null if the {@link Friend} does not exist..
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

    boolean reset();

    TreeSet<Friend> getFriendsFromBook(Path b);

    class Default implements FriendService {

        private static final Type FRIEND_TYPE = new TypeToken<Friend>() {}.getType();

        private FilesService filesService;
        private Path booksPath;

        public Default(FilesService filesService) {
            this.filesService = filesService;

            booksPath = Paths.get(System.getProperty("user.dir") + "/books");

            filesService.createMissingDirectory(booksPath);
        }

        @Override
        public TreeSet<String> books() {
            TreeSet<String> books = filesService.listFiles(booksPath)
                        .map(Path::toString)
                        .map(s -> s.substring(s.lastIndexOf("/") + 1, s.indexOf(".")))
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

            Path bookPath = pathFromBook(book);

            if (filesService.exists(bookPath)) {
                return getFriendsFromBook(bookPath);
            } else {
                return null;
            }
        }

        @Override
        public Friend friend(String name, String book) {

            TreeSet<Friend> friends = friends(book);

            if (friends != null) {
                return friends
                        .stream()
                        .filter(f -> f.getName().equals(name))
                        .findFirst().orElse(null);
            } else {
                return null;
            }
        }

        @Override
        public boolean add(String book) {

            Path path = filesService.createFile(pathFromBook(book));
            return (path != null);
        }

        @Override
        public boolean add(Friend friend, String book) {
            TreeSet<Friend> friends = friends(book);

            if (friends != null) {
                friends.add(friend);

                Gson gson = new Gson();
                Path path = filesService.overwriteFile(pathFromBook(book), gson.toJson(friends).getBytes());
                return (path != null);
            } else {
                return false;
            }
        }

        @Override
        public TreeSet<Friend> getFriendsFromBook(Path b) {
            Gson gson = new Gson();

            JsonReader reader = new JsonReader(filesService.getReader(b));

            TreeSet<Friend> data = new TreeSet<>();
            try {
                reader.beginArray();
                while (reader.hasNext()) {
                    Friend f = gson.fromJson(reader, FRIEND_TYPE);
                    data.add(f);
                }
                reader.endArray();
                reader.close();
            } catch (IOException e) {
                exceptMe("unable to read from file", e);
            }

            return data;
        }

        @Override
        public boolean reset() {
            filesService.listFiles(booksPath).forEach(path -> filesService.delete(path));
            return true;
        }

        private Path pathFromBook(String book) {
            return Paths.get(booksPath + "/" + book + ".json");
        }

        private BookException exceptMe(String message, Exception e) {
            return new BookException(message, e);
        }

    }
}
