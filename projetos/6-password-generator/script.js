/**
 * Gerador de Senha Forte
 * Projeto 6 - Com cálculo de entropia
 */

class PasswordGenerator {
    constructor() {
        this.lengthSlider = document.getElementById('length');
        this.lengthValue = document.getElementById('lengthValue');
        this.generateBtn = document.getElementById('generateBtn');
        this.copyBtn = document.getElementById('copyBtn');
        // value to restore after showing "copied" message
        this.copyBtnDefault = this.copyBtn.textContent;

        this.checkboxes = {
            uppercase: document.getElementById('uppercase'),
            lowercase: document.getElementById('lowercase'),
            numbers: document.getElementById('numbers'),
            symbols: document.getElementById('symbols')
        };

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.lengthSlider.addEventListener('input', (e) => {
            this.lengthValue.textContent = e.target.value;
        });

        this.generateBtn.addEventListener('click', () => this.generate());
        this.copyBtn.addEventListener('click', () => this.copyToClipboard());

        // Permitir gerar com Enter
        // use keydown because keypress is deprecated and may not fire for Enter on all elements
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Enter') {
                // avoid generating while the user is interacting with controls
                const active = document.activeElement;
                if (active === this.copyBtn || active.tagName === 'INPUT' || active.tagName === 'BUTTON') {
                    return;
                }
                this.generate();
            }
        });
    }

    /**
     * Gera uma nova senha
     */
    generate() {
        const length = parseInt(this.lengthSlider.value);
        const charset = this.getCharset();

        // hide previous result if still visible
        document.getElementById('resultSection').classList.add('hidden');
        if (charset.length === 0) {
            alert('❌ Selecione pelo menos uma opção!');
            return;
        }

        let password = '';
        for (let i = 0; i < length; i++) {
            password += charset.charAt(Math.floor(Math.random() * charset.length));
        }

        // reset copy button label if user generates multiple times in quick succession
        this.copyBtn.textContent = this.copyBtnDefault;
        this.displayPassword(password, length, charset);
    }

    /**
     * Retorna o conjunto de caracteres
     */
    getCharset() {
        let charset = '';

        if (this.checkboxes.uppercase.checked) charset += 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        if (this.checkboxes.lowercase.checked) charset += 'abcdefghijklmnopqrstuvwxyz';
        if (this.checkboxes.numbers.checked) charset += '0123456789';
        if (this.checkboxes.symbols.checked) {
            // escape backslash and single quote inside string
            charset += '!@#$%^&*()_+-=[]{};\\\':",./<>?';
        }

        return charset;
    }

    /**
     * Exibe a senha e calcula estatísticas
     */
    displayPassword(password, length, charset) {
        document.getElementById('passwordOutput').value = password;
        document.getElementById('resultSection').classList.remove('hidden');

        // Calcular estatísticas
        const charsetSize = charset.length;
        const combinations = Math.pow(charsetSize, length);
        const entropy = this.calculateEntropy(length, charsetSize);

        // Atualizar display
        document.getElementById('statLength').textContent = length;
        document.getElementById('statCharsets').textContent = this.countCharsetTypes();
        document.getElementById('statCombinations').textContent =
            !isFinite(combinations)
                ? '> 10^15 (infinito)'
                : combinations > 1e15
                    ? '> 10^15'
                    : combinations.toLocaleString('pt-BR');
        document.getElementById('statEntropy').textContent = entropy.toFixed(2) + ' bits';

        // Barra de entropia
        this.updateEntropyBar(entropy);

        // Tempo para quebrar
        this.calculateCrackTime(combinations);
    }

    /**
     * Calcula entropia da senha
     * Formula: N = L × log₂(R)
     * N = entropy (bits)
     * L = comprimento
     * R = tamanho do charset
     */
    calculateEntropy(length, charsetSize) {
        return length * (Math.log2(charsetSize));
    }

    /**
     * Conta quantos tipos de caracteres estão sendo usados
     */
    countCharsetTypes() {
        let count = 0;
        if (this.checkboxes.uppercase.checked) count++;
        if (this.checkboxes.lowercase.checked) count++;
        if (this.checkboxes.numbers.checked) count++;
        if (this.checkboxes.symbols.checked) count++;
        return count;
    }

    /**
     * Atualiza a barra visual de entropia
     */
    updateEntropyBar(entropy) {
        const fill = document.getElementById('entropyFill');
        const label = document.getElementById('entropyLabel');

        // Máximo recomendado é 128 bits
        const percentage = Math.min((entropy / 128) * 100, 100);
        fill.style.width = percentage + '%';

        // Cor baseada em entropia
        if (entropy < 32) {
            fill.style.background = 'linear-gradient(90deg, #e74c3c, #e67e22)';
            label.textContent = '⚠️ Fraca';
        } else if (entropy < 64) {
            fill.style.background = 'linear-gradient(90deg, #f39c12, #27ae60)';
            label.textContent = '🟡 Média';
        } else if (entropy < 96) {
            fill.style.background = 'linear-gradient(90deg, #27ae60, #16a085)';
            label.textContent = '✅ Forte';
        } else {
            fill.style.background = 'linear-gradient(90deg, #16a085, #0288d1)';
            label.textContent = '🔒 Muito Forte';
        }
    }

    /**
     * Calcula tempo estimado para quebrar a senha
     * Assume 1 bilhão de tentativas por segundo
     */
    calculateCrackTime(combinations) {
        const attemptsPerSecond = 1e9;
        const averageAttempts = combinations / 2;
        const seconds = averageAttempts / attemptsPerSecond;

        const crackTimeElement = document.getElementById('crackTime');
        crackTimeElement.textContent = this.formatTime(seconds);
    }

    /**
     * Formata tempo em unidade legível
     */
    formatTime(seconds) {
        if (seconds < 1) return 'Menos de 1 segundo ⚡';
        if (seconds < 60) return Math.floor(seconds) + ' segundos';
        if (seconds < 3600) return (seconds / 60).toFixed(2) + ' minutos';
        if (seconds < 86400) return (seconds / 3600).toFixed(2) + ' horas';
        if (seconds < 2592000) return (seconds / 86400).toFixed(2) + ' dias';
        if (seconds < 31536000) return (seconds / 2592000).toFixed(2) + ' meses';
        if (!isFinite(seconds)) return 'tempo infinito';
        return (seconds / 31536000).toFixed(2) + ' anos';
    }

    /**
     * Copia a senha para a área de transferência
     */
    copyToClipboard() {
        const output = document.getElementById('passwordOutput');
        const text = output.value;

        // Try modern API first
        if (navigator.clipboard && navigator.clipboard.writeText) {
            navigator.clipboard.writeText(text).catch(() => {
                // fallback if writeText fails
                output.select();
                document.execCommand('copy');
            });
        } else {
            // legacy fallback
            output.select();
            document.execCommand('copy');
        }

        // Feedback visual
        this.copyBtn.textContent = '✅ Copiado!';
        setTimeout(() => {
            this.copyBtn.textContent = this.copyBtnDefault;
        }, 2000);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new PasswordGenerator();
});
