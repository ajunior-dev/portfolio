# 4-form-validator

Pequena página estática que valida formulários no lado do cliente com JavaScript.

Recursos:

- Verificação em tempo real de campos obrigatórios, e‑mail, senhas;
- Sanitização contra XSS;
- Máscara de entrada e validação de CPF/legalese;
- Módulos de feedback visual via classes CSS.

Abra `index.html` em um navegador ou use um servidor local (`python -m http.server`).

### Arquivos principais
- `index.html`: marcação do formulário e referências a CSS/JS;
- `style.css`: estilos responsivos e classes de feedback;
- `script.js`: validações e funções utilitárias.

### Objetivo
Mostrar como defender aplicações web de entradas maliciosas, com foco em usabilidade e padrões modernos de HTML5.