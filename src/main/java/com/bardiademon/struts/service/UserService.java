package com.bardiademon.struts.service;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private UserService() {
    }


    public static JjsonObject fetchUserById(final Connection sqlConnection, final long userId) {
        final String query = """
                   select * from `users` where `id` = ?
                """;
        try (final PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setLong(1, userId);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.last() && resultSet.isLast() && resultSet.getRow() > 0 && resultSet.first() && resultSet.isFirst()) {
                    return JjsonObject.create()
                            .put("id", resultSet.getLong("id"))
                            .put("name", resultSet.getString("name"))
                            .put("username", resultSet.getString("username"));
                }
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    public static JjsonObject fetchUserByUsernameAndPassword(final Connection sqlConnection, final String username, final String password) {
        final String query = """
                   select * from `users` where `username` = ? and `password` = ?
                """;
        try (final PreparedStatement preparedStatement = sqlConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.last() && resultSet.isLast() && resultSet.getRow() > 0 && resultSet.first() && resultSet.isFirst()) {
                    return JjsonObject.create()
                            .put("id", resultSet.getLong("id"))
                            .put("name", resultSet.getString("name"))
                            .put("username", resultSet.getString("username"));
                }
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

}
