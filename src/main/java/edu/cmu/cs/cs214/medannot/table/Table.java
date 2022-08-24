package edu.cmu.cs.cs214.medannot.table;

import edu.cmu.cs.cs214.medannot.framework.core.Pair;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class Table {
    private final Map<Pair, Map<String, String>> parameters = new HashMap<>();
    Set<String> fields = new HashSet<>();

    /**
     * Constructor for {@link Table}.
     * @param filePath a {@link String} of the filePath to the csv file. Start the filePath from src folder.
     * @throws TableError.EmptyCSV if the CSV file is empty i.e. no rows
     * @throws TableError.InvalidFile if the file does not exist at the specified location, or is not a CSV
     * @throws TableError.MissingCoreFields if any of the core fields xCoordinate, yCoordinate, and Title is missing.
     */
    public Table(String filePath) throws TableError.EmptyCSV, TableError.InvalidFile, TableError.MissingCoreFields {
        if (filePath.length() < 4) throw new TableError.InvalidFile("Not a CSV file! " + filePath);
        if (!filePath.endsWith(".csv")) throw new TableError.InvalidFile("Not a CSV file! " + filePath);
        try {
            if (isContainBOM(Paths.get(filePath))) {
                removeBom(Paths.get(filePath));
            }
        } catch (IOException e) {
            throw new TableError.InvalidFile(filePath);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            CSVParser parser = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            fields.addAll(parser.getHeaderMap().keySet());
            if (fields.size() == 0) throw new TableError.EmptyCSV(filePath);
            Iterable<CSVRecord> records = parser.getRecords();
            if (!(fields.containsAll(Set.of("xCoordinate", "yCoordinate", "Title")))) {
                List<String> fieldsMissing = new ArrayList<>();
                if (!fields.contains("xCoordinate")) fieldsMissing.add("xCoordinate");
                if (!fields.contains("yCoordinate")) fieldsMissing.add("yCoordinate");
                if (!fields.contains("Title")) fieldsMissing.add("Title");
                throw new TableError.MissingCoreFields(fieldsMissing);
            }
            fields.removeAll(List.of("xCoordinate", "yCoordinate"));
            for (CSVRecord record : records) {
                Map<String, String> parameters = new HashMap<>();
                for (String field : fields) {
                    parameters.put(field, record.get(field));
                }
                this.parameters.put(new Pair(Integer.parseInt(record.get("xCoordinate")),
                                Integer.parseInt(record.get(
                        "yCoordinate"))),
                        parameters);
            }
        } catch (IOException e) {
            throw new TableError.InvalidFile(filePath);
        }
    }

    /**
     * Obtains the parameters for the target at the pair = (x, y) location in the image.
     * @param pair the coordinates
     * @return a {@link HashMap} mapping the other parameters to their values if pair = (x, y) is a target in the table,
     * and returns {@code null} otherwise.
     */
    public Map<String, String> getParams(Pair pair) {
        if (this.parameters.get(pair) == null) return null;
        return new HashMap<>(this.parameters.get(pair));
    }

    /**
     * Gets a set of all the field names except xCoordinate and yCoordinate.
     * @return A {@link Set} object containing the names of all the fields in table except the xCoordinate and
     * yCoordinate names.
     */
    public Set<String> getFields() {
        return this.fields;
    }

    /**
     * getter for all the targets in this {@link Table}. A target is defined as a
     * an (x, y) coordinate pair for which there is an entry in the table.
     * @return a {@link List} of the targets encoded as {@link Pair} objects.
     */
    public List<Pair> getTargets() {
        return new ArrayList<>(parameters.keySet());
    }


    // code for isContainBOM and removeBOM copied from
    // https://mkyong.com/java/java-how-to-add-and-remove-bom-from-utf-8-file/#remove-bom-from-a-utf-8-file
    private static boolean isContainBOM(Path path) throws IOException {

        if (Files.notExists(path)) {
            throw new IllegalArgumentException("Path: " + path + " does not exists!");
        }

        boolean result = false;

        byte[] bom = new byte[3];
        try (InputStream is = new FileInputStream(path.toFile())) {

            // read 3 bytes of a file.
            is.read(bom);

            // BOM encoded as ef bb bf
            String content = new String(Hex.encodeHex(bom));
            if ("efbbbf".equalsIgnoreCase(content)) {
                result = true;
            }

        }

        return result;
    }

    private static void removeBom(Path path) throws IOException {

        if (isContainBOM(path)) {

            byte[] bytes = Files.readAllBytes(path);

            ByteBuffer bb = ByteBuffer.wrap(bytes);
            byte[] bom = new byte[3];
            // get the first 3 bytes
            bb.get(bom, 0, bom.length);

            // remaining
            byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
            bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);

            // override the same path
            Files.write(path, contentAfterFirst3Bytes);

        }
    }
}
