package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    private DatabaseBuilder() {}

    public static void build() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:urna.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS candidates");
            stmt.execute("""
                    CREATE TABLE candidates (
                        number         INTEGER NOT NULL,
                        name           TEXT    NOT NULL,
                        running_office TEXT    NOT NULL,
                        party          TEXT    NOT NULL,
                        votes          INTEGER NOT NULL DEFAULT 0,
                        PRIMARY KEY (number, running_office)
                    );
                    """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to build database schema", e);
        }
    }
}
