import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, User> users;
    private static final String FILE_NAME = "users.txt";

    public UserManager() {
        users = new HashMap<>();
    }

    public void addUser(String username) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username));
            saveUsers("");
        }
    }

    public void changeUserPassword(String username, String newPassword) {
        User user = users.get(username);
        if (user != null) {
            user.setPassword(newPassword);
            saveUsers("");
        }
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public void blockUser(String username) {
        User user = users.get(username);
        if (user != null) {
            user.setBlocked(true);
            saveUsers("");
        }
    }

    public void unblockUser(String username) {
        User user = users.get(username);
        if (user != null) {
            user.setBlocked(false);
            saveUsers("");
        }
    }

    public void setPasswordRestrictions(String username, boolean hasRestrictions) {
        User user = users.get(username);
        if (user != null) {
            user.setPasswordRestrictions(hasRestrictions);
            saveUsers("");
        }
    }

    public void saveUsers(String passphrase) {
        try {
            StringBuilder data = new StringBuilder();
            for (User user : users.values()) {
                data.append(user.getUsername()).append(",")
                        .append(user.getPassword()).append(",")
                        .append(user.isBlocked()).append(",")
                        .append(user.hasPasswordRestrictions()).append("\n");
            }
            if (!passphrase.isEmpty()) {
                CryptoUtil.encrypt(data.toString(), FILE_NAME, passphrase);
            } else {
                try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
                    writer.print(data.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadUsers(String passphrase) throws Exception {
        File userFile = new File(FILE_NAME);
        if (!userFile.exists()) {
            System.out.println("User file not found. Creating ADMIN user.");
            addUser("ADMIN");
            saveUsers("");
            return;
        }


        try {
            String decryptedData = CryptoUtil.decrypt(FILE_NAME, passphrase);
            if (decryptedData.trim().isEmpty()) {
                throw new IOException("Decrypted data is empty!");
            }
            String[] lines = decryptedData.split("\n");
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String password = parts[1];
                    boolean isBlocked = Boolean.parseBoolean(parts[2]);
                    boolean hasRestrictions = Boolean.parseBoolean(parts[3]);
                    users.put(username, new User(username, password, isBlocked, hasRestrictions));
                    System.out.println("Loaded user: " + username); // Debug line
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Failed to load users.");
        }
    }
}
