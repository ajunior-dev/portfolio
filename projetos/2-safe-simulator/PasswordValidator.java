/**
 * Validador de força de senha
 * Analisa e classifica senhas em: Fraca, Média ou Forte
 */
public class PasswordValidator {

    /**
     * Enum para classificação de força
     */
    public enum PasswordStrength {
        FRACA(1, "🔴 Fraca"),
        MEDIA(2, "🟡 Média"),
        FORTE(3, "🟢 Forte");

        private final int level;
        private final String display;

        PasswordStrength(int level, String display) {
            this.level = level;
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }
    }

    /**
     * Valida força da senha
     * Critérios:
     * - Comprimento mínimo
     * - Maiúsculas e minúsculas
     * - Números e símbolos
     * - Padrões óbvios (123, abc, aaa, etc)
     */
    public static PasswordStrength validatePassword(String password) {
        int score = 0;

        // Verificação de nulo/vazio
        if (password == null || password.isEmpty()) {
            return PasswordStrength.FRACA;
        }

        // Comprimento
        if (password.length() >= 8) score += 1;
        if (password.length() >= 12) score += 1;
        if (password.length() >= 16) score += 1;

        // Caracteres maiúsculos
        if (password.matches(".*[A-Z].*")) score += 1;

        // Caracteres minúsculos
        if (password.matches(".*[a-z].*")) score += 1;

        // Números
        if (password.matches(".*\\d.*")) score += 1;

        // Símbolos especiais
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\].*")) score += 1;

        // Verificar padrões óbvios (penalidade)
        if (hasObviousPatterns(password)) {
            score -= 2;
        }

        // Converter score em força
        if (score <= 2) {
            return PasswordStrength.FRACA;
        } else if (score <= 4) {
            return PasswordStrength.MEDIA;
        } else {
            return PasswordStrength.FORTE;
        }
    }

    /**
     * Verifica padrões óbvios e inseguros
     */
    private static boolean hasObviousPatterns(String password) {
        // Repetição de caracteres iguais (aaa, 111, ###)
        if (password.matches("(.)\\1{2,}")) {
            return true;
        }

        // Sequências óbvias
        if (password.contains("123") || password.contains("abc") ||
            password.contains("qwerty") || password.contains("password")) {
            return true;
        }

        // Padrão de teclado (abcd, 1234)
        if (password.matches(".*[a-z]{2,}[0-9]{2,}.*") &&
            isSequential(password)) {
            return true;
        }

        return false;
    }

    /**
     * Verifica se há sequência de números ou letras
     */
    private static boolean isSequential(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            char c = password.charAt(i);
            if (password.charAt(i + 1) == (char)(c + 1) &&
                password.charAt(i + 2) == (char)(c + 2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna dicas de melhoria
     */
    public static String getTips(String password) {
        StringBuilder tips = new StringBuilder();

        if (password.length() < 8) {
            tips.append("• Aumente para pelo menos 8 caracteres\n");
        }
        if (!password.matches(".*[A-Z].*")) {
            tips.append("• Adicione letras MAIÚSCULAS\n");
        }
        if (!password.matches(".*[a-z].*")) {
            tips.append("• Adicione letras minúsculas\n");
        }
        if (!password.matches(".*\\d.*")) {
            tips.append("• Adicione NÚMEROS\n");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\].*")) {
            tips.append("• Adicione SÍMBOLOS especiais (!@#$%)\n");
        }

        return tips.length() > 0 ? tips.toString() : "✅ Senha forte!";
    }

    /**
     * Calcula entropia estimada da senha
     * Quanto maior, mais segura
     */
    public static double calculateEntropy(String password) {
        int charsetSize = 0;

        if (password.matches(".*[a-z].*")) charsetSize += 26;
        if (password.matches(".*[A-Z].*")) charsetSize += 26;
        if (password.matches(".*\\d.*")) charsetSize += 10;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\].*")) charsetSize += 32;

        if (charsetSize == 0) return 0;

        return password.length() * (Math.log(charsetSize) / Math.log(2));
    }
}
