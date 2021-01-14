import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class XmlConverterTests {

    String[] testFileNames = {"testFiles\\12345678901.csv", "testFiles\\98765432109.csv"};
    String[] outputFileNames;

    @BeforeClass
    public static void produceOutputFiles() throws IOException {

        //Deletes directory if it already exists
        if (new File("outputFiles").exists()) {
            cleanFolder();
        }
        String xmlFile = "testFiles\\testfile.xml";
        XmlConverter converter = new XmlConverter(xmlFile);
        converter.xmlConvert();
    }

    @Before
    public void getFileNames() {
        File dir = new File("outputFiles");
        outputFileNames = dir.list();
    }

    @Test
    public void testOutputExtension() {
        for (String fileName : outputFileNames) {
            assertTrue(fileName.endsWith(".csv"));
        }
    }

    //Test to check line by line if the output files are identical to the given outputs
    @Test
    public void testOutputData() throws IOException {
        for (String outputFileName : outputFileNames) {
            File f1 = new File("outputFiles\\" + outputFileName);
            for (String testFileName : testFileNames) {
                File f2 = new File(testFileName);
                if (f1.getName().equals(f2.getName())) {
                    Scanner file1 = new Scanner(f1);
                    Scanner file2 = new Scanner(f2);
                    while (file1.hasNextLine() && file2.hasNextLine()) {
                        assertEquals(file1.nextLine(), file2.nextLine());
                    }
                    file1.close();
                    file2.close();
                }
            }
        }
    }

    //Deletes the directory after testing
    @AfterClass
    public static void cleanFolder() throws IOException {
        File dir = new File("outputFiles");
        FileUtils.cleanDirectory(dir);
        FileUtils.deleteDirectory(dir);
    }
}
