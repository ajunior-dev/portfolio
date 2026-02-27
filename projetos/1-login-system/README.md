# 1-login-system

Sistema de login seguro escrito em Java. Ideal para demonstrar conceitos de segurança em aplicações desktop:

- Hash de senhas usando SHA-256;
- Bloqueio temporário após múltiplas tentativas para prevenir ataques de força bruta;
- Registro de logs detalhado para auditoria;
- Projeto orientado a objetos com classes `LoginManager`, `User` e `LoginSystem`;
- Fácil de compilar/executar com `javac`/`java`.

## Como usar

```bash
javac *.java
java LoginSystem
```

O programa solicita credenciais e aplica todas as validações descritas acima.

### Estrutura do código
- `User.java` representa um usuário com nome e hash de senha;
- `LoginManager.java` realiza as verificações, mantém contador de tentativas e gera logs;
- `LoginSystem.java` contém o método `main` e a interface de console.

### Motivação
Este projeto foi criado para demonstrar práticas de segurança em autenticação e rastrear atividades de login, mostrando claramente como prevenir ataques comuns em aplicações empresariais.