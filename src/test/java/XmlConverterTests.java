import org.junit.Before;
import org.junit.Test;

public class XmlConverterTests {

    String[] sampleFileNames = {"testFiles\\12345678901.csv", "testFiles\\98765432109.csv"};
    XmlConverter converter;

    @Before
    public void produceOutputFiles() {
        converter = new XmlConverter("testFiles\\testfile.xml");
        converter.xmlConvert();
    }

    @Test
    public void testSampleInputsAndOutputs() {
    }
}
