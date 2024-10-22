import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI {
    private UserManager userManager;
    private JFrame loginFrame;

    public LoginUI(UserManager userManager) {
        this.userManager = userManager;
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeComponents(panel);

        loginFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                User user = userManager.getUser(username);
                if (user != null) {
                    if (user.getPassword().isEmpty()) {
                        // First-time login, must set a new password
                        JPasswordField newPasswordField = new JPasswordField();
                        Object[] message = {"Enter new password:", newPasswordField};
                        int option = JOptionPane.showConfirmDialog(null, message, "Set Password", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            String newPassword = new String(newPasswordField.getPassword());
                            userManager.changeUserPassword(username, newPassword);
                            JOptionPane.showMessageDialog(loginFrame, "Password set successfully. Please login again.");
                        }
                    } else if (user.getPassword().equals(password)) {
                        if ("ADMIN".equals(username)) {
                            new AdminUI(userManager);
                        } else {
                            new UserUI(userManager, username, password);
                        }
                        loginFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials");
                    }
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "User not found");
                }
            }
        });
    }
}
