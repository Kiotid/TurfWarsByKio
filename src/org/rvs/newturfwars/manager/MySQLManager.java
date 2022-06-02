package org.rvs.newturfwars.manager;

import java.sql.DriverManager;

import org.rvs.newturfwars.Main;

import java.sql.Connection;

public class MySQLManager
{
    private Main main;
    private Connection connection;
    
    public MySQLManager(final Main main) {
        this.main = main;
    }
    
    public void setup() {
        final String host = this.main.getConfig().getString("database.host");
        final int port = this.main.getConfig().getInt("database.port");
        final String database = this.main.getConfig().getString("database.database");
        final String username = this.main.getConfig().getString("database.username");
        final String password = this.main.getConfig().getString("database.password");
        try {
            synchronized (this) {
                if (this.getConnection() != null && !this.getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                this.setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password));
                System.out.println("[NewKioTurfWars] Connesso con successo al database!");
            }
        }
        catch (Exception e) {

        }
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public void setConnection(final Connection connection) {
        this.connection = connection;
    }
}
