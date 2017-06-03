package database;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;

/**
 * Created by migueloliveira on 02/06/17.
 */
public class MarketDatabase
{
    public MarketDatabase()
    {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:/Users/migueloliveira//wizzio-db;mode=mysql");

        ds.setUser("pepe");
        ds.setPassword("jeans");

        try {
            String sql = Resources.toString(Resources.getResource("db/schema.sql"), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
