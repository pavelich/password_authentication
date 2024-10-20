import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI {
    private JFrame loginFrame;
    private UserManager userManager;

    public LoginUI(UserManager userManager) {
        this.userManager = userManager;

        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);
        loginFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                User user = userManager.getUser(username);

                if (user == null) {
                    JOptionPane.showMessageDialog(loginFrame, "User not found!");
                    return;
                }

                if (user.isBlocked()) {
                    JOptionPane.showMessageDialog(loginFrame, "User is blocked!");
                    return;
                }

                // Проверка на пустой пароль (как у администратора, так и у пользователей)
                if (user.getPassword().isEmpty()) {
                    // Пользователь должен установить пароль при первом входе
                    String newPassword = JOptionPane.showInputDialog("Password is empty. Set a new password:");
                    if (newPassword != null && !newPassword.trim().isEmpty()) {
                        userManager.changeUserPassword(username, newPassword);
                        JOptionPane.showMessageDialog(loginFrame, "Password set successfully!");
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Password cannot be empty!");
                        return;
                    }
                }

                // Если пароль корректный, продолжаем вход
                if (user.getPassword().equals(password)) {
                    loginFrame.dispose();
                    if (username.equals("ADMIN")) {
                        new AdminUI(userManager);
                    } else {
                        new UserUI(userManager, username);
                    }
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid password!");
                }
            }
        });

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);

        loginFrame.add(panel, BorderLayout.CENTER);
        loginFrame.setVisible(true);
    }
}
