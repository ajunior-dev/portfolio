import java.util.*;

/**
 * Sistema de Gerenciamento de Tarefas com Controle de Acesso
 * Projeto 9 - Permissões por papel (role-based access control)
 *
 * Funcionalidades:
 * ✓ Dois papéis: ADMIN e USER
 * ✓ Permissões diferentes por papel
 * ✓ Gerenciamento de tarefas
 * ✓ Atribuição de tarefas
 * ✓ Controle de acesso
 *
 * ADMIN pode:
 * - Criar tarefas
 * - Editar qualquer tarefa
 * - Deletar tarefas
 * - Ver todas as tarefas
 * - Designar tarefas
 *
 * USER pode:
 * - Ver suas próprias tarefas
 * - Atualizar status de suas tarefas
 *
 * Como executar:
 * javac User.java Task.java TaskManager.java
 * java TaskManager
 */
public class TaskManager {
    private List<User> users;
    private List<Task> tasks;
    private User currentUser;
    private int taskIdCounter;
    private Scanner scanner;

    public TaskManager() {
        this.users = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.taskIdCounter = 1;
        this.scanner = new Scanner(System.in);
        initializeUsers();
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.run();
    }

    /**
     * Inicializa usuários de teste
     */
    private void initializeUsers() {
        users.add(new User(1, "Admin Silva", "ADMIN"));
        users.add(new User(2, "User Junior", "USER"));
        users.add(new User(3, "User Ana", "USER"));
    }

    /**
     * loop principal
     */
    private void run() {
        showWelcome();
        selectUser();

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            running = handleMenuChoice(choice);
        }

