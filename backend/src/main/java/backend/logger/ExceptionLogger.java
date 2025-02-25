package backend.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class ExceptionLogger {

    private static final Path PATH_TO_FILE_WITH_EXCEPTION_LOGS =
        Path.of("src", "main", "java", "backend", "logger", "ExceptionLogger.txt");

    public static void log(String message, String stackTrace, LocalDateTime time) throws IOException {
        try (Writer writer = new FileWriter(PATH_TO_FILE_WITH_EXCEPTION_LOGS.toFile(), true)) {
            writer.write(message);
            writer.write(System.lineSeparator());
            writer.write(stackTrace);
            writer.write(System.lineSeparator());
            writer.write(time.toString());
            writer.write(System.lineSeparator());
            writer.flush();
        }
    }

}
