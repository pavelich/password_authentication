import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserUI {
    private JFrame userFrame;
    private UserManager userManager;
    private String username;
    private String password;

    public UserUI(UserManager userManager, String username, String password) {
        this.userManager = userManager;
        this.username = username;
        this.password = password;

        userFrame = new JFrame("User Panel");
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(400, 300);
        userFrame.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setBounds(10, 10, 300, 25);
        userFrame.add(welcomeLabel);


        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(10, 50, 150, 25);
        userFrame.add(changePasswordButton);
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField oldPasswordField = new JPasswordField();
                JPasswordField newPasswordField = new JPasswordField();
                JPasswordField confirmPasswordField = new JPasswordField();
                Object[] message = {
                        "Enter old password:", oldPasswordField,
                        "Enter new password:", newPasswordField,
                        "Confirm new password:", confirmPasswordField
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Change Password", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String oldPassword = new String(oldPasswordField.getPassword());
                    String newPassword = new String(newPasswordField.getPassword());
                    String confirmPassword = new String(confirmPasswordField.getPassword());

                    if (oldPassword.equals(password)) {
                        if (newPassword.equals(confirmPassword)) {
                            userManager.changeUserPassword(username, newPassword);
                            JOptionPane.showMessageDialog(userFrame, "Password changed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(userFrame, "New passwords do not match!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(userFrame, "Old password is incorrect!");
                    }
                }
            }
        });


        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(10, 90, 150, 25);
        userFrame.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userFrame.dispose();
            }
        });

        userFrame.setVisible(true);
    }
}
