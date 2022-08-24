package edu.cmu.cs.cs214.medannot.image;

public class ImageError extends Throwable {
    public static class InvalidImage extends Throwable {
        private String filePath;
        public InvalidImage(String filePath) {this.filePath = filePath;}
        public String getFilePath() {return this.filePath;}
    }
}
