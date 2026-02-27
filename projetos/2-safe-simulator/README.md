# 2-safe-simulator

Simulador educacional de cofre em Java que exige senha forte para abrir.

Funcionalidades:

- Validações de comprimento e tipos de caracteres (maiúsculas, minúsculas, números, símbolos);
- Mensagens de feedback de força da senha;
- Conceito de repetição e padrões óbvios penalizados.

### Componentes
- `PasswordValidator.java` contém todas as regras de validação e calcula uma pontuação;
- `SafeSimulator.java` usa o validador e simula a tentativa de abertura do cofre.

### Por que este projeto?
Construído para ensinar a importância de políticas de senha fortes. Permite experimentar diferentes combinações e ver como elas afetam a força.

Compile e execute da mesma forma que outros projetos Java:

```bash
javac PasswordValidator.java SafeSimulator.java
java SafeSimulator
```