import java.util.HashSet;
import java.util.Set;

public class User {
    private String username;
    private String password;
    private boolean isBlocked;
    private boolean hasPasswordRestrictions;

    public User(String username) {
        this.username = username;
        this.password = "";
        this.isBlocked = false;
        this.hasPasswordRestrictions = false;
    }

    public User(String username, String password, boolean isBlocked, boolean hasPasswordRestrictions) {
        this.username = username;
        this.password = password;
        this.isBlocked = isBlocked;
        this.hasPasswordRestrictions = hasPasswordRestrictions;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean hasPasswordRestrictions() {
        return hasPasswordRestrictions;
    }

    public void setPasswordRestrictions(boolean hasRestrictions) {
        this.hasPasswordRestrictions = hasRestrictions;
    }

    public boolean isPasswordValid(String newPassword) {
        if (!hasPasswordRestrictions) {
            return true;
        }
        // Проверка на отсутствие повторяющихся символов
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : newPassword.toCharArray()) {
            if (!uniqueChars.add(c)) {
                return false; // Есть повторяющиеся символы
            }
        }
        return true;
    }
}
