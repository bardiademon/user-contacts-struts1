package com.bardiademon.struts.util;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectToDatabase {

    private final static Logger LOGGER = System.getLogger(ConnectToDatabase.class.getName());

    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/user_contacts";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "73487712";

    private ConnectToDatabase() {
    }

    public static Connection connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final Connection connection = DriverManager.getConnection(URL_CONNECTION, USERNAME, PASSWORD);
            LOGGER.log(Level.INFO, "Successfully connection database");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "Fail to connecting to database", e);
            return null;
        }
    }

}
