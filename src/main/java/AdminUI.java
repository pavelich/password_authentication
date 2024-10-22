import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI {
    private JFrame adminFrame;
    private UserManager userManager;

    public AdminUI(UserManager userManager) {
        this.userManager = userManager;

        adminFrame = new JFrame("Admin Panel");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(500, 400);
        adminFrame.setLayout(new BorderLayout());

        JTextArea userListArea = new JTextArea();
        userListArea.setEditable(false);
        updateUserList(userListArea);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1));

        JButton changeAdminPasswordButton = new JButton("Change Admin Password");
        changeAdminPasswordButton.addActionListener(new ActionListener() {
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

                int option = JOptionPane.showConfirmDialog(null, message, "Change Admin Password", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String oldPassword = new String(oldPasswordField.getPassword());
                    String newPassword = new String(newPasswordField.getPassword());
                    String confirmPassword = new String(confirmPasswordField.getPassword());

                    User admin = userManager.getUser("ADMIN");
                    if (admin.getPassword().equals(oldPassword)) {
                        if (newPassword.equals(confirmPassword)) {
                            userManager.changeUserPassword("ADMIN", newPassword);
                            JOptionPane.showMessageDialog(adminFrame, "Password changed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(adminFrame, "Passwords do not match!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "Old password is incorrect!");
                    }
                }
            }
        });


        JButton addUserButton = new JButton("Add New User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter new username:");
                if (userManager.getUser(username) == null) {
                    userManager.addUser(username);
                    updateUserList(userListArea);
                    JOptionPane.showMessageDialog(adminFrame, "User added successfully!");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "User already exists!");
                }
            }
        });


        JButton blockUserButton = new JButton("Block User");
        blockUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter username to block:");
                User user = userManager.getUser(username);
                if (user != null) {
                    userManager.blockUser(username);
                    updateUserList(userListArea);
                    JOptionPane.showMessageDialog(adminFrame, "User blocked successfully!");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "User not found!");
                }
            }
        });


        JButton unblockUserButton = new JButton("Unblock User");
        unblockUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter username to unblock:");
                User user = userManager.getUser(username);
                if (user != null) {
                    userManager.unblockUser(username);
                    updateUserList(userListArea);
                    JOptionPane.showMessageDialog(adminFrame, "User unblocked successfully!");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "User not found!");
                }
            }
        });


        JButton togglePasswordRestrictionsButton = new JButton("Toggle Password Restrictions");
        togglePasswordRestrictionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter username to toggle password restrictions:");
                User user = userManager.getUser(username);
                if (user != null) {
                    userManager.setPasswordRestrictions(username, !user.hasPasswordRestrictions());
                    updateUserList(userListArea);
                    String message = user.hasPasswordRestrictions() ? "Password restrictions enabled!" : "Password restrictions disabled!";
                    JOptionPane.showMessageDialog(adminFrame, message);
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "User not found!");
                }
            }
        });


        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
            }
        });

        buttonPanel.add(changeAdminPasswordButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(blockUserButton);
        buttonPanel.add(unblockUserButton);
        buttonPanel.add(togglePasswordRestrictionsButton);
        buttonPanel.add(exitButton);

        adminFrame.add(new JScrollPane(userListArea), BorderLayout.CENTER);
        adminFrame.add(buttonPanel, BorderLayout.EAST);

        adminFrame.setVisible(true);
    }

    private void updateUserList(JTextArea userListArea) {
        userListArea.setText("");
        for (User user : userManager.getUsers()) {
            userListArea.append("Username: " + user.getUsername() +
                    ", Blocked: " + user.isBlocked() +
                    ", Password Restrictions: " + user.hasPasswordRestrictions() + "\n");
        }
    }
}
