package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CsvUtils {
    private static List<String[]> data;
    private static Map<String, List<String[]>> byOffice;

    public static List<String[]> readAllCandidates() {
        if (data != null) return data;

        data = new ArrayList<>();
        byOffice = new HashMap<>();
        InputStream is = CsvUtils.class.getClassLoader().getResourceAsStream("candidates.csv");
        if (is == null) throw new IllegalArgumentException("Resource not found: candidates.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    String[] row = line.split(",", -1);
                    data.add(row);
                    byOffice.computeIfAbsent(row[2], k -> new ArrayList<>()).add(row);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV: candidates.csv", e);
        }
        return data;
    }

    public static int randomCandidateNumber(String office) {
        readAllCandidates();
        List<String[]> candidates = byOffice.getOrDefault(office, List.of());
        if (candidates.isEmpty()) throw new IllegalArgumentException("No candidates found for office: " + office);
        return Integer.parseInt(candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()))[0]);
    }
}
