import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class XmlConverter {
    String filename;
    String data;
    ArrayList<CsvFile> newFiles;

    public XmlConverter(String filename) {
        this.filename = filename;
        this.newFiles = new ArrayList<>();
    }

    //Reads the xml file and collects the data within the target tags
    public void xmlReader() {
        String csvIntervalData = "";
        try {
            //Use document builder to access XML attributes
            File file = new File(this.filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            //Get data inside the CSVIntervalData tag
            NodeList list = doc.getElementsByTagName("CSVIntervalData");
            Element eElement = (Element) list.item(0);
            csvIntervalData = eElement.getTextContent();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        this.data = csvIntervalData;
    }

    //Creates CSV file objects which contain the correct data collected from the XML
    public void writeCSV() {
        String header = "";
        String trailer = "";
        String csvName = "";
        Scanner scanner = new Scanner(this.data);
        StringBuilder newData = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("100")) {
                header = line + "\n";
            } else if (line.startsWith("900")) {
                trailer = line + "\n";
                if (!newData.toString().equals("\n")) {
                    newFiles.add(new CsvFile(csvName, newData.toString()));
                }
            } else if (line.startsWith("200")) {
                if (!newData.toString().equals("\n")) {
                    newFiles.add(new CsvFile(csvName, newData.toString()));
                }
                newData = new StringBuilder(line + "\n");
                csvName = line.split(",")[1];
            } else {
                newData.append(line).append("\n");
            }
        }
        scanner.close();

        //Adds the header and trailer for each CSV object
        for (CsvFile file : newFiles) {
            file.addHeaderAndTrailer(header, trailer);
        }
    }

    //Creates CSV files from the CSV objects
    public void createCsvFiles() {
        File dir = new File("outputFiles");
        if (dir.mkdir()) {
            for (CsvFile file : newFiles) {
                try {
                    Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream("outputFiles\\" + file.getCsvName() + ".csv")));
                    writer.write(file.getData());
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Directory, \"outputFiles\", cannot be created");
        }
    }

    public void xmlConvert() {
        xmlReader();
        writeCSV();
        createCsvFiles();
    }
}
