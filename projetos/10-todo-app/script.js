/**
 * TODO Manager Pro - Professional Task Management Application
 * Features: LocalStorage persistence, filtering, sorting, import/export
 */

class TodoManager {
    constructor() {
        this.tasks = [];
        this.currentFilter = 'todas';
        this.currentSort = 'data-desc';
        this.STORAGE_KEY = 'todoManagerPro_tasks';
        
        this.init();
    }

    /**
     * Initialize the application and set up event listeners
     */
    init() {
        this.loadFromStorage();
        this.setupEventListeners();
        this.render();
    }

    /**
     * Setup all event listeners for the application
     */
    setupEventListeners() {
        // Input section
        const addBtn = document.getElementById('addBtn');
        const taskInput = document.getElementById('taskInput');
        
        addBtn.addEventListener('click', () => this.addTask());
        taskInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') this.addTask();
        });

        // Filter buttons
        document.querySelectorAll('.filter-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                this.currentFilter = e.target.dataset.filter;
                this.render();
            });
        });

        // Sort select
        document.getElementById('sortSelect').addEventListener('change', (e) => {
            this.currentSort = e.target.value;
            this.render();
        });

        // Action buttons
        document.getElementById('clearCompletedBtn').addEventListener('click', () => this.clearCompleted());
        document.getElementById('exportBtn').addEventListener('click', () => this.exportJSON());
        document.getElementById('importBtn').addEventListener('click', () => {
            document.getElementById('fileInput').click();
        });
        document.getElementById('fileInput').addEventListener('change', (e) => this.importJSON(e));
        document.getElementById('clearAllBtn').addEventListener('click', () => this.clearAll());
    }

    /**
     * Add a new task
     */
    addTask() {
        const input = document.getElementById('taskInput');
        const text = input.value.trim();
        const priority = document.getElementById('prioritySelect').value;
        const errorDiv = document.getElementById('inputError');

        // Validation
        if (!text) {
            errorDiv.textContent = 'Por favor, digite uma tarefa.';
            return;
        }

        if (text.length > 200) {
            errorDiv.textContent = 'Tarefa muito longa (máximo 200 caracteres).';
            return;
        }

        // Sanitize input to prevent XSS
        const sanitized = this.sanitizeInput(text);

        // Create task object
        const task = {
            id: Date.now(),
            text: sanitized,
            completed: false,
            priority: priority,
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString()
        };

        this.tasks.push(task);
        this.saveToStorage();
        input.value = '';
        errorDiv.textContent = '';
        this.render();
    }

    /**
     * Sanitize user input to prevent XSS attacks
     */
    sanitizeInput(input) {
        const div = document.createElement('div');
        div.textContent = input;
        return div.innerHTML;
    }

    /**
     * Toggle task completion status
     */
    toggleTask(id) {
        const task = this.tasks.find(t => t.id === id);
        if (task) {
            task.completed = !task.completed;
            task.updatedAt = new Date().toISOString();
            this.saveToStorage();
            this.render();
        }
    }

    /**
     * Delete a task
     */
    deleteTask(id) {
        if (confirm('Tem certeza que deseja deletar esta tarefa?')) {
            this.tasks = this.tasks.filter(t => t.id !== id);
            this.saveToStorage();
            this.render();
        }
    }

    /**
     * Edit a task (simple implementation)
     */
    editTask(id) {
        const task = this.tasks.find(t => t.id === id);
        if (task) {
            const newText = prompt('Editar tarefa:', task.text);
            if (newText && newText.trim()) {
                task.text = this.sanitizeInput(newText.trim());
                task.updatedAt = new Date().toISOString();
                this.saveToStorage();
                this.render();
            }
        }
    }

    /**
     * Filter tasks based on current filter
     */
    filterTasks() {
        switch (this.currentFilter) {
            case 'ativas':
                return this.tasks.filter(t => !t.completed);
            case 'completas':
                return this.tasks.filter(t => t.completed);
            case 'alta':
                return this.tasks.filter(t => t.priority === 'alta');
            case 'media':
                return this.tasks.filter(t => t.priority === 'media');
            case 'baixa':
                return this.tasks.filter(t => t.priority === 'baixa');
            default:
                return this.tasks;
        }
    }

    /**
     * Sort tasks based on current sort option
     */
    sortTasks(filteredTasks) {
        const sorted = [...filteredTasks];

        switch (this.currentSort) {
            case 'data-desc':
                sorted.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
                break;
            case 'data-asc':
                sorted.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
                break;
            case 'prioridade':
                const priorityOrder = { alta: 1, media: 2, baixa: 3 };
                sorted.sort((a, b) => priorityOrder[a.priority] - priorityOrder[b.priority]);
                break;
            case 'alfabetica':
                sorted.sort((a, b) => a.text.localeCompare(b.text, 'pt-BR'));
                break;
        }

        return sorted;
    }

    /**
     * Calculate and update statistics
     */
    updateStats() {
        const total = this.tasks.length;
        const completed = this.tasks.filter(t => t.completed).length;
        const active = total - completed;
        const highPriority = this.tasks.filter(t => t.priority === 'alta' && !t.completed).length;

        document.getElementById('totalTasks').textContent = total;
        document.getElementById('completedTasks').textContent = completed;
        document.getElementById('activeTasks').textContent = active;
        document.getElementById('highPriorityTasks').textContent = highPriority;

        // Update progress bar
        const percent = total === 0 ? 0 : Math.round((completed / total) * 100);
        document.getElementById('progressFill').style.width = percent + '%';
        document.getElementById('progressPercent').textContent = percent;
    }

    /**
     * Clear completed tasks
     */
    clearCompleted() {
        if (confirm('Tem certeza que deseja deletar todas as tarefas completas?')) {
            this.tasks = this.tasks.filter(t => !t.completed);
            this.saveToStorage();
            this.render();
        }
    }

    /**
     * Clear all tasks
     */
    clearAll() {
        if (confirm('ATENÇÃO: Isso deletará TODAS as tarefas! Tem certeza?')) {
            if (confirm('Tem CERTEZA? Esta ação não pode ser desfeita!')) {
                this.tasks = [];
                this.saveToStorage();
                this.render();
            }
        }
    }

    /**
     * Export tasks as JSON file
     */
    exportJSON() {
        const dataStr = JSON.stringify(this.tasks, null, 2);
        const dataBlob = new Blob([dataStr], { type: 'application/json' });
        const url = URL.createObjectURL(dataBlob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `tasks_${new Date().toISOString().split('T')[0]}.json`;
        link.click();
        URL.revokeObjectURL(url);
    }

    /**
     * Import tasks from JSON file
     */
    importJSON(event) {
        const file = event.target.files[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = (e) => {
            try {
                const imported = JSON.parse(e.target.result);
                if (Array.isArray(imported)) {
                    if (confirm('Isso substituirá todas as suas tarefas. Deseja continuar?')) {
                        this.tasks = imported;
                        this.saveToStorage();
                        this.render();
                        alert('Tarefas importadas com sucesso!');
                    }
                } else {
                    alert('Arquivo JSON inválido!');
                }
            } catch (error) {
                alert('Erro ao importar arquivo: ' + error.message);
            }
        };
        reader.readAsText(file);
        event.target.value = '';
    }

    /**
     * Save tasks to localStorage
     */
    saveToStorage() {
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.tasks));
    }

    /**
     * Load tasks from localStorage
     */
    loadFromStorage() {
        const stored = localStorage.getItem(this.STORAGE_KEY);
        this.tasks = stored ? JSON.parse(stored) : [];
    }

    /**
     * Format date for display
     */
    formatDate(dateString) {
        const date = new Date(dateString);
        const today = new Date();
        const isToday = date.toDateString() === today.toDateString();
        
        if (isToday) {
            return date.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
        }
        
        return date.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit', year: '2-digit' });
    }

    /**
     * Render the task list
     */
    render() {
        const tasksList = document.getElementById('tasksList');
        const filteredTasks = this.filterTasks();
        const sortedTasks = this.sortTasks(filteredTasks);

        // Update statistics
        this.updateStats();

        // Render tasks or empty state
        if (sortedTasks.length === 0) {
            tasksList.innerHTML = `
                <div class="empty-state">
                    <span class="empty-icon">📝</span>
                    <p>${this.currentFilter === 'todas' ? 'Nenhuma tarefa ainda. Crie sua primeira tarefa!' : 'Nenhuma tarefa neste filtro.'}</p>
                </div>
            `;
            return;
        }

        tasksList.innerHTML = sortedTasks.map(task => `
            <div class="task-item ${task.completed ? 'completed' : ''}">
                <input 
                    type="checkbox" 
                    class="task-checkbox" 
                    ${task.completed ? 'checked' : ''}
                    onchange="todoManager.toggleTask(${task.id})"
                >
                <div class="task-content">
                    <div class="task-text">${task.text}</div>
                    <div class="task-meta">
                        <span class="task-date">📅 ${this.formatDate(task.createdAt)}</span>
                        <span class="task-priority ${task.priority}">${task.priority.toUpperCase()}</span>
                    </div>
                </div>
                <div class="task-actions">
                    <button class="task-btn task-edit" onclick="todoManager.editTask(${task.id})">✏️ Editar</button>
                    <button class="task-btn task-delete" onclick="todoManager.deleteTask(${task.id})">🗑️ Deletar</button>
                </div>
            </div>
        `).join('');
    }
}

// Initialize the application when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.todoManager = new TodoManager();
});