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

public class StudentFrame extends JFrame {
    private User user;

    public StudentFrame(User user) {
        this.user = user;
        setTitle("学生界面");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton viewGradesButton = new JButton("查询成绩");
        add(viewGradesButton);

        viewGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT subject, grade FROM grades WHERE student_id = ?";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, user.getId());
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

        setVisible(true);
    }
}
