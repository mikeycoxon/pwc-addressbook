package au.com.pwc.addressbook.service;

import au.com.pwc.addressbook.BookException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * A Service, providing an interface for file manipulation, mostly to isolate the exception handling
 * and enabling mocking out the filesystem for testing.
 */
public interface FilesService {

    /**
     * Checks to see if the given {@link Path} exists as a directory and creates it if it doesn't.
     * @param path the path to test and create, if necessary.
     * @return true if the directory exists, or that it was successfully created.
     */
    boolean createMissingDirectory(Path path);

    Reader getReader(Path filePath);

    Stream<Path> listFiles(Path dirPath);

    class Default implements FilesService {

        @Override
        public boolean createMissingDirectory(Path path) {
            if (Files.notExists(path) || !Files.isDirectory(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw exceptMe("Could not initialise books directory", e);
                }
            }
            return true;
        }

        @Override
        public Reader getReader(Path filePath) {
            try {
                return Files.newBufferedReader(filePath);
            } catch (IOException e) {
                throw exceptMe("Could not get reader", e);
            }
        }

        @Override
        public Stream<Path> listFiles(Path dirPath) {
            try {
                return Files.list(dirPath);
            } catch (IOException e) {
                throw exceptMe("Could not list files", e);
            }
        }

        private BookException exceptMe(String message, Exception e) {
            return new BookException(message, e);
        }

    }
}
