package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlCheckRepository extends BaseRepository {

    public static void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static void save(UrlCheck check) throws SQLException {
        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, check.getStatusCode());
            stmt.setString(2, check.getTitle());
            stmt.setString(3, check.getH1());
            stmt.setString(4, check.getDescription());
            stmt.setLong(5, check.getUrlId());
            stmt.setTimestamp(6, check.getCreatedAt());
            stmt.executeUpdate();
            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1));
            }
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        var result = new ArrayList<UrlCheck>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var check = new UrlCheck(
                        rs.getLong("id"),
                        rs.getInt("status_code"),
                        rs.getString("title"),
                        rs.getString("h1"),
                        rs.getString("description"),
                        rs.getLong("url_id"),
                        rs.getTimestamp("created_at")
                );
                result.add(check);
            }
        }
        return result;
    }

    public static Map<Long, UrlCheck> findLatestChecks() throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE id IN (SELECT MAX(id) FROM url_checks GROUP BY url_id )";
        var result = new HashMap<Long, UrlCheck>();

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var check = new UrlCheck(
                        rs.getLong("id"),
                        rs.getInt("status_code"),
                        rs.getString("title"),
                        rs.getString("h1"),
                        rs.getString("description"),
                        rs.getLong("url_id"),
                        rs.getTimestamp("created_at")
                );
                result.put(check.getUrlId(), check);
            }
        }
        return result;
    }
}
