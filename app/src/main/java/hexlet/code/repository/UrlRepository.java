package hexlet.code.repository;

import hexlet.code.model.Url;
import java.sql.Statement;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(new Url(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at")
            ));
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(new Url(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at")
            ));
        }
    }

    public static List<Url> findAll(int rowsPerPage, int offset) throws SQLException {
        // Сортируем по ASC, чтобы ID 1 был вверху
        String sql = "SELECT * FROM urls ORDER BY id ASC LIMIT ? OFFSET ?";
        List<Url> urls = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rowsPerPage);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                urls.add(new Url(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return urls;
    }

    public static void save(Url url) throws SQLException {
        System.out.println("Сохраняем URL: " + url.getName());  // Лог
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, url.getCreatedAt());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                url.setId(rs.getLong(1));
                System.out.println("Сохранен URL с ID: " + url.getId());  // Лог
            }
        }
    }

    public static int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM urls";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
