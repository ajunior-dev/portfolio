/**
 * Classe que representa um usuário com papel (role) específico
 */
public class User {
    private int id;
    private String name;
    private String role; // ADMIN ou USER

    public User(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

    @Override
    public String toString() {
        return String.format("👤 %s [%s]", name, role);
    }
}
