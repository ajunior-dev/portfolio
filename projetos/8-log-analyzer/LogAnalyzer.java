import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

/**
 * Analisador de Logs
 * Projeto 8 - Análise de logs de segurança
 *
 * Funcionalidades:
 * ✓ Lê arquivo de log
 * ✓ Conta tentativas de login
 * ✓ Identifica IPs suspeitos
 * ✓ Detecta ataques de brute force
 * ✓ Gera relatório
 *
 * Como executar:
 * javac LogAnalyzer.java
 * java LogAnalyzer
 */
public class LogAnalyzer {
    private List<LogEntry> logs;
    private static final String LOG_FILE = "sample-logs.txt";

    public LogAnalyzer() {
        this.logs = new ArrayList<>();
    }

    public static void main(String[] args) {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.run();
    }

    private void run() {
        showWelcome();

        // Criar arquivo de amostra
        createSampleLogs();

        // Carregar logs
        if (loadLogs()) {
            analyzeAndReport();
        } else {
            System.out.println("❌ Erro ao carregar arquivo de logs.");
        }
    }

    /**
     * Carrega os logs do arquivo
     */
    private boolean loadLogs() {
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry entry = parseLogLine(line);
                if (entry != null) {
                    logs.add(entry);
                }
            }
            System.out.println("✅ Carregados " + logs.size() + " logs");
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("❌ Arquivo não encontrado: " + LOG_FILE);
            return false;
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler arquivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Parse de uma linha de log
     * Formato: [TIMESTAMP] [TIPO] [IP] [USUARIO] [MENSAGEM]
     */
    private LogEntry parseLogLine(String line) {
        Pattern pattern = Pattern.compile(
            "\\[(.*?)\\]\\s\\[(.*?)\\]\\s\\[(.*?)\\]\\s\\[(.*?)\\]\\s(.*)"
        );
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return new LogEntry(
                matcher.group(1), // timestamp
                matcher.group(2), // tipo
                matcher.group(3), // ip
                matcher.group(4), // usuario
                matcher.group(5)  // mensagem
            );
        }
        return null;
    }

    /**
     * Analisa logs e gera relatório
     */
    private void analyzeAndReport() {
        System.out.println("\n" +
                "════════════════════════════════════════\n" +
                "📊 RELATÓRIO DE ANÁLISE DE LOGS\n" +
                "════════════════════════════════════════\n");

        // Estatísticas gerais
        showGeneralStats();

        // Falhas de login
        showFailedLogins();

        // IPs suspeitos
        showSuspiciousIPs();

        // Ataques detectados
        showAttackDetection();

        // Usuários com problemas
        showProblematicUsers();
    }

    /**
     * Exibe estatísticas gerais
     */
    private void showGeneralStats() {
        long successLogins = logs.stream()
            .filter(log -> "SUCESSO".equals(log.tipo))
            .count();

        long failedLogins = logs.stream()
            .filter(log -> "FALHA".equals(log.tipo))
            .count();

        long bloqueados = logs.stream()
            .filter(log -> "BLOQUEADO".equals(log.tipo))
            .count();

        System.out.println("📌 Estatísticas Gerais:");
        System.out.printf("  Total de eventos: %d\n", logs.size());
        System.out.printf("  ✅ Logins bem-sucedidos: %d\n", successLogins);
        System.out.printf("  ❌ Falhas de login: %d\n", failedLogins);
        System.out.printf("  ⛔ Bloqueados: %d\n", bloqueados);
        System.out.printf("  Taxa de falha: %.2f%%\n",
            (failedLogins * 100.0 / (successLogins + failedLogins)));
        System.out.println();
    }

    /**
     * Exibe falhas de login
     */
    private void showFailedLogins() {
        System.out.println("📌 Últimas Falhas de Login:");
        logs.stream()
            .filter(log -> "FALHA".equals(log.tipo))
            .limit(5)
            .forEach(log -> {
                System.out.printf("  [%s] %s @ %s - %s\n",
                    log.timestamp, log.usuario, log.ip, log.mensagem);
            });
        System.out.println();
    }

    /**
     * Identifica IPs suspeitos (múltiplas tentativas falhas)
     */
    private void showSuspiciousIPs() {
        Map<String, Long> ipFailures = new HashMap<>();

        logs.stream()
            .filter(log -> "FALHA".equals(log.tipo) || "BLOQUEADO".equals(log.tipo))
            .forEach(log -> {
                ipFailures.put(log.ip, ipFailures.getOrDefault(log.ip, 0L) + 1);
            });

        System.out.println("🚨 IPs Suspeitos (>3 tentativas falhas):");
        ipFailures.entrySet().stream()
            .filter(e -> e.getValue() > 3)
            .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
            .forEach(e -> {
                System.out.printf("  🔴 %s: %d tentativas falhas\n", e.getKey(), e.getValue());
            });

        if (ipFailures.values().stream().noneMatch(v -> v > 3)) {
            System.out.println("  ✅ Nenhum IP suspeito identificado");
        }
        System.out.println();
    }

    /**
     * Detecta possível ataque de brute force
     */
    private void showAttackDetection() {
        System.out.println("🔍 Detecção de Brute Force:");

        // Contar falhas por IP em janela de tempo
        Map<String, Integer> bruteForceAttempts = new HashMap<>();

        for (LogEntry log : logs) {
            if ("FALHA".equals(log.tipo)) {
                bruteForceAttempts.put(log.ip,
                    bruteForceAttempts.getOrDefault(log.ip, 0) + 1);
            }
        }

        // Limiar para brute force: >5 tentativas
        boolean hasAttack = bruteForceAttempts.values().stream()
            .anyMatch(v -> v > 5);

        if (hasAttack) {
            System.out.println("  ⚠️  POSSÍVEL ATAQUE DETECTADO!");
            bruteForceAttempts.entrySet().stream()
                .filter(e -> e.getValue() > 5)
                .forEach(e -> {
                    System.out.printf("    IP: %s (%d tentativas)\n", e.getKey(), e.getValue());
                });
        } else {
            System.out.println("  ✅ Nenhum ataque detectado");
        }
        System.out.println();
    }

    /**
     * Exibe usuários com problemas
     */
    private void showProblematicUsers() {
        Map<String, Long> userFailures = new HashMap<>();

        logs.stream()
            .filter(log -> "FALHA".equals(log.tipo))
            .forEach(log -> {
                userFailures.put(log.usuario,
                    userFailures.getOrDefault(log.usuario, 0L) + 1);
            });

        System.out.println("👤 Usuários com Múltiplas Falhas:");
        userFailures.entrySet().stream()
            .filter(e -> e.getValue() > 2)
            .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
            .limit(5)
            .forEach(e -> {
                System.out.printf("  ⚠️  %s: %d falhas\n", e.getKey(), e.getValue());
            });
        System.out.println();
    }

    /**
     * Cria arquivo de amostra com logs realistas
     */
    private void createSampleLogs() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE))) {
            String[] tipos = {"SUCESSO", "FALHA", "BLOQUEADO"};
            String[] ips = {"192.168.1.100", "203.0.113.45", "198.51.100.200", "192.0.2.50"};
            String[] usuarios = {"admin", "user1", "user2", "guest"};

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Gerar 30 logs aleatórios
            Random rand = new Random();
            for (int i = 0; i < 30; i++) {
                String tipo = tipos[rand.nextInt(tipos.length)];
                String ip = ips[rand.nextInt(ips.length)];
                String user = usuarios[rand.nextInt(usuarios.length)];
                LocalDateTime time = LocalDateTime.now().minusHours(rand.nextInt(24));

                String msg = switch(tipo) {
                    case "SUCESSO" -> "Login bem-sucedido";
                    case "FALHA" -> "Senha incorreta";
                    default -> "Bloqueado por excesso de tentativas";
                };

                pw.printf("[%s] [%s] [%s] [%s] %s\n",
                    time.format(formatter), tipo, ip, user, msg);
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao criar arquivo de amostra");
        }
    }

    private void showWelcome() {
        System.out.println("\n" +
                "╔════════════════════════════════════════╗\n" +
                "║   📊 ANALISADOR DE LOGS                ║\n" +
                "║   Segurança e Detecção de Ataques      ║\n" +
                "╚════════════════════════════════════════╝\n");
    }

    /**
     * Classe interna para representar um entry do log
     */
    static class LogEntry {
        String timestamp;
        String tipo;
        String ip;
        String usuario;
        String mensagem;

        LogEntry(String timestamp, String tipo, String ip, String usuario, String mensagem) {
            this.timestamp = timestamp;
            this.tipo = tipo;
            this.ip = ip;
            this.usuario = usuario;
            this.mensagem = mensagem;
        }
    }
}
