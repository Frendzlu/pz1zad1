import org.example.HMUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HMUtilsTest {

    @Test
    public void testListFilesForFolder() throws IOException {
        // Create a temporary directory for testing
        File tempDir = Files.createTempDirectory("testFolder").toFile();

        // Create files and directories in this folder
        File tempFile1 = new File(tempDir, "file1.txt");
        File tempFile2 = new File(tempDir, "file2.txt");
        File tempDir1 = new File(tempDir, "subDir");

        tempFile1.createNewFile();
        tempFile2.createNewFile();
        tempDir1.mkdir();

        // List files in the folder using HMUtils
        ArrayList<File> files = HMUtils.listFilesForFolder(tempDir);

        // Verify that the files were listed but the directory was excluded
        assertTrue(files.contains(tempFile1));
        assertTrue(files.contains(tempFile2));
        assertFalse(files.contains(tempDir1)); // directory should not be included

        // Clean up
        tempFile1.delete();
        tempFile2.delete();
        tempDir1.delete();
        tempDir.delete();
    }
}
