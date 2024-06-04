package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("学生成绩管理系统登录");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setBounds(50, 30, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 30, 120, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 70, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 70, 120, 25);
        add(passwordField);

        loginButton = new JButton("登录");
        loginButton.setBounds(100, 110, 80, 25);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                UserDAO userDAO = new UserDAO();
                User user = userDAO.validateUser(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "登录成功！");
                    // 根据用户角色显示不同的界面
                    switch (user.getRole()) {
                        case "student":
                            new StudentFrame(user).setVisible(true);
                            break;
                        case "teacher":
                            new TeacherFrame(user).setVisible(true);
                            break;
                        case "admin":
                            new AdminFrame(user).setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "未知角色！");
                            break;
                    }
                    dispose(); // 关闭登录界面
                } else {
                    JOptionPane.showMessageDialog(null, "登录失败，用户名或密码错误。");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
