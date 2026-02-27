🚀 GUIA RÁPIDO - 9 PROJETOS DE SEGURANÇA
========================================

Este arquivo contém **comandos rápidos** para rodar cada projeto.

---

## ☕ PROJETOS JAVA

### 1️⃣ Sistema de Login com Força Bruta Protection
```bash
cd projetos/1-login-system
javac User.java LoginManager.java LoginSystem.java
java LoginSystem

# Teste: Register → Login 3x com senha errada → Bloqueio
```

### 2️⃣ Simulador de Cofre - Análise de Força de Senha
```bash
cd projetos/2-safe-simulator
javac PasswordValidator.java SafeSimulator.java
java SafeSimulator

# Teste: Digitar "abc123" e "MyDog@2026_Coffee#Secure"
```

### 3️⃣ Gerenciador de Senhas (Arquivo Local)
```bash
cd projetos/3-password-manager
javac PasswordEntry.java PasswordManager.java
java PasswordManager

# Cria: passwords.dat e passwords_backup.csv
```

### 8️⃣ Log Analyzer - Detecção de Ataques
```bash
cd projetos/8-log-analyzer
javac LogAnalyzer.java
java LogAnalyzer

# Gera: sample-logs.txt com dados simulados
# Detecta: IPs suspeitos, brute force, usuários problemáticos
```

### 9️⃣ Task Manager - Controle de Acesso (RBAC)
```bash
cd projetos/9-task-manager
javac User.java Task.java TaskManager.java
java TaskManager

# Login como: Admin (todos permissões) ou User (permissões limitadas)
```

---

## 🌐 PROJETOS WEB (Browser)

### 4️⃣ Validador de Formulário (Prevenção de XSS)
```bash
cd projetos/4-form-validator

# Opção 1: Abrir arquivo direto
# Double-click em: index.html

# Opção 2: Servidor local (Python)
python -m http.server 8000
# Acesse: http://localhost:8000

# Opção 3: Servidor local (Node.js)
npx http-server
# Acesse: http://localhost:8080
```

### 5️⃣ Simulador de Força Bruta (Educacional)
```bash
cd projetos/5-brute-force-simulator

# Abrir: index.html no navegador
# Teste com: 4 caracteres only numbers → segundos
#           8 caracteres com símbolos → horas/dias
```

### 6️⃣ Gerador de Senha Forte (com Entropia)
```bash
cd projetos/6-password-generator

# Abrir: index.html no navegador
# Ver: Entropia em bits, tempo para quebrar
```

### 7️⃣ API Fake - RESTful Simulada
```bash
cd projetos/7-fake-api

# Abrir: index.html no navegador
# Testar endpoints: /api/users, /api/posts, /api/search
```

---

## 📋 CHECKLIST EXECUTAR TUDO

```bash
# Java - Compilar e rodar cada um
cd 1-login-system && javac *.java && java LoginSystem
cd ../2-safe-simulator && javac *.java && java SafeSimulator
cd ../3-password-manager && javac *.java && java PasswordManager
cd ../8-log-analyzer && javac *.java && java LogAnalyzer
cd ../9-task-manager && javac *.java && java TaskManager

# Web - Abrir em navegador
# File > Open > projetos/4-form-validator/index.html
# File > Open > projetos/5-brute-force-simulator/index.html
# File > Open > projetos/6-password-generator/index.html
# File > Open > projetos/7-fake-api/index.html
```

---

## 📊 RESUMO DOS PROJETOS

| # | Nome | Tipo | Nível | Conceitos Principais |
|---|------|------|-------|---------------------|
| 1 | Login System | Java | Int. | POO, Hash SHA-256, Brute Force |
| 2 | Safe Simulator | Java | Int. | Validação, Regex, Entropia |
| 3 | Password Manager | Java | Int. | I/O, Serialização, Arquivo |
| 4 | Form Validator | Web | Int. | XSS Prevention, Sanitização |
| 5 | Brute Force Sim. | Web | Int. | Algoritmos, Visualização |
| 6 | Password Generator | Web | Inic. | Aleatório, Entropia, UX |
| 7 | Fake API | Web | Int. | REST, Fetch, Async/Await |
| 8 | Log Analyzer | Java | Avç. | Regex, Análise, Anomalias |
| 9 | Task Manager | Java | Avç. | RBAC, Design Patterns |

---

## 💻 REQUISITOS

### Para Java:
- ✅ JDK 11+ instalado
- ✅ Nenhuma dependência externa

### Para Web:
- ✅ Navegador moderno (Chrome, Firefox, Safari, Edge)
- ✅ Nenhuma dependência extern

---

## 🎯 POR ONDE COMEÇAR?

**Se é iniciante:**
1. Abrir `6-password-generator` no navegador
2. Depois `4-form-validator`
3. Depois `1-login-system` no terminal

**Se é intermediário:**
1. Rodar `1-login-system` (Backend)
2. Abrir `7-fake-api` (Frontend)
3. Estudar `8-log-analyzer`

**Se é avançado:**
1. Combinar Backend + Frontend
2. Entender RBAC em `9-task-manager`
3. Implementar JWT ou OAuth nos projetos

---

## 📚 DOCUMENTAÇÃO

Para documentação detalhada de cada projeto, ver:
```bash
cat projetos/README.md
```

---

## 🆘 TROUBLESHOOTING

### Java: "javac não encontrado"
```bash
# Windows - Adicionar ao PATH
# C:\Program Files\Java\jdk-XX\bin

# Mac/Linux - Instalar JDK
# brew install openjdk
# sudo apt install default-jdk
```

### Web: "CORS error"
```bash
# Use servidor local em vez de file://
python -m http.server 8000
# Ou
npx http-server
```

### "Class not found"
```bash
# Garantir que está no diretório correto
cd projetos/1-login-system
# E compilar no mesmo diretório
javac *.java
java LoginSystem
```

---

## ✨ DESTAQUES

**Maior: Login System** (Demonstra segurança real)
**Mais Legal: Brute Force Simulator** (Hacker ético!)
**Mais Arquitetura: Task Manager** (RBAC production-ready)
**Mais Educativo: Todos!** 

---

## 🔐 SEGURANÇA

⚠️ **AVISO EDUCACIONAL:**
Estes projetos são **para aprendizado apenas**.
Não use em produção sem:
- ✅ Validação adicional
- ✅ Banco de dados seguro
- ✅ HTTPS/TLS
- ✅ Autenticação forte (JWT/OAuth)
- ✅ Auditoria completa

---

**Pronto para começar? Escolha um projeto e vamos lá! 🚀**
