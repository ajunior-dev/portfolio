import java.util.Scanner;

/**
 * Sistema de Login com Controle de Tentativas
 * Projeto 1 - Nível: Iniciante → Intermediário
 *
 * Funcionalidades:
 * ✓ Cadastro de usuário
 * ✓ Login com validação
 * ✓ Limite de 3 tentativas
 * ✓ Bloqueio temporário (2 minutos)
 * ✓ Registro de logs em arquivo
 * ✓ Hash SHA-256 para senhas
 *
 * Como executar:
 * javac User.java LoginManager.java LoginSystem.java
 * java LoginSystem
 */
public class LoginSystem {
    private static LoginManager loginManager;
    private static Scanner scanner;
    private static boolean running;

    public static void main(String[] args) {
        loginManager = new LoginManager();
        scanner = new Scanner(System.in);
        running = true;

        showWelcome();

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            handleChoice(choice);
        }

        scanner.close();
        System.out.println("\n👋 Até logo!");
    }

    private static void showWelcome() {
        System.out.println("\n" +
                "╔════════════════════════════════════════╗\n" +
                "║   🔐 SISTEMA DE LOGIN SEGURO          ║\n" +
                "║   Com controle de força bruta          ║\n" +
                "╚════════════════════════════════════════╝\n");
    }

    private static void showMenu() {
        System.out.println("\n📌 Menu Principal:");
        System.out.println("1. Cadastro");
        System.out.println("2. Login");
        System.out.println("3. Ver informações do usuário");
        System.out.println("4. Status do sistema");
        System.out.println("5. Sair");
        System.out.print("\nEscolha uma opção (1-5): ");
    }

    private static void handleChoice(String choice) {
        switch (choice) {
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            case "3":
                showUserInfo();
                break;
            case "4":
                showSystemStatus();
                break;
            case "5":
                running = false;
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    private static void register() {
        System.out.println("\n===== 📝 CADASTRO DE USUÁRIO =====");

        System.out.print("Nome de usuário: ");
        String username = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha (mín. 6 caracteres): ");
        String password = scanner.nextLine();

        System.out.print("Confirme a senha: ");
        String passwordConfirm = scanner.nextLine();

        if (!password.equals(passwordConfirm)) {
            System.out.println("❌ As senhas não coincidem!");
            return;
        }

        if (loginManager.register(username, email, password)) {
            System.out.println("✅ Cadastro realizado com sucesso!");
        } else {
            System.out.println("❌ Erro no cadastro. Tente novamente.");
        }
    }

    private static void login() {
        System.out.println("\n===== 🔑 FAZER LOGIN =====");

        System.out.print("Nome de usuário: ");
        String username = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        if (loginManager.login(username, password)) {
            System.out.println("\n🎉 Bem-vindo, " + username + "!");
            loginManager.showUserInfo(username);
        }
    }

    private static void showUserInfo() {
        System.out.print("\nDigite o nome de usuário: ");
        String username = scanner.nextLine().trim();
        loginManager.showUserInfo(username);
    }

    private static void showSystemStatus() {
        System.out.println("\n📊 Status do Sistema:");
        System.out.println("Total de usuários cadastrados: " + loginManager.getUserCount());
        System.out.println("Arquivo de logs: login_logs.txt");
        System.out.println("Status: ✅ Operacional");
    }
}
