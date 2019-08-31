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
     * This is an initialising method that checks to see if the given {@link Path} exists as a directory
     * and creates it if it doesn't.
     * @param path the {@link Path} to test and create, if necessary.
     * @return true if the directory exists, or that it was successfully created.
     * @throws BookException (unchecked) if there are file operating system issues.
     */
    boolean createMissingDirectory(Path path);

    /**
     * Returns a Reader (basically a FileReader) from the file at the given {@link Path}
     * @param filePath The {@link Path}.
     * @return the {@link Reader}.
     */
    Reader getReader(Path filePath);

    /**
     * Checks for the existance of a file corresponding to the supplied path.
     * @param filePath the {@link Path} of the file to check.
     * @return true if the file exists, otherwise false.
     */
    boolean exists(Path filePath);

    /**
     * Presents the list of files at the given {@link Path} as a {@link Stream} for consumption.
     * @param dirPath The {@link Path}
     * @return the files as a {@link Stream} of {@link Path}.
     */
    Stream<Path> listFiles(Path dirPath);

    /**
     * Creates a file at the given {@link Path}.
     * @param filePath The {@link Path}
     * @return the {@link Path} of the file just made.
     */
    Path createFile(Path filePath);

    /**
     * Writes the supplied content to the file at the given {@link Path}, replacing the content that
     * may or may not be there.
     * @param filePath The {@link Path} specifying the file to write to
     * @param content the content to be written.
     * @return the {@link Path} of the file just written to.
     */
    Path overwriteFile(Path filePath, byte[] content);

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
        public boolean exists(Path filePath) {
            return Files.exists(filePath);
        }


        @Override
        public Stream<Path> listFiles(Path dirPath) {
            try {
                return Files.list(dirPath);
            } catch (IOException e) {
                throw exceptMe("Could not list files", e);
            }
        }

        @Override
        public Path createFile(Path filePath) {
            try {
                return Files.createFile(filePath);
            } catch (IOException e) {
                throw exceptMe("Could not create file", e);
            }
        }

        @Override
        public Path overwriteFile(Path filePath, byte[] content) {
            try {
                return Files.write(filePath, content);
            } catch (IOException e) {
                throw exceptMe("Could not write to file", e);
            }
        }

        private BookException exceptMe(String message, Exception e) {
            return new BookException(message, e);
        }

    }
}
