package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DatabaseConnection;
import model.User;

public class TeacherFrame extends JFrame {
    private User user;

    public TeacherFrame(User user) {
        this.user = user;
        setTitle("教师界面");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton viewGradesButton = new JButton("查询成绩");
        JButton modifyGradesButton = new JButton("修改成绩");
        JButton inputGradesButton = new JButton("录入成绩");

        add(viewGradesButton);
        add(modifyGradesButton);
        add(inputGradesButton);

        viewGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = JOptionPane.showInputDialog("请输入学生ID:");
                String query = "SELECT subject, grade FROM grades WHERE student_id = ?";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, studentId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    StringBuilder grades = new StringBuilder("成绩:\n");
                    while (resultSet.next()) {
                        grades.append(resultSet.getString("subject")).append(": ")
                                .append(resultSet.getDouble("grade")).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, grades.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        modifyGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = JOptionPane.showInputDialog("请输入学生ID:");
                String subject = JOptionPane.showInputDialog("请输入科目:");
                String newGradeStr = JOptionPane.showInputDialog("请输入新成绩:");
                double newGrade = Double.parseDouble(newGradeStr);

                String query = "UPDATE grades SET grade = ? WHERE student_id = ? AND subject = ?";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setDouble(1, newGrade);
                    preparedStatement.setString(2, studentId);
                    preparedStatement.setString(3, subject);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "成绩修改成功");
                    } else {
                        JOptionPane.showMessageDialog(null, "未找到该学生的成绩记录");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        inputGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = JOptionPane.showInputDialog("请输入学生ID:");
                String subject = JOptionPane.showInputDialog("请输入科目:");
                String gradeStr = JOptionPane.showInputDialog("请输入成绩:");
                double grade = Double.parseDouble(gradeStr);

                String query = "INSERT INTO grades (student_id, subject, grade) VALUES (?, ?, ?)";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, studentId);
                    preparedStatement.setString(2, subject);
                    preparedStatement.setDouble(3, grade);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "成绩录入成功");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}
