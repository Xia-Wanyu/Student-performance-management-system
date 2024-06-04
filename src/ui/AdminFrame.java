package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.DatabaseConnection;
import model.User;

public class AdminFrame extends JFrame {
    private User user;

    public AdminFrame(User user) {
        this.user = user;
        setTitle("管理员界面");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton manageUsersButton = new JButton("管理账户");
        JButton manageStudentsButton = new JButton("管理学生信息");
        JButton manageTeachersButton = new JButton("管理教师信息");

        add(manageUsersButton);
        add(manageStudentsButton);
        add(manageTeachersButton);

        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"添加账户", "修改账户", "删除账户"};
                int choice = JOptionPane.showOptionDialog(null, "请选择操作:", "管理账户",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch (choice) {
                    case 0:
                        String newUsername = JOptionPane.showInputDialog("请输入用户名:");
                        String newPassword = JOptionPane.showInputDialog("请输入密码:");
                        String newRole = JOptionPane.showInputDialog("请输入角色 (student/teacher/admin):");
                        addAccount(newUsername, newPassword, newRole);
                        break;
                    case 1:
                        String usernameToModify = JOptionPane.showInputDialog("请输入要修改的用户名:");
                        String newPasswordForModify = JOptionPane.showInputDialog("请输入新密码:");
                        modifyAccount(usernameToModify, newPasswordForModify);
                        break;
                    case 2:
                        String usernameToDelete = JOptionPane.showInputDialog("请输入要删除的用户名:");
                        deleteAccount(usernameToDelete);
                        break;
                    default:
                        break;
                }
            }
        });

        manageStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"添加学生", "修改学生信息", "删除学生"};
                int choice = JOptionPane.showOptionDialog(null, "请选择操作:", "管理学生信息",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch (choice) {
                    case 0:
                        String studentId = JOptionPane.showInputDialog("请输入学生ID:");
                        String studentName = JOptionPane.showInputDialog("请输入学生姓名:");
                        String studentClass = JOptionPane.showInputDialog("请输入学生班级:");
                        addStudent(studentId, studentName, studentClass);
                        break;
                    case 1:
                        String studentIdToModify = JOptionPane.showInputDialog("请输入要修改的学生ID:");
                        String newStudentName = JOptionPane.showInputDialog("请输入新学生姓名:");
                        String newStudentClass = JOptionPane.showInputDialog("请输入新学生班级:");
                        modifyStudent(studentIdToModify, newStudentName, newStudentClass);
                        break;
                    case 2:
                        String studentIdToDelete = JOptionPane.showInputDialog("请输入要删除的学生ID:");
                        deleteStudent(studentIdToDelete);
                        break;
                    default:
                        break;
                }
            }
        });

        manageTeachersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"添加教师", "修改教师信息", "删除教师"};
                int choice = JOptionPane.showOptionDialog(null, "请选择操作:", "管理教师信息",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch (choice) {
                    case 0:
                        String teacherId = JOptionPane.showInputDialog("请输入教师ID:");
                        String teacherName = JOptionPane.showInputDialog("请输入教师姓名:");
                        String teacherDepartment = JOptionPane.showInputDialog("请输入教师部门:");
                        addTeacher(teacherId, teacherName, teacherDepartment);
                        break;
                    case 1:
                        String teacherIdToModify = JOptionPane.showInputDialog("请输入要修改的教师ID:");
                        String newTeacherName = JOptionPane.showInputDialog("请输入新教师姓名:");
                        String newTeacherDepartment = JOptionPane.showInputDialog("请输入新教师部门:");
                        modifyTeacher(teacherIdToModify, newTeacherName, newTeacherDepartment);
                        break;
                    case 2:
                        String teacherIdToDelete = JOptionPane.showInputDialog("请输入要删除的教师ID:");
                        deleteTeacher(teacherIdToDelete);
                        break;
                    default:
                        break;
                }
            }
        });

        setVisible(true);
    }

    private void addAccount(String username, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "账户添加成功");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modifyAccount(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "账户密码修改成功");
            } else {
                JOptionPane.showMessageDialog(null, "未找到该用户");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteAccount(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "账户删除成功");
            } else {
                JOptionPane.showMessageDialog(null, "未找到该用户");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addStudent(String studentId, String name, String studentClass) {
        String query = "INSERT INTO students (student_id, name, class) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, studentClass);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "学生添加成功");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modifyStudent(String studentId, String name, String studentClass) {
        String query = "UPDATE students SET name = ?, class = ? WHERE student_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, studentClass);
            preparedStatement.setString(3, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "学生信息修改成功");
            } else {
                JOptionPane.showMessageDialog(null, "未找到该学生");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteStudent(String studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "学生删除成功");
            } else {
                JOptionPane.showMessageDialog(null, "未找到该学生");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addTeacher(String teacherId, String name, String department) {
        String query = "INSERT INTO teachers (teacher_id, name, department) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, department);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "教师添加成功");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modifyTeacher(String teacherId, String name, String department) {
        String query = "UPDATE teachers SET name = ?, department = ? WHERE teacher_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, department);
            preparedStatement.setString(3, teacherId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "教师信息修改成功");
            } else {
                JOptionPane.showMessageDialog(null, "未找到该教师");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteTeacher(String teacherId) {
        String query = "DELETE FROM teachers WHERE teacher_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teacherId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "教师删除成功");
            } else {
                JOptionPane.showMessageDialog(null, "未找到该教师");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

