package main;

import dao.GradeDAO;
import dao.UserDAO;
import model.Grade;
import model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser("student1", "password1");

        if (user != null) {
            System.out.println("登录成功，欢迎 " + user.getUsername() + "!");
            System.out.println("角色: " + user.getRole());
            System.out.println("是否可以查看成绩: " + user.isCanViewGrades());

            if (user.getRole().equals("student") && user.isCanViewGrades()) {
                GradeDAO gradeDAO = new GradeDAO();
                List<Grade> grades = gradeDAO.getGradesByStudentId(user.getId());
                for (Grade grade : grades) {
                    System.out.println("科目: " + grade.getSubject() + ", 成绩: " + grade.getGrade());
                }
            }
        } else {
            System.out.println("登录失败，用户名或密码错误。");
        }
    }
}
