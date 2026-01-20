package hexlet.code.repository;

import hexlet.code.DataSourceConfig;

import javax.sql.DataSource;

public abstract class BaseRepository {
    public static DataSource dataSource = DataSourceConfig.getDataSource();
}