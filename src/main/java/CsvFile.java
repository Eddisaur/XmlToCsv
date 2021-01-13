public class CsvFile {
    private final String csvName;
    private String data;

    public CsvFile(String filename, String data) {
        this.csvName = filename;
        this.data = data;
    }

    public String getCsvName() {
        return csvName;
    }

    public String getData() {
        return data;
    }

    public void addHeaderAndTrailer(String header, String trailer) {
        this.data = header + this.data + trailer;
    }
}
