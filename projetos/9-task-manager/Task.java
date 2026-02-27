/**
 * Representa uma tarefa no sistema
 */
public class Task {
    private int id;
    private String title;
    private String description;
    private String status; // TODO, IN_PROGRESS, COMPLETED
    private int assignedToUserId;
    private String priority; // LOW, MEDIUM, HIGH

    public Task(int id, String title, String description, int assignedToUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "TODO";
        this.assignedToUserId = assignedToUserId;
        this.priority = "MEDIUM";
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getAssignedToUserId() { return assignedToUserId; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    @Override
    public String toString() {
        return String.format("📝 [%d] %s (Status: %s | Prioridade: %s | Atribuído a: %d)",
            id, title, status, priority, assignedToUserId);
    }
}