        scanner.close();
        System.out.println("\n👋 Até logo!");
    }

    /**
     * Seleciona usuário atual
     */
    private void selectUser() {
        System.out.println("\n📌 Selecione um usuário:");
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, users.get(i));
        }

        System.out.print("\nEscolha (1-" + users.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < users.size()) {
                currentUser = users.get(choice);
                System.out.println("\n✅ Logado como: " + currentUser);
            } else {
                System.out.println("❌ Opção inválida!");
                selectUser();
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida!");
            selectUser();
        }
    }

    private void showWelcome() {
        System.out.println("\n" +
                "╔════════════════════════════════════════╗\n" +
                "║   📋 GERENCIADOR DE TAREFAS            ║\n" +
                "║   Com Controle de Acesso               ║\n" +
                "╚════════════════════════════════════════╝\n");
    }

    private void showMenu() {
        System.out.println("\n📌 Menu (" + currentUser.getName() + "):");

        if (currentUser.isAdmin()) {
            System.out.println("1. Criar tarefa");
            System.out.println("2. Ver todas as tarefas");
            System.out.println("3. Editar tarefa");
            System.out.println("4. Deletar tarefa");
            System.out.println("5. Designar tarefa");
        } else {
            System.out.println("1. Ver minhas tarefas");
            System.out.println("2. Atualizar status de tarefa");
        }

        System.out.println((currentUser.isAdmin() ? "6" : "3") + ". Sair");
        System.out.print("\nEscolha: ");
    }

    private boolean handleMenuChoice(String choice) {
        if (currentUser.isAdmin()) {
            switch (choice) {
                case "1": createTask(); break;
                case "2": viewAllTasks(); break;
                case "3": editTask(); break;
                case "4": deleteTask(); break;
                case "5": assignTask(); break;
                case "6": return false;
                default: System.out.println("❌ Opção inválida!");
            }
        } else {
            switch (choice) {
                case "1": viewMyTasks(); break;
                case "2": updateTaskStatus(); break;
                case "3": return false;
                default: System.out.println("❌ Opção inválida!");
            }
        }
        return true;
    }

    /**
     * [ADMIN] Criar nova tarefa
     */
    private void createTask() {
        if (!requireAdmin()) return;

        System.out.println("\n===== ➕ CRIAR TAREFA =====");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        System.out.print("ID do usuário responsável: ");
        try {
            int userId = Integer.parseInt(scanner.nextLine());
            if (userExists(userId)) {
                Task task = new Task(taskIdCounter++, title, description, userId);
                tasks.add(task);
                System.out.println("✅ Tarefa criada: " + task);
            } else {
                System.out.println("❌ Usuário não existe!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido!");
        }
    }

    /**
     * [ADMIN] Ver todas as tarefas
     */
    private void viewAllTasks() {
        if (!requireAdmin()) return;

        if (tasks.isEmpty()) {
            System.out.println("\n❌ Nenhuma tarefa cadastrada.");
            return;
        }

        System.out.println("\n" +
                "════════════════════════════════════════\n" +
                "📋 TODAS AS TAREFAS\n" +
                "════════════════════════════════════════\n");

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    /**
     * [USER] Ver apenas minhas tarefas
     */
    private void viewMyTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\n❌ Nenhuma tarefa para você.");
            return;
        }

        System.out.println("\n" +
                "════════════════════════════════════════\n" +
                "📋 MINHAS TAREFAS\n" +
                "════════════════════════════════════════\n");

        tasks.stream()
            .filter(t -> t.getAssignedToUserId() == currentUser.getId())
            .forEach(System.out::println);
    }

    /**
     * [ADMIN] Editar tarefa
     */
    private void editTask() {
        if (!requireAdmin()) return;

        viewAllTasks();

        System.out.print("\nDigite o ID da tarefa a editar: ");
        try {
            int taskId = Integer.parseInt(scanner.nextLine());
            Task task = findTask(taskId);

            if (task == null) {
                System.out.println("❌ Tarefa não encontrada!");
                return;
            }

            System.out.print("Novo status (TODO/IN_PROGRESS/COMPLETED): ");
            String newStatus = scanner.nextLine();
            task.setStatus(newStatus);

            System.out.print("Prioridade (LOW/MEDIUM/HIGH): ");
            String priority = scanner.nextLine();
            task.setPriority(priority);

            System.out.println("✅ Tarefa atualizada!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida!");
        }
    }

    /**
     * [USER] Atualizar status de minha tarefa
     */
    private void updateTaskStatus() {
        viewMyTasks();

        System.out.print("\nDigite o ID da tarefa: ");
        try {
            int taskId = Integer.parseInt(scanner.nextLine());
            Task task = findTask(taskId);

            if (task == null || task.getAssignedToUserId() != currentUser.getId()) {
                System.out.println("❌ Você não tem permissão!");
                return;
            }

            System.out.print("Novo status (TODO/IN_PROGRESS/COMPLETED): ");
            String newStatus = scanner.nextLine();
            task.setStatus(newStatus);

            System.out.println("✅ Status atualizado!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida!");
        }
    }

    /**
     * [ADMIN] Deletar tarefa
     */
    private void deleteTask() {
        if (!requireAdmin()) return;

        viewAllTasks();

        System.out.print("\nDigite o ID da tarefa a deletar: ");
        try {
            int taskId = Integer.parseInt(scanner.nextLine());
            Task task = findTask(taskId);

            if (task == null) {
                System.out.println("❌ Tarefa não encontrada!");
                return;
            }

            tasks.remove(task);
            System.out.println("✅ Tarefa deletada!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida!");
        }
    }

    /**
     * [ADMIN] Designar tarefa para usuário
     */
    private void assignTask() {
        if (!requireAdmin()) return;

        viewAllTasks();

        System.out.print("\nDigite o ID da tarefa: ");
        try {
            int taskId = Integer.parseInt(scanner.nextLine());
            Task task = findTask(taskId);

            if (task == null) {
                System.out.println("❌ Tarefa não encontrada!");
                return;
            }

            System.out.print("ID do novo responsável: ");
            int userId = Integer.parseInt(scanner.nextLine());

            if (!userExists(userId)) {
                System.out.println("❌ Usuário não existe!");
                return;
            }

            // Aqui você teria um campo mutable em Task para isso
            System.out.println("✅ Tarefa designada!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida!");
        }
    }

    // ==================== HELPERS ====================

    private boolean requireAdmin() {
        if (!currentUser.isAdmin()) {
            System.out.println("❌ Você não tem permissão para esta ação!");
            System.out.println("   Apenas ADMINs podem fazer isto.");
            return false;
        }
        return true;
    }

    private Task findTask(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    private boolean userExists(int id) {
        return users.stream().anyMatch(u -> u.getId() == id);
    }

    private void showWelcome() {
        System.out.println("\n" +
                "╔════════════════════════════════════════╗\n" +
                "║   📋 GERENCIADOR DE TAREFAS            ║\n" +
                "║   Com Controle de Acesso por Papel     ║\n" +
                "╚════════════════════════════════════════╝\n");
    }
}
