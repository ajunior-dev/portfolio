/**
 * Simulador de Ataque de Força Bruta
 * Projeto 5 - Educacional
 */

class BruteForceSimulator {
    constructor() {
        this.passwordLength = 8;
        this.includeNumbers = true;
        this.includeSymbols = false;
        this.isRunning = false;

        this.setupEventListeners();
    }

    setupEventListeners() {
        document.getElementById('passwordLength').addEventListener('change', (e) => {
            this.passwordLength = parseInt(e.target.value);
            this.updateStatistics();
        });

        document.getElementById('includeNumbers').addEventListener('change', (e) => {
            this.includeNumbers = e.target.checked;
            this.updateStatistics();
        });

        document.getElementById('includeSymbols').addEventListener('change', (e) => {
            this.includeSymbols = e.target.checked;
            this.updateStatistics();
        });

        document.getElementById('startBtn').addEventListener('click', () => {
            this.startSimulation();
        });

        this.updateStatistics();
    }

    /**
     * Calcula o conjunto de caracteres baseado nas opções
     */
    getCharset() {
        let charset = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        if (this.includeNumbers) charset += '0123456789';
        // a string literal containing backslash and single quote must escape them
        if (this.includeSymbols) {
            charset += '!@#$%^&*()_+-=[]{};\\\':",./<>?';
        }
        return charset;
    }

    /**
     * Atualiza estatísticas
     */
    updateStatistics() {
        const charset = this.getCharset();
        const charsetSize = charset.length;
        const combinations = Math.pow(charsetSize, this.passwordLength);

        // Tentativas por segundo
        const attemptsPerSecond = 1000;
        const averageAttempts = combinations / 2; // em média, acha na metade
        const averageSeconds = Math.ceil(averageAttempts / attemptsPerSecond);

        document.getElementById('charsetSize').textContent = charsetSize;
        document.getElementById('combinations').textContent =
            combinations.toLocaleString('pt-BR');
        document.getElementById('averageTime').textContent =
            this.formatTime(averageSeconds);
        document.getElementById('worstCase').textContent =
            this.formatTime(Math.ceil(combinations / attemptsPerSecond));
    }

    /**
     * Inicia simulação
     */
    startSimulation() {
        if (this.isRunning) return;

        // reset UI elements before starting a new run
        document.getElementById('attempts').textContent = '0';
        document.getElementById('progressBar').style.width = '0%';
        document.getElementById('progressText').textContent = '0%';
        document.getElementById('currentPassword').textContent = '-';
        document.getElementById('elapsedTime').textContent = '0s';

        this.isRunning = true;
        document.getElementById('startBtn').disabled = true;
        // disable the controls while simulation is running to avoid inconsistent state
        document.getElementById('passwordLength').disabled = true;
        document.getElementById('includeNumbers').disabled = true;
        document.getElementById('includeSymbols').disabled = true;

        const charset = this.getCharset();
        const targetPassword = this.generateRandomPassword(charset);

        document.getElementById('simulationSection').classList.remove('hidden');
        document.getElementById('successMessage').classList.add('hidden');

        this.runBruteForce(targetPassword, charset);
    }

    /**
     * Gera senha aleatória
     */
    generateRandomPassword(charset) {
        let password = '';
        for (let i = 0; i < this.passwordLength; i++) {
            password += charset.charAt(Math.floor(Math.random() * charset.length));
        }
        return password;
    }

    /**
     * Executa força bruta
     */
    runBruteForce(targetPassword, charset) {
        let attempts = 0;
        let found = false;
        const startTime = Date.now();
        let lastUpdateTime = startTime;
        const totalCombos = Math.pow(charset.length, this.passwordLength);

        const bruteForce = () => {
            if (found) return;

            const currentTime = Date.now();
            attempts++;

            // Atualizar UI a cada 100ms para não travcar
            if (currentTime - lastUpdateTime > 100) {
                const progress = (attempts / totalCombos) * 100;
                const elapsedSeconds = Math.floor((currentTime - startTime) / 1000);

                document.getElementById('attempts').textContent = attempts.toLocaleString('pt-BR');
                document.getElementById('progressBar').style.width = Math.min(progress, 100) + '%';
                document.getElementById('progressText').textContent = Math.floor(progress) + '%';
                document.getElementById('elapsedTime').textContent = elapsedSeconds + 's';

                lastUpdateTime = currentTime;
            }

            // Gerar próxima tentativa
            const attempt = this.indexToPassword(attempts - 1, charset);
            document.getElementById('currentPassword').textContent = attempt;

            // Verificar se encontrou
            if (attempt === targetPassword) {
                found = true;
                this.showSuccess(attempts, startTime);
                return;
            }

            // caso atinja todas as combinações e não encontre (não deveria acontecer)
            if (attempts >= totalCombos) {
                found = true;
                this.showFailure(attempts, startTime);
                return;
            }

            // Continuar iteração
            requestAnimationFrame(bruteForce);
        };

        bruteForce();
    }

    /**
     * Converte índice para string de senha
     * Base conversion para gerar combinações
     */
    indexToPassword(index, charset) {
        let password = '';
        const base = charset.length;

        for (let i = 0; i < this.passwordLength; i++) {
            password = charset.charAt(index % base) + password;
            index = Math.floor(index / base);
        }

        return password;
    }

    /**
     * Mostra sucesso
     */
    showSuccess(attempts, startTime) {
        const endTime = Date.now();
        const elapsedSeconds = ((endTime - startTime) / 1000).toFixed(2);

        document.getElementById('finalAttempts').textContent = attempts.toLocaleString('pt-BR');
        document.getElementById('finalTime').textContent = elapsedSeconds + 's';
        document.getElementById('successMessage').classList.remove('hidden');

        // reabilitar controles
        document.getElementById('startBtn').disabled = false;
        document.getElementById('passwordLength').disabled = false;
        document.getElementById('includeNumbers').disabled = false;
        document.getElementById('includeSymbols').disabled = false;
        this.isRunning = false;
    }

    showFailure(attempts, startTime) {
        const endTime = Date.now();
        const elapsedSeconds = ((endTime - startTime) / 1000).toFixed(2);

        document.getElementById('finalAttempts').textContent = attempts.toLocaleString('pt-BR');
        document.getElementById('finalTime').textContent = elapsedSeconds + 's';
        const msg = document.getElementById('successMessage');
        msg.querySelector('h3').textContent = '❌ Sem senha encontrada';
        msg.classList.remove('hidden');

        document.getElementById('startBtn').disabled = false;
        document.getElementById('passwordLength').disabled = false;
        document.getElementById('includeNumbers').disabled = false;
        document.getElementById('includeSymbols').disabled = false;
        this.isRunning = false;
    }

    /**
     * Formata tempo em formato legível
     */
    formatTime(seconds) {
        if (seconds < 60) return seconds + 's';
        if (seconds < 3600) return (seconds / 60).toFixed(1) + 'min';
        if (seconds < 86400) return (seconds / 3600).toFixed(1) + 'h';
        // garantir que não retornamos "Infinitydias" se o valor for muito grande
        if (!isFinite(seconds)) return 'muito longo';
        return (seconds / 86400).toFixed(1) + 'dias';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new BruteForceSimulator();
});
