import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Gerenciador de login com controle de tentativas e brute force
 * Implementa:
 * - Limite de 3 tentativas
 * - Bloqueio temporário de 2 minutos
 * - Registro de logs
 * - Armazenamento de usuários
 */
public class LoginManager {
    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCKOUT_TIME = 2 * 60 * 1000; // 2 minutos em ms
    private static final String LOG_FILE = "login_logs.txt";

    private Map<String, User> users;
    private Map<String, Integer> failedAttempts;
    private Map<String, Long> lockoutTimes;
    private DateTimeFormatter dateFormatter;

    public LoginManager() {
        this.users = new HashMap<>();
        this.failedAttempts = new HashMap<>();
        this.lockoutTimes = new HashMap<>();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Registra um novo usuário
     * @param username Nome do usuário
     * @param email Email
     * @param password Senha
     * @return true se cadastro bem-sucedido
     */
    public boolean register(String username, String email, String password) {
        // Validações básicas
        if (username == null || username.trim().isEmpty()) {
            log("ERRO", "Tentativa de cadastro com usuário vazio");
            return false;
        }
        if (users.containsKey(username)) {
            log("ERRO", "Tentativa de cadastro com usuário duplicado: " + username);
            return false;
        }
        if (password.length() < 6) {
            log("ERRO", "Tentativa de cadastro com senha fraca: " + username);
            return false;
        }

        User newUser = new User(username, email, password);
        users.put(username, newUser);
        log("SUCESSO", "Novo usuário cadastrado: " + username);
        return true;
    }

    /**
     * Tenta fazer login
     * @param username Nome do usuário
     * @param password Senha
     * @return true se login bem-sucedido
     */
    public boolean login(String username, String password) {
        // Verificar se está bloqueado
        if (isLockedOut(username)) {
            log("BLOQUEADO", "Tentativa de acesso a usuário bloqueado: " + username);
            System.out.println("⛔ Usuário bloqueado. Tente novamente em 2 minutos.");
            return false;
        }

        // Verificar se usuário existe
        if (!users.containsKey(username)) {
            incrementFailedAttempts(username);
            log("FALHA", "Tentativa de login com usuário inexistente: " + username);
            return false;
        }

        User user = users.get(username);

        // Verificar senha
        if (user.verifyPassword(password)) {
            // Reset de tentativas
            failedAttempts.remove(username);
            log("SUCESSO", "Login bem-sucedido: " + username);
            System.out.println("✅ Login realizado com sucesso!");
            return true;
        } else {
            incrementFailedAttempts(username);
            log("FALHA", "Senha incorreta para: " + username);
            return false;
        }
    }

    /**
     * Incrementa contador de tentativas falhadas
     * Se atingir limite, bloqueia o usuário
     */
    private void incrementFailedAttempts(String username) {
        int attempts = failedAttempts.getOrDefault(username, 0) + 1;
        failedAttempts.put(username, attempts);

        System.out.println("❌ Falha no login. Tentativas: " + attempts + "/" + MAX_ATTEMPTS);

        if (attempts >= MAX_ATTEMPTS) {
            lockoutTimes.put(username, System.currentTimeMillis());
            log("BLOQUEIO", "Usuário bloqueado por excesso de tentativas: " + username);
            System.out.println("⛔ Muitas tentativas. Acesso bloqueado por 2 minutos!");
        }
    }

    /**
     * Verifica se um usuário está bloqueado
     */
    private boolean isLockedOut(String username) {
        if (!lockoutTimes.containsKey(username)) {
            return false;
        }

        long lockoutTime = lockoutTimes.get(username);
        long currentTime = System.currentTimeMillis();

        if (currentTime - lockoutTime > LOCKOUT_TIME) {
            // Desbloqueio após timeout
            lockoutTimes.remove(username);
            failedAttempts.remove(username);
            return false;
        }
        return true;
    }

    /**
     * Registra eventos no log
     */
    private void log(String tipo, String mensagem) {
        String timestamp = LocalDateTime.now().format(dateFormatter);
        String logEntry = String.format("[%s] %s - %s", timestamp, tipo, mensagem);

        // Imprimir no console
        System.out.println(logEntry);

        // Salvar em arquivo
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(logEntry);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no log: " + e.getMessage());
        }
    }

    /**
     * Exibe informações da conta
     */
    public void showUserInfo(String username) {
        if (users.containsKey(username)) {
            System.out.println("📋 " + users.get(username));
        } else {
            System.out.println("Usuário não encontrado");
        }
    }

    /**
     * Retorna total de usuários cadastrados
     */
    public int getUserCount() {
        return users.size();
    }
}
