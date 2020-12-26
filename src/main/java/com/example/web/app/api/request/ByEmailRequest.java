package com.example.web.app.api.request;

import com.example.web.app.dao.model.AuthorizationUser;
import java.sql.*;

public class ByEmailRequest {
    static final String dbPath = "jdbc:postgresql://192.168.56.101:5432/hornsandhooves";
    static final String USER = "astrav";
    static final String PASS = "12345678";

    public static AuthorizationUser selectUserByEmail(String login) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT login, password FROM human WHERE login = '").append(login).append("'");
        try(Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
            Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.valueOf(query));
            resultSet.next();
            AuthorizationUser user = new AuthorizationUser();
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            query.setLength(0);
            query.append("SELECT role_name FROM roles WHERE login = '").append(user.getLogin()).append("'");

            ResultSet resultSetForCheck = stat.executeQuery(String.valueOf(query));
            resultSetForCheck.next();
            String role = resultSetForCheck.getString("role_name");
                if (role.equals("USER_Department")) {
                    user.setRole_name("USER_Department");
                } else if (role.equals("USER_Design")) {
                    user.setRole_name("USER_Design");
                } else if (role.equals("USER_Employee")) {
                    user.setRole_name("USER_Employee");
                } else if (role.equals("USER_Storage")) {
                    user.setRole_name("USER_Storage");
                } else if (role.equals("USER_Test")) {
                    user.setRole_name("USER_Test");
                } else if (role.equals("USER_Workshop")) {
                    user.setRole_name("USER_Workshop");
                }
            return user;
        } catch (SQLException e) {
            System.out.println("Ошибка получения пользователя из БД при аутентификации: " + e.getMessage());
            return new AuthorizationUser();
        }
    }
}