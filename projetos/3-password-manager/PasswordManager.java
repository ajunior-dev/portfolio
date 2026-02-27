import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Gerenciador de Senhas Local
 * Projeto 3 - Nível: Intermediário
 *
 * Funcionalidades:
 * ✓ Armazenar senhas em arquivo
 * ✓ Hash SHA-256
 * ✓ Listar senhas
 * ✓ Buscar por serviço
 * ✓ Adicionar/Remover entradas
 *
 * Como executar:
 * javac PasswordEntry.java PasswordManager.java
 * java PasswordManager
 */
public class PasswordManager {
    private static final String PASSWORD_FILE = "passwords.dat";
    private static final String CSV_FILE = "passwords_backup.csv";

    private List<PasswordEntry> passwords;
    private Scanner scanner;

    public PasswordManager() {
        this.passwords = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        loadPasswords();
    }

    public static void main(String[] args) {
        PasswordManager manager = new PasswordManager();
        manager.run();
    }

    private void run() {
        showWelcome();

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addPassword();
                    break;
                case "2":
                    listPasswords();
                    break;
                case "3":
                    searchPassword();
                    break;
                case "4":
                    deletePassword();
                    break;
                case "5":
                    exportToCSV();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        }

        savePasswords();
        scanner.close();
        System.out.println("\n👋 Até logo!");
    }

    private void showWelcome() {
        System.out.println("\n" +
                "╔════════════════════════════════════════╗\n" +
                "║   🔐 GERENCIADOR DE SENHAS             ║\n" +
                "║   Armazenamento local seguro           ║\n" +
                "╚════════════════════════════════════════╝\n");
    }

    private void showMenu() {
        System.out.println("\n📌 Menu:");
        System.out.println("1. Adicionar senha");
        System.out.println("2. Listar todas as senhas");
        System.out.println("3. Buscar senha");
        System.out.println("4. Deletar senha");
        System.out.println("5. Exportar para CSV");
        System.out.println("6. Sair");
        System.out.print("\nEscolha (1-6): ");
    }

    private void addPassword() {
        System.out.println("\n===== ➕ ADICIONAR SENHA =====");

        System.out.print("Serviço/Site: ");
        String service = scanner.nextLine().trim();

        System.out.print("Usuário/Email: ");
        String username = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        PasswordEntry entry = new PasswordEntry(service, username, password);
        passwords.add(entry);

        System.out.println("✅ Senha adicionada com sucesso!");
    }

    private void listPasswords() {
        if (passwords.isEmpty()) {
            System.out.println("\n❌ Nenhuma senha armazenada.");
            return;
        }

        System.out.println("\n" +
                "════════════════════════════════════════\n" +
                "📋 SUAS SENHAS\n" +
                "════════════════════════════════════════\n");

        for (int i = 0; i < passwords.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, passwords.get(i));
        }
    }

    private void searchPassword() {
        System.out.print("\nBuscar por serviço: ");
        String search = scanner.nextLine().trim().toLowerCase();

        List<PasswordEntry> results = new ArrayList<>();
        for (PasswordEntry entry : passwords) {
            if (entry.getService().toLowerCase().contains(search)) {
                results.add(entry);
            }
        }

        if (results.isEmpty()) {
            System.out.println("❌ Nenhuma senha encontrada para: " + search);
        } else {
            System.out.println("\n🔍 Resultados da busca:");
            for (PasswordEntry entry : results) {
                System.out.println("  " + entry);
            }
        }
    }

    private void deletePassword() {
        listPasswords();

        if (passwords.isEmpty()) return;

        System.out.print("\nDigite o número da senha a deletar: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < passwords.size()) {
                PasswordEntry removed = passwords.remove(index);
                System.out.println("✅ Senha de " + removed.getService() + " removida!");
            } else {
                System.out.println("❌ Índice inválido!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida!");
        }
    }

    private void exportToCSV() {
        try (FileWriter fw = new FileWriter(CSV_FILE);
             BufferedWriter bw = new BufferedWriter(fw)) {

            // Cabeçalho
            bw.write("Serviço,Usuário,Data Criação\n");

            // Dados
            for (PasswordEntry entry : passwords) {
                bw.write(String.format("%s,%s,%s\n",
                        entry.getService(),
                        entry.getUsername(),
                        entry.getCreatedAt()));
            }

            System.out.println("✅ Exportado para: " + CSV_FILE);
        } catch (IOException e) {
            System.out.println("❌ Erro ao exportar: " + e.getMessage());
        }
    }

    private void savePasswords() {
        try (FileOutputStream fos = new FileOutputStream(PASSWORD_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(passwords);
            System.out.println("💾 Senhas salvas com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadPasswords() {
        File file = new File(PASSWORD_FILE);
        if (!file.exists()) {
            System.out.println("📝 Novo arquivo de senhas criado.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(PASSWORD_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            passwords = (List<PasswordEntry>) ois.readObject();
            System.out.println("✅ Senhas carregadas!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️  Não foi possível carregar senhas: " + e.getMessage());
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao fazer hash", e);
        }
    }
}
