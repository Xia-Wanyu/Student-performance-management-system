package dao;

import model.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    public List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT * FROM grades WHERE student_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Grade grade = new Grade();
                grade.setId(resultSet.getInt("id"));
                grade.setStudentId(resultSet.getInt("student_id"));
                grade.setSubject(resultSet.getString("subject"));
                grade.setGrade(resultSet.getDouble("grade"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public void updateGrade(int studentId, String subject, double grade) {
        String query = "INSERT INTO grades (student_id, subject, grade) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE grade = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, subject);
            preparedStatement.setDouble(3, grade);
            preparedStatement.setDouble(4, grade);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
