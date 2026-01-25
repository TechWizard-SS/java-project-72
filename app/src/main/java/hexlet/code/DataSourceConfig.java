package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DataSourceConfig {
    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(System.getenv().getOrDefault("JDBC_DATABASE_URL",
                    "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1"));

            config.setUsername(System.getenv("JDBC_DATABASE_USERNAME"));
            config.setPassword(System.getenv("JDBC_DATABASE_PASSWORD"));

            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}
