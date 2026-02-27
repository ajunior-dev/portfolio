/**
 * API Fake em JavaScript
 * Projeto 7 - Simulação de endpoints RESTful
 */

// ==================== DADOS SIMULADOS ====================
const fakeDatabase = {
    users: [
        { id: 1, name: 'Junior Silva', email: 'junior@email.com', role: 'admin' },
        { id: 2, name: 'Ana Costa', email: 'ana@email.com', role: 'user' },
        { id: 3, name: 'Carlos Santos', email: 'carlos@email.com', role: 'user' }
    ],
    posts: [
        { id: 1, userId: 1, title: 'Segurança em Aplicações Web', content: '...' },
        { id: 2, userId: 2, title: 'JavaScript Avançado', content: '...' },
        { id: 3, userId: 1, title: 'Hash e Criptografia', content: '...' }
    ]
};

// ==================== SIMULADOR DE API ====================
class FakeAPI {
    /**
     * Simula delay de requisição real (200-800ms)
     */
    static getRandomDelay() {
        return Math.random() * 600 + 200;
    }

    /**
     * GET /api/users - Retorna todos os usuários
     */
    static async getUsers() {
        await this.delay(this.getRandomDelay());
        return {
            success: true,
            data: fakeDatabase.users,
            timestamp: new Date().toISOString()
        };
    }

    /**
     * GET /api/users/:id - Retorna um usuário específico
     */
    static async getUserById(id) {
        await this.delay(this.getRandomDelay());

        const user = fakeDatabase.users.find(u => u.id === parseInt(id));

        if (!user) {
            return {
                success: false,
                error: 'Usuário não encontrado',
                status: 404
            };
        }

        return {
            success: true,
            data: user,
            timestamp: new Date().toISOString()
        };
    }

    /**
     * POST /api/users - Cria um novo usuário
     */
    static async createUser(name, email) {
        await this.delay(this.getRandomDelay());

        if (!name || !email) {
            return {
                success: false,
                error: 'Nome e email são obrigatórios',
                status: 400
            };
        }

        // Verificar duplicado
        if (fakeDatabase.users.some(u => u.email === email)) {
            return {
                success: false,
                error: 'Email já cadastrado',
                status: 409
            };
        }

        const newUser = {
            id: fakeDatabase.users.length + 1,
            name,
            email,
            role: 'user'
        };

        fakeDatabase.users.push(newUser);

        return {
            success: true,
            message: 'Usuário criado',
            data: newUser,
            status: 201
        };
    }

    /**
     * GET /api/posts - Retorna todos os posts
     */
    static async getPosts() {
        await this.delay(this.getRandomDelay());

        return {
            success: true,
            data: fakeDatabase.posts,
            timestamp: new Date().toISOString()
        };
    }

    /**
     * GET /api/search?q=termo - Busca em posts
     */
    static async search(query) {
        await this.delay(this.getRandomDelay());

        if (!query) {
            return {
                success: false,
                error: 'Query é obrigatório',
                status: 400
            };
        }

        const results = fakeDatabase.posts.filter(p =>
            p.title.toLowerCase().includes(query.toLowerCase())
        );

        return {
            success: true,
            query,
            results: results.length,
            data: results
        };
    }

    /**
     * Simula delay
     */
    static delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
}

// ==================== UI CONTROLLER ====================
class APIController {
    constructor() {
        this.methodSelect = document.getElementById('methodSelect');
        this.urlInput = document.getElementById('urlInput');
        this.sendBtn = document.getElementById('sendBtn');
        this.responseOutput = document.getElementById('responseOutput');
        this.loading = document.getElementById('loading');

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.sendBtn.addEventListener('click', () => this.handleRequest());

        // Clicar em botões de endpoint
        document.querySelectorAll('.endpoint-btn').forEach(btn => {
            btn.addEventListener('click', () => this.handleEndpointClick(btn));
        });

        // Allow Enter to send
        this.urlInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') this.handleRequest();
        });
    }

    handleEndpointClick(btn) {
        const endpoint = btn.dataset.endpoint;

        switch (endpoint) {
            case 'users':
                this.urlInput.value = '/api/users';
                this.methodSelect.value = 'GET';
                break;
            case 'user':
                this.urlInput.value = '/api/users/1';
                this.methodSelect.value = 'GET';
                break;
            case 'create':
                this.urlInput.value = '/api/users';
                this.methodSelect.value = 'POST';
                break;
            case 'posts':
                this.urlInput.value = '/api/posts';
                this.methodSelect.value = 'GET';
                break;
            case 'search':
                this.urlInput.value = '/api/search?q=segurança';
                this.methodSelect.value = 'GET';
                break;
        }

        // Auto enviar
        setTimeout(() => this.handleRequest(), 300);
    }

    async handleRequest() {
        const method = this.methodSelect.value;
        const url = this.urlInput.value || '/api/users';

        this.showLoading(true);

        try {
            const response = await this.routeRequest(method, url);
            this.displayResponse(response, method, url);
        } catch (error) {
            this.displayError(error);
        } finally {
            this.showLoading(false);
        }
    }

    async routeRequest(method, url) {
        const urlObj = new URL('http://localhost' + url);
        const pathname = urlObj.pathname;
        const search = urlObj.search;

        // GET /api/users
        if (pathname === '/api/users' && method === 'GET') {
            return await FakeAPI.getUsers();
        }

        // GET /api/users/:id
        if (pathname.match(/^\/api\/users\/\d+$/)) {
            const id = pathname.split('/').pop();
            return await FakeAPI.getUserById(id);
        }

        // POST /api/users
        if (pathname === '/api/users' && method === 'POST') {
            return await FakeAPI.createUser(
                prompt('Nome:') || 'Novo Usuário',
                prompt('Email:') || 'novo@email.com'
            );
        }

        // GET /api/posts
        if (pathname === '/api/posts') {
            return await FakeAPI.getPosts();
        }

        // GET /api/search
        if (pathname === '/api/search') {
            const query = new URLSearchParams(search).get('q');
            return await FakeAPI.search(query);
        }

        // 404
        return {
            success: false,
            error: 'Endpoint não encontrado',
            status: 404
        };
    }

    displayResponse(response, method, url) {
        const formatted = {
            timestamp: new Date().toLocaleTimeString('pt-BR'),
            method,
            url,
            httpStatus: response.status || (response.success ? 200 : 400),
            response
        };

        this.responseOutput.textContent = JSON.stringify(formatted, null, 2);
        this.responseOutput.classList.remove('error');
        this.responseOutput.classList.add('success');
    }

    displayError(error) {
        const response = {
            timestamp: new Date().toLocaleTimeString('pt-BR'),
            error: error.message
        };

        this.responseOutput.textContent = JSON.stringify(response, null, 2);
        this.responseOutput.classList.add('error');
    }

    showLoading(show) {
        this.loading.classList.toggle('hidden', !show);
    }
}

// Inicializar quando DOM carregar
document.addEventListener('DOMContentLoaded', () => {
    new APIController();
});
