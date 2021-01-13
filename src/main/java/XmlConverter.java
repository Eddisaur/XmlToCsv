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

    private void xmlReader() {
        String csvIntervalData = "";
        try {
            File file = new File(this.filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("CSVIntervalData");
            Element eElement = (Element) list.item(0);
            csvIntervalData = eElement.getTextContent();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        this.data = csvIntervalData;
    }

    private void writeCSV() {
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

        for (CsvFile file : newFiles) {
            file.addHeaderAndTrailer(header, trailer);
        }
    }

    private void createCsvFiles() {
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
    }

    public void xmlConvert() {
        xmlReader();
        writeCSV();
        createCsvFiles();
    }
}
