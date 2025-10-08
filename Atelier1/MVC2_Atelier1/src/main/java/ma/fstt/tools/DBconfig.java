package ma.fstt.tools;

import com.mysql.cj.jdbc.MysqlDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class DBconfig {

    private static DataSource dataSource;

    public DBconfig() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(3306);
        ds.setDatabaseName("atelier1_mvc2");
        ds.setUser("root");
        ds.setPassword("");
        dataSource = ds;
    }

    @Produces
    @ApplicationScoped
    public static DataSource produceDataSource() throws SQLException {
        if (dataSource == null) {
            new DBconfig();
        }
        return dataSource;
    }

    @Produces
    @RequestScoped
    public Connection produceConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}
