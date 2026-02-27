import java.util.Scanner;

/**
 * Simulador de Cofre Digital
 * Projeto 2 - Nível: Iniciante → Intermediário
 *
 * Funcionalidades:
 * ✓ Criar senha
 * ✓ Validar força
 * ✓ Mostrar nível (Fraca / Média / Forte)
 * ✓ Impedir padrões óbvios
 * ✓ Calcular entropia
 * ✓ Fornecer dicas
 *
 * Como executar:
 * javac PasswordValidator.java SafeSimulator.java
 * java SafeSimulator
 */
public class SafeSimulator {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        boolean running = true;

        showWelcome();

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    testPassword();
                    break;
                case "2":
                    showSecurityTips();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        }

        scanner.close();
        System.out.println("\n👋 Até logo!");
    }

    private static void showWelcome() {
        System.out.println("\n" +
                "╔════════════════════════════════════════╗\n" +
                "║   🔐 SIMULADOR DE COFRE DIGITAL       ║\n" +
                "║   Análise de Força de Senha            ║\n" +
                "╚════════════════════════════════════════╝\n");
    }

    private static void showMenu() {
        System.out.println("\n📌 Menu:");
        System.out.println("1. Testar força da senha");
        System.out.println("2. Dicas de segurança");
        System.out.println("3. Sair");
        System.out.print("\nEscolha (1-3): ");
    }

    private static void testPassword() {
        System.out.print("\nDigite sua senha: ");
        String password = scanner.nextLine();

        System.out.println("\n" +
                "════════════════════════════════════════\n" +
                "📊 ANÁLISE DE FORÇA DA SENHA\n" +
                "════════════════════════════════════════\n");

        // Validar força
        PasswordValidator.PasswordStrength strength =
                PasswordValidator.validatePassword(password);
        System.out.println("Nível: " + strength.getDisplay());

        // Comprimento
        System.out.println("Comprimento: " + password.length() + " caracteres");

        // Análise de caracteres
        System.out.println("\n🔍 Análise de Caracteres:");
        System.out.println("  • Maiúsculas: " +
                (password.matches(".*[A-Z].*") ? "✅ Sim" : "❌ Não"));
        System.out.println("  • Minúsculas: " +
                (password.matches(".*[a-z].*") ? "✅ Sim" : "❌ Não"));
        System.out.println("  • Números: " +
                (password.matches(".*\\d.*") ? "✅ Sim" : "❌ Não"));
        System.out.println("  • Símbolos: " +
                (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\].*") ?
                        "✅ Sim" : "❌ Não"));

        // Entropia
        double entropy = PasswordValidator.calculateEntropy(password);
        System.out.println("\n🔑 Entropia: " + String.format("%.2f", entropy) + " bits");
        System.out.println("  (Recomendado: >50 bits para boa segurança)");

        // Dicas
        System.out.println("\n💡 Dicas de Melhoria:");
        String tips = PasswordValidator.getTips(password);
        System.out.println(tips);

        // Aviso de padrões óbvios
        if (password.contains("123") || password.contains("abc") ||
            password.contains("password") || password.contains("qwerty")) {
            System.out.println("\n⚠️  AVISO: Sua senha contém padrões óbvios!");
            System.out.println("  Evite sequências comuns como '123', 'abc', 'password'");
        }
    }

    private static void showSecurityTips() {
        System.out.println("\n" +
                "════════════════════════════════════════\n" +
                "🛡️  DICAS DE SEGURANÇA\n" +
                "════════════════════════════════════════\n");

        System.out.println("✅ FAÇA:");
        System.out.println("  1. Use pelo menos 12 caracteres");
        System.out.println("  2. Misture letras, números e símbolos");
        System.out.println("  3. Use MAIÚSCULAS e minúsculas");
        System.out.println("  4. Use uma frase única e pessoal");
        System.out.println("  5. Altere suas senhas regularmente");

        System.out.println("\n❌ NÃO FAÇA:");
        System.out.println("  1. Não use datas de nascimento");
        System.out.println("  2. Não use nomes de pessoas");
        System.out.println("  3. Não use sequências (123, abc, qwerty)");
        System.out.println("  4. Não reutilize senhas em vários sites");
        System.out.println("  5. Não compartilhe sua senha");

        System.out.println("\n📌 EXEMPLO DE BOA SENHA:");
        System.out.println("  ✨ MyDog@2026_Coffee#Secure");
        PasswordValidator.PasswordStrength ex =
                PasswordValidator.validatePassword("MyDog@2026_Coffee#Secure");
        System.out.println("  ↳ Classificação: " + ex.getDisplay());
        double exEntropy = PasswordValidator.calculateEntropy("MyDog@2026_Coffee#Secure");
        System.out.println("  ↳ Entropia: " + String.format("%.2f", exEntropy) + " bits");
    }
}
