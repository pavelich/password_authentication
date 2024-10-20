import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, User> users;
    private static final String FILE_NAME = "users.txt";

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    public void addUser(String username) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username));
            saveUsers();
        }
    }

    public void changeUserPassword(String username, String newPassword) {
        User user = users.get(username);
        if (user != null) {
            user.setPassword(newPassword);
            saveUsers();
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
            saveUsers();
        }
    }

    public void unblockUser(String username) {
        User user = users.get(username);
        if (user != null) {
            user.setBlocked(false);
            saveUsers();
        }
    }

    public void setPasswordRestrictions(String username, boolean hasRestrictions) {
        User user = users.get(username);
        if (user != null) {
            user.setPasswordRestrictions(hasRestrictions);
            saveUsers();
        }
    }

    public void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users.values()) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.isBlocked() + "," + user.hasPasswordRestrictions());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String password = parts[1];
                    boolean isBlocked = Boolean.parseBoolean(parts[2]);
                    boolean hasRestrictions = Boolean.parseBoolean(parts[3]);
                    users.put(username, new User(username, password, isBlocked, hasRestrictions));
                }
            }
        } catch (FileNotFoundException e) {
            addUser("ADMIN"); // Добавляем администратора с пустым паролем
            saveUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
