package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User validateUser(String username, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
                user.setCanViewGrades(resultSet.getBoolean("can_view_grades"));
                user.setCanModifyGrades(resultSet.getBoolean("can_modify_grades"));
                user.setCanInputGrades(resultSet.getBoolean("can_input_grades"));
                user.setCanModifyPersonalInfo(resultSet.getBoolean("can_modify_personal_info"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
