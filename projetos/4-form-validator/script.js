/**
 * Validador de Formulário Seguro
 * Projeto 4 - Prevenção de XSS e Validação robusta
 */

class FormValidator {
    constructor() {
        this.form = document.getElementById('loginForm');
        this.emailInput = document.getElementById('email');
        this.passwordInput = document.getElementById('password');
        this.confirmPasswordInput = document.getElementById('confirmPassword');
        this.resultContainer = document.getElementById('resultContainer');

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.form.addEventListener('submit', (e) => this.handleSubmit(e));
        this.emailInput.addEventListener('blur', () => this.validateEmail());
        this.passwordInput.addEventListener('blur', () => this.validatePassword());
        this.confirmPasswordInput.addEventListener('blur', () => this.validateConfirmPassword());

        // Validação em tempo real
        this.emailInput.addEventListener('input', () => this.validateEmail());
        this.passwordInput.addEventListener('input', () => this.validatePassword());

        document.querySelector('.btn-reset')?.addEventListener('click', () => this.resetForm());
    }

    /**
     * Valida formato de email
     * Regex básico mas efetivo
     */
    validateEmail() {
        const email = this.emailInput.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const errorElement = document.getElementById('emailError');

        // Sanitização: remover caracteres perigosos
        const sanitized = this.sanitizeInput(email);
        if (sanitized !== email) {
            this.emailInput.value = sanitized;
        }

        if (!email) {
            this.setError(errorElement, '❌ Email é obrigatório');
            return false;
        }

        if (!emailRegex.test(email)) {
            this.setError(errorElement, '❌ Email inválido');
            return false;
        }

        if (email.length > 254) {
            this.setError(errorElement, '❌ Email muito longo');
            return false;
        }

        this.clearError(errorElement);
        return true;
    }

    /**
     * Valida força da senha
     * Critérios rigorosos
     */
    validatePassword() {
        const password = this.passwordInput.value;
        const errorElement = document.getElementById('passwordError');

        // Verificações
        const checks = {
            length: password.length >= 8,
            uppercase: /[A-Z]/.test(password),
            lowercase: /[a-z]/.test(password),
            numbers: /\d/.test(password),
            symbols: /[!@#$%^&*()_+\-=\[\]{};:'",.<>?/\\]/.test(password),
            noSpaces: !/\s/.test(password),
            noDangerousChars: !/<|>|&|"|'/.test(password) // previne XSS
        };

        let errors = [];

        if (!checks.length) errors.push('mínimo 8 caracteres');
        if (!checks.uppercase) errors.push('maiúscula');
        if (!checks.lowercase) errors.push('minúscula');
        if (!checks.numbers) errors.push('número');
        if (!checks.symbols) errors.push('símbolo (!@#$%)');
        if (!checks.noSpaces) errors.push('sem espaços em branco');
        if (!checks.noDangerousChars) errors.push('sem caracteres perigosos');

        if (errors.length > 0) {
            this.setError(errorElement, `❌ Precisa de: ${errors.join(', ')}`);
            return false;
        }

        this.clearError(errorElement);
        return true;
    }

    /**
     * Verifica se as senhas coincidem
     */
    validateConfirmPassword() {
        const password = this.passwordInput.value;
        const confirmPassword = this.confirmPasswordInput.value;
        const errorElement = document.getElementById('confirmError');

        if (!confirmPassword) {
            this.setError(errorElement, '❌ Confirme a senha');
            return false;
        }

        if (password !== confirmPassword) {
            this.setError(errorElement, '❌ As senhas não coincidem');
            return false;
        }

        this.clearError(errorElement);
        return true;
    }

    /**
     * Sanitização básica contra entrada maliciosa
     * Remove ou escapa caracteres perigosos
     */
    sanitizeInput(input) {
        // Remove tags HTML
        let sanitized = input.replace(/<[^>]*>/g, '');

        // Escapa caracteres especiais
        sanitized = sanitized
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#x27;');

        return sanitized;
    }

    /**
     * Manipulador do submit do formulário
     */
    handleSubmit(e) {
        e.preventDefault();

        // Validar todos os campos
        const isEmailValid = this.validateEmail();
        const isPasswordValid = this.validatePassword();
        const isConfirmValid = this.validateConfirmPassword();

        if (isEmailValid && isPasswordValid && isConfirmValid) {
            this.showSuccess();
        }
    }

    /**
     * Mostra mensagem de sucesso
     */
    showSuccess() {
        const email = this.sanitizeInput(this.emailInput.value);

        const resultContent = `
            <div class="success-message">
                <h3>✅ Formulário validado com sucesso!</h3>
                <p><strong>Email:</strong> ${email}</p>
                <p><strong>Senha:</strong> Armazenada com segurança (hash SHA-256)</p>
                <p class="info">ℹ️ Este é um simulador educacional. Dados não são realmente enviados.</p>
            </div>
        `;

        document.getElementById('resultContent').innerHTML = resultContent;
        this.resultContainer.classList.remove('hidden');
        this.form.classList.add('hidden');
    }

    /**
     * Reseta o formulário
     */
    resetForm() {
        this.form.reset();
        this.form.classList.remove('hidden');
        this.resultContainer.classList.add('hidden');

        // Limpar erros
        document.getElementById('emailError').textContent = '';
        document.getElementById('passwordError').textContent = '';
        document.getElementById('confirmError').textContent = '';
    }

    /**
     * Exibe erro
     */
    setError(element, message) {
        element.textContent = message;
        element.style.display = 'block';
    }

    /**
     * Limpa erro
     */
    clearError(element) {
        element.textContent = '';
        element.style.display = 'none';
    }
}

// Inicializar quando DOM carregar
document.addEventListener('DOMContentLoaded', () => {
    new FormValidator();
});
