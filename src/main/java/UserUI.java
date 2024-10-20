import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserUI {
    private JFrame userFrame;
    private UserManager userManager;
    private String username;

    public UserUI(UserManager userManager, String username) {
        this.userManager = userManager;
        this.username = username;

        userFrame = new JFrame("User Panel");
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(400, 200);
        userFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));

        // Смена пароля пользователя
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = JOptionPane.showInputDialog("Enter old password:");
                String newPassword = JOptionPane.showInputDialog("Enter new password:");
                String confirmPassword = JOptionPane.showInputDialog("Confirm new password:");

                User user = userManager.getUser(username);
                if (user.getPassword().equals(oldPassword)) {
                    if (newPassword.equals(confirmPassword)) {
                        if (user.isPasswordValid(newPassword)) {
                            userManager.changeUserPassword(username, newPassword);
                            JOptionPane.showMessageDialog(userFrame, "Password changed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(userFrame, "Password does not meet the restrictions!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(userFrame, "Passwords do not match!");
                    }
                } else {
                    JOptionPane.showMessageDialog(userFrame, "Old password is incorrect!");
                }
            }
        });

        // Завершение работы
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userFrame.dispose();
            }
        });

        buttonPanel.add(changePasswordButton);
        buttonPanel.add(exitButton);

        userFrame.add(buttonPanel, BorderLayout.CENTER);
        userFrame.setVisible(true);
    }
}
