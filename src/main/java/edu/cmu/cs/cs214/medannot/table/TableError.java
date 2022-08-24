package edu.cmu.cs.cs214.medannot.table;

import java.util.ArrayList;
import java.util.List;

public class TableError extends Throwable {
    public static class InvalidFile extends Throwable {
        private String filePath;
        public InvalidFile(String filePath) {
            this.filePath = filePath;
        }
        public String getFilePath() {
            return this.filePath;
        }
    }
    public static class EmptyCSV extends Throwable {
        private String filePath;
        public EmptyCSV(String filePath) {
            this.filePath = filePath;
        }
        public String getFilePath() {
            return this.filePath;
        }
    }
    public static class MissingCoreFields extends Throwable {
        private List<String> fieldsMissing = new ArrayList<>();
        public MissingCoreFields(List<String> fieldsMissing) {
            this.fieldsMissing.addAll(fieldsMissing);
        }

        public List<String> getFieldsMissing() {
            return new ArrayList<>(this.fieldsMissing);
        }
    }
}
