package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck check) throws SQLException {
        check.setCreatedAt(Instant.now()); // Время создаем тут

        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, check.getStatusCode());
            stmt.setString(2, check.getTitle());
            stmt.setString(3, check.getH1());
            stmt.setString(4, check.getDescription());
            stmt.setLong(5, check.getUrlId());
            stmt.setTimestamp(6, Timestamp.from(check.getCreatedAt())); // Конвертация
            stmt.executeUpdate();

            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1));
                log.info("UrlCheck saved with id: {}", check.getId());
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
                result.add(new UrlCheck(
                        rs.getLong("id"),
                        rs.getInt("status_code"),
                        rs.getString("title"),
                        rs.getString("h1"),
                        rs.getString("description"),
                        rs.getLong("url_id"),
                        rs.getTimestamp("created_at").toInstant()
                ));
            }
        }
        return result;
    }

    public static Map<Long, UrlCheck> findLatestChecks() throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE id IN (SELECT MAX(id) FROM url_checks GROUP BY url_id)";
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
                        rs.getTimestamp("created_at").toInstant()
                );
                result.put(check.getUrlId(), check);
            }
        }
        return result;
    }
}
