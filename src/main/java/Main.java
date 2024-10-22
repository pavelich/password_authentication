import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {

            JPasswordField passphraseField = new JPasswordField();
            Object[] message = {"Enter passphrase:", passphraseField};
            int option = JOptionPane.showConfirmDialog(null, message, "Passphrase", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String passphrase = new String(passphraseField.getPassword());

                UserManager userManager = new UserManager();
                userManager.loadUsers(passphrase);


                new LoginUI(userManager);
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
