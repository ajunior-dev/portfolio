📚 SEGURANÇA & ARQUITETURA - 9 PROJETOS PRÁTICOS
================================================

Este diretório contém **9 projetos educacionais** de segurança, arquitetura de sistemas e desenvolvimento web, organizados por nível de dificuldade.

🎯 OBJETIVOS
-----------

✓ Demonstrar noção de **segurança** em aplicações
✓ Aplicar conceitos de **POO** e **design patterns**
✓ Praticar desenvolvimento **backend (Java)** e **frontend (JS)**
✓ Entender **hashes, encriptação e validação**
✓ Posicionar-se como desenvolvedor **consciente de segurança**

---

## 📋 PROJETOS

### 🟦 PROJETOS JAVA (Backend & Segurança)

#### **1️⃣ SISTEMA DE LOGIN COM CONTROLE DE TENTATIVAS**
📁 Pasta: `1-login-system/`
Nível: **Iniciante → Intermediário**

**Conceitos:**
- Orientação a Objetos (POO)
- Encapsulamento
- Hash SHA-256
- Controle de estado
- Brute force protection

**Funcionalidades:**
✓ Cadastro de usuário
✓ Validação de login
✓ Limite de 3 tentativas
✓ Bloqueio temporário (2 minutos)
✓ Registro de logs em arquivo
✓ Hash de senha seguro

**Como executar:**
```bash
cd 1-login-system
javac User.java LoginManager.java LoginSystem.java
java LoginSystem
```

**O que impressiona:**
- Hash SHA-256 (segurança real)
- Controle de força bruta
- Log estruturado de eventos
- Encapsulamento de dados sensíveis

---

#### **2️⃣ SIMULADOR DE COFRE DIGITAL**
📁 Pasta: `2-safe-simulator/`
Nível: **Intermediário**

**Conceitos:**
- Validação de padrões
- Regex avançado
- Cálculo de entropia
- Análise de força

**Funcionalidades:**
✓ Validar força de senha
✓ Classificação (Fraca/Média/Forte)
✓ Detecção de padrões óbvios
✓ Cálculo de entropia (bits)
✓ Dicas de melhoria
✓ Verificação de sequências perigosas

**Como executar:**
```bash
cd 2-safe-simulator
javac PasswordValidator.java SafeSimulator.java
java SafeSimulator
```

**Por que é importante:**
- Demonstra noção de **entropia criptográfica**
- Detecção de padrões inseguros
- Educativo sobre força de senha

---

#### **3️⃣ MINI GERENCIADOR DE SENHAS (Local)**
📁 Pasta: `3-password-manager/`
Nível: **Intermediário**

**Conceitos:**
- Serialização (Java)
- I/O e File handling
- Estrutura de dados
- Exportação de dados

**Funcionalidades:**
✓ Armazenar senhas em arquivo
✓ Hash SHA-256
✓ Listar senhas armazenadas
✓ Buscar por serviço
✓ Adicionar/Remover entradas
✓ Exportar para CSV

**Como executar:**
```bash
cd 3-password-manager
javac PasswordEntry.java PasswordManager.java
java PasswordManager
```

**O que mostra:**
- Armazenamento seguro local
- Organização modular
- Persistência de dados

---

#### **8️⃣ LOG ANALYZER SIMPLES**
📁 Pasta: `8-log-analyzer/`
Nível: **Intermediário → Avançado**

**Conceitos:**
- Parsing de texto (Regex)
- Análise de dados
- Detecção de anomalias
- Relatórios

**Funcionalidades:**
✓ Ler arquivo de log
✓ Contar tentativas de login
✓ Identificar IPs suspeitos
✓ Detectar ataques brute force
✓ Gerar relatório de segurança
✓ Análise de usuários problemáticos

**Como executar:**
```bash
cd 8-log-analyzer
javac LogAnalyzer.java
java LogAnalyzer
```

**Por que impressiona:**
- Simula trabalho de **analista de segurança**
- Pattern matching com regex
- Detecção de anomalias
- Thinking analítico sobre segurança

---

#### **9️⃣ SISTEMA DE CONTROLE DE TAREFAS COM NÍVEIS DE ACESSO**
📁 Pasta: `9-task-manager/`
Nível: **Intermediário → Avançado**

