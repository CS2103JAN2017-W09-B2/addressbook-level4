package seedu.typed.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    public static void createIfMissing(File file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent
     * directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir
     *            the directory to be created; assumed not null
     * @throws IOException
     *             if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes given string to a file. Will create the file if it does not exist
     * yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    //@@author A0139392X
    /*
     * If src file exists, writes from src to dest.
     * Will create src file if it does not exist yet.
     * @throws IOException
     *      if src file does not exist
     *
     */
    public static void transferToFile(File src, File dest) throws IOException {
        writeToFile(dest, readFromFile(src));
    }
    //@@author

    /**
     * Converts a string to a platform-specific file path
     *
     * @param pathWithForwardSlash
     *            A String representing a file path but using '/' as the
     *            separator
     * @return {@code pathWithForwardSlash} but '/' replaced with
     *         {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        assert pathWithForwardSlash != null;
        assert pathWithForwardSlash.contains("/");
        return pathWithForwardSlash.replace("/", File.separator);
    }

    //@@author A0139392X
    /*
     * Returns true if the filename is a acceptable.
     *
     *  @param String fileName
     *             A string that will be tested to see if the naming is valid.
     *  @return true if name is valid, false if name otherwise.
     */
    public static boolean isValidName(String fileName) {
        File f = new File(fileName);
        try {
            f.createNewFile();
            boolean isValid = f.isFile() && !f.isHidden();
            f.getCanonicalFile();
            f.delete();
            return isValid;
        } catch (IOException e) {
            return false;
        }
    }
    //@@author

    //@@author A0139392X
    /*
     * Returns the full path of the directory
     */
    public static String getFullDirectoryPath() throws IOException {
        File file = File.createTempFile("hello", ".tmp");

        String absolutePath = file.getAbsolutePath();

        file.delete();

        String directoryPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

        return directoryPath;
    }
    //@@author

    //@@author A0139392X
    /*
     * Given a directory, obtain the filename
     */
    public static String getNameFromDirectory(File directory) {
        String absolutePath = directory.getAbsolutePath();
        String fileName = absolutePath.substring(absolutePath.lastIndexOf(File.separator));

        return fileName;
    }

    public static boolean isValidLocation(String tempLocation) {
        File f = new File(tempLocation);

        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /*
     * Returns the correct extension no matter the input.
     * @param  String fileName
     *             fileName input by the user as the new name
     * @return String
     *             with the proper extension.
     */
    public static String createProperExtension(String str) {
        if (str.contains(".")) {
            String beforeDot = str.substring(0, str.lastIndexOf("."));
            String afterDot = str.substring(str.lastIndexOf("."));
            return appendXmlIfMissing(beforeDot, afterDot);
        } else {
            return (str + ".xml");
        }
    }

    /*
     * Appends .xml to the back if missing
     */
    private static String appendXmlIfMissing(String beforeDot, String afterDot) {
        if (afterDot.equalsIgnoreCase("xml")) {
            return beforeDot;
        } else {
            return (beforeDot + ".xml");
        }
    }

    /*
     * Returns true if the file is of an xml extension
     */
    public static boolean isXML(String filePath) {
        if (filePath.contains(".")) {
            String afterDot = filePath.substring(filePath.lastIndexOf("."));
            return afterDot.equalsIgnoreCase(".xml");
        } else {
            return false;
        }
    }

    /*
     * Copies the file from toCopyFrom to fileToCreate.
     *
     * @param File toCopyFrom
     *          A source file containing the contents that will be copied over.
     *        File fileToCreate
     *          A file that will hold the contents that are being copied over.
     */
    public static void writingFile(File toCopyFrom, File fileToCreate) throws FileNotFoundException, IOException {
        fileToCreate.createNewFile();

        FileInputStream fis = new FileInputStream(toCopyFrom);
        FileOutputStream fos = new FileOutputStream(fileToCreate);

        int length;

        byte[] buffer = new byte[1024];

        while ((length = fis.read(buffer)) != (-1)) {
            fos.write(buffer, 0, length);
        }

        fis.close();
        fos.close();
    }
    //@@author
}
