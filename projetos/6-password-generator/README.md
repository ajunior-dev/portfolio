# 6-password-generator

Gerador de senhas fortes com cálculo de entropia.

Características:

- Slider para comprimento (8‑32 caracteres);
- Inclusão opcional de maiúsculas, minúsculas, números e símbolos;
- Cálculo dinâmico de entropia e tempo estimado de quebra;
- Barra de progresso de entropia com cores indicativas.

Basta abrir `index.html` num navegador moderno e interagir.

### Internals
`script.js` contém funções para construir charset, calcular entropia e tempo de quebra. A interface atualiza dinamicamente valores conforme o usuário altera as opções.

### Propósito
Este gerador foi projetado para enfatizar o conceito de entropia na segurança de senhas e ajudar usuários a escolher combinações robustas.