**Conceitos:**
- RBAC (Role-Based Access Control)
- Controle de permissões
- Separação de responsabilidades
- Design patterns

**Funcionalidades:**
✓ Dois papéis: ADMIN e USER
✓ Permissões diferentes por papel
✓ Gerenciamento de tarefas
✓ Atribuição de tarefas
✓ Controle de acesso granular

**ADMIN pode:**
- Criar tarefas
- Editar qualquer tarefa
- Deletar tarefas
- Ver todas as tarefas
- Designar tarefas

**USER pode:**
- Ver suas próprias tarefas
- Atualizar status de suas tarefas

**Como executar:**
```bash
cd 9-task-manager
javac User.java Task.java TaskManager.java
java TaskManager
```

**O que mostra:**
- Conceito de RBAC (muito usado em produção)
- Controle fino de acesso
- Arquitetura modular

---

### 🟩 PROJETOS WEB (Frontend & Interatividade)

#### **4️⃣ VALIDADOR DE FORMULÁRIO SEGURO**
📁 Pasta: `4-form-validator/`
Nível: **Iniciante → Intermediário**

**Tecnologias:** HTML5, CSS3, Vanilla JavaScript

**Conceitos:**
- Validação no cliente
- Sanitização de entrada
- Prevenção de XSS
- Regex para validação
- Feedback visual

**Funcionalidades:**
✓ Validação de email
✓ Validação de senha forte
✓ Verificação de caracteres perigosos
✓ Prevenção básica contra XSS
✓ Validação em tempo real
✓ Feedback visual amigável

**Como abrir:**
```bash
cd 4-form-validator
# Abrir index.html no navegador
# Ou usar um servidor local:
python -m http.server 8000
# Acesse: http://localhost:8000
```

**Código destaque:**
```javascript
// Sanitização básica contra entrada maliciosa
sanitizeInput(input) {
    let sanitized = input.replace(/<[^>]*>/g, '');
    return sanitized
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');
}
```

---

#### **5️⃣ SIMULADOR DE ATAQUE DE FORÇA BRUTA (Educacional)**
📁 Pasta: `5-brute-force-simulator/`
Nível: **Intermediário**

**Tecnologias:** HTML5, CSS3, Canvas, Vanilla JS

**Conceitos:**
- Base conversion
- Cálculo de combinações
- Progresso visual
- UX/UI educativa

**Funcionalidades:**
✓ Gera combinações de senha
✓ Mostra tempo estimado
✓ Demonstrates por que senha fraca é perigosa
✓ Visualização em tempo real
✓ Estatísticas educativas

**Como abrir:**
```bash
cd 5-brute-force-simulator
# Abrir index.html no navegador
```

**Por que impressiona:**
- Demonstra conceito de **hacker ético**
- Visualização educativa
- Mostra impacto real da fraqueza de senha

---

#### **6️⃣ GERADOR DE SENHA FORTE**
📁 Pasta: `6-password-generator/`
Nível: **Iniciante**

**Tecnologias:** HTML5, CSS3, Vanilla JS

**Conceitos:**
- Geração criptográfica
- Cálculo de entropia
- UX intuitiva
- Feedback visual

**Funcionalidades:**
✓ Gerar senhas aleatórias
✓ Escolher tamanho e opções
✓ Calcular entropia em bits
✓ Estimar tempo para quebrar
✓ Copiar para área de transferência
✓ Dicas de segurança

**Como abrir:**
```bash
cd 6-password-generator
# Abrir index.html no navegador
```

**Extra desejável:**
- Entropia estimada (✓ implementado!)

---

#### **7️⃣ API FAKE EM JAVASCRIPT**
📁 Pasta: `7-fake-api/`
Nível: **Intermediário**

**Tecnologias:** HTML5, CSS3, Vanilla JS (Fetch API)

**Conceitos:**
- Simulação de API RESTful
- Async/await
- Padrão de requisição/resposta
- Documentação

**Funcionalidades:**
✓ Endpoints fake (GET, POST)
✓ Retorno JSON
✓ Consumo com fetch
✓ Simulação de delay
✓ Documentação integrada

