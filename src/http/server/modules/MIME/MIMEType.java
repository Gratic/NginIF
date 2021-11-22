package http.server.modules.MIME;

import java.util.HashMap;
import java.util.Map;

/**
 * MIME types wrapper.
 * Basic implementation.
 */
public class MIMEType {
    private static Map<String, String> extensionToMime;
    private static Map<String, String> mimeToExtension;

    private String mime;
    private String extension;

    public MIMEType() {
        initArrays();

        this.mime = "";
        this.extension = "";
    }

    public MIMEType setMime(String mime) {
        if (mimeToExtension.containsKey(mime)) {
            this.mime = mime;
            this.extension = mimeToExtension.get(mime);
        }

        return this;
    }

    public MIMEType setExtension(String extension) {
        if (extensionToMime.containsKey(extension)) {
            this.extension = extension;
            this.mime = extensionToMime.get(extension);
        }

        return this;
    }

    public String getMime() {
        return this.mime;
    }

    public String getExtension() {
        return this.extension;
    }

    private static void initArrays() {
        if (extensionToMime == null || extensionToMime.size() == 0 || mimeToExtension == null || mimeToExtension.size() == 0) {
            extensionToMime = new HashMap<>();
            mimeToExtension = new HashMap<>();

            mimeToExtension.put("application/x-executable", ".exe");
            mimeToExtension.put("application/graphql", ".gql");
            mimeToExtension.put("application/javascript", ".js");
            mimeToExtension.put("application/json", ".json");
            mimeToExtension.put("application/ld+json", ".json");
            mimeToExtension.put("application/feed+json", ".json");
            mimeToExtension.put("application/msword", ".doc");
            mimeToExtension.put("application/pdf", ".pdf");
            mimeToExtension.put("application/sql", ".sql");
            mimeToExtension.put("application/vnd.api+json", ".json");
            mimeToExtension.put("application/vnd.ms-excel", ".xls");
            mimeToExtension.put("application/vnd.ms-powerpoint", ".ppt");
            mimeToExtension.put("application/vnd.oasis.opendocument.text", ".odt");
            mimeToExtension.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx");
            mimeToExtension.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");
            mimeToExtension.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx");
            mimeToExtension.put("application/x-www-form-urlencoded", "");
            mimeToExtension.put("application/xml", ".xml");
            mimeToExtension.put("application/zip", ".zip");
            mimeToExtension.put("application/zstd", ".zst");
            mimeToExtension.put("application/macbinary", ".bin");
            mimeToExtension.put("audio/mpeg", ".mpeg");
            mimeToExtension.put("audio/ogg", ".ogg");
            mimeToExtension.put("image/apng", ".apng");
            mimeToExtension.put("image/avif", ".avif");
            mimeToExtension.put("image/flif", ".flif");
            mimeToExtension.put("image/gif", ".gif");
            mimeToExtension.put("image/jpeg", ".jpg");
            mimeToExtension.put("image/jxl", ".jxl");
            mimeToExtension.put("image/png", ".png");
            mimeToExtension.put("image/svg+xml", ".svg");
            mimeToExtension.put("image/webp", ".webp");
            mimeToExtension.put("image/x-mng", ".mng");
            mimeToExtension.put("multipart/form-data", "");
            mimeToExtension.put("text/css", ".css");
            mimeToExtension.put("text/csv", ".csv");
            mimeToExtension.put("text/html", ".html");
            mimeToExtension.put("text/php", ".php");
            mimeToExtension.put("text/plain", ".txt");
            mimeToExtension.put("text/xml", ".xml");

            for (Map.Entry<String, String> entry : mimeToExtension.entrySet()) {
                extensionToMime.put(entry.getValue(), entry.getKey());
            }

            extensionToMime.put(".jpeg", "image/jpeg");
            extensionToMime.put(".jfif", "image/jpeg");
            extensionToMime.put(".pjpeg", "image/jpeg");
            extensionToMime.put(".pjp", "image/jpeg");
        }
    }

    public static String getFileExtensionOf(String mime) {
        return mimeToExtension.get(mime);
    }

    public static String getMimeTypeOf(String fileExtension) {
        String fileToGet = fileExtension;

        if (!fileToGet.startsWith(".")) {
            fileToGet = "." + fileToGet;
        }

        return extensionToMime.get(fileToGet);
    }
}