**Endpoints disponíveis:**
```
GET /api/users         → Lista usuários
GET /api/users/:id     → Usuário específico
POST /api/users        → Criar usuário
GET /api/posts         → Lista posts
GET /api/search?q=...  → Buscar posts
```

**Como abrir:**
```bash
cd 7-fake-api
# Abrir index.html no navegador
```

**Por qu impressiona:**
- Noção de **arquitetura de API**
- Padrão RESTful
- Async patterns em JS

---

## 🎓 COMO ESTUDAR OS PROJETOS

### **Ordem Recomendada (Iniciante):**
1. **6-password-generator** (20 min) - Comece simples
2. **4-form-validator** (30 min) - Aprender validação
3. **1-login-system** (1h) - Primeiro Java robusto
4. **2-safe-simulator** (40 min) - Conceitos avançados de força

### **Para Intermediário:**
1. **7-fake-api** (1h) - Arquitetura de API
2. **3-password-manager** (1h) - Persistência de dados
3. **5-brute-force-simulator** (1h) - Pentest educacional
4. **8-log-analyzer** (1.5h) - Análise de segurança

### **Avançado:**
1. **9-task-manager** (1.5h) - RBAC em produção

---

## 💡 DICAS DE ENTREVISTA

Ao mostrar esses projetos para recrutadores:

✅ **Foque em segurança:**
- "Este projeto demonstra hash SHA-256"
- "Implementei validação contra XSS"
- "Controle de brute force com bloqueio temporário"

✅ **Mostrar arquitetura:**
- "Separação de responsabilidades"
- "RBAC usando padrão role-based"
- "Modularização com classes bem definidas"

✅ **Falar sobre UX:**
- "Feedback em tempo real"
- "Validação amigável ao usuário"
- "Visualização educativa de conceitos"

✅ **Mencionar boas práticas:**
- "Sanitização de entrada"
- "Hash seguro de senha"
- "Logging estruturado de eventos"
- "Permissões granulares"

---

## 🔧 STACK UTILIZADO

**Backend:**
- Java (POO, Collections, I/O, Regex)
- Hash SHA-256
- File handling & Serialização

**Frontend:**
- HTML5 semântico
- CSS3 (Gradient, Flexbox, Grid)
- Vanilla JavaScript (ES6+)
- Fetch API

**Sem dependências externas** (apenas Java stdlib e JS nativo)

---

## 📊 ESTRUTURA DE PASTAS

```
projetos/
├── 1-login-system/
│   ├── User.java
│   ├── LoginManager.java
│   └── LoginSystem.java
├── 2-safe-simulator/
│   ├── PasswordValidator.java
│   └── SafeSimulator.java
├── 3-password-manager/
│   ├── PasswordEntry.java
│   └── PasswordManager.java
├── 4-form-validator/
│   ├── index.html
│   ├── script.js
│   └── style.css
├── 5-brute-force-simulator/
│   ├── index.html
│   ├── script.js
│   └── style.css
├── 6-password-generator/
│   ├── index.html
│   ├── script.js
│   └── style.css
├── 7-fake-api/
│   ├── index.html
│   ├── script.js
│   └── style.css
├── 8-log-analyzer/
│   └── LogAnalyzer.java
├── 9-task-manager/
│   ├── User.java
│   ├── Task.java
│   └── TaskManager.java
└── README.md (este arquivo)
```

---

## ⚡ COMEÇAR AGORA

### Projetos Java:
```bash
cd 1-login-system
javac *.java
java LoginSystem
```

### Projetos Web:
```bash
cd 4-form-validator
# Abrir index.html com navegador
# Ou: python -m http.server 8000
```

---

## 🚀 Próximos Passos

Após completar esses projetos, considere:

- ✅ Integrar Backend com Frontend (Java + HTML)
- ✅ Banco de dados (SQL)
- ✅ API RESTful com Spring Boot
- ✅ Testes automatizados
- ✅ Deploy em servidor

---

## 📝 Licença

Todos os projetos são **educacionais** e de **código aberto**.
Use, modifique e aprenda!

---

## 👤 Autor

Junior Silva | Desenvolvedor em Formação | @senhoritadev

Construindo aplicações seguras e bem arquitetadas! 🔐

---

**Última atualização:** Fevereiro 2026
**Status:** ✅ Completo e funcional
