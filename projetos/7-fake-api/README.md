# 7-fake-api

Simulador simples de API REST implementado inteiramente em JavaScript.

Funções:

- `GET /api/users` e `POST /api/users` com dados em memória;
- filtro de busca e paginação fictícia;
- Endpoint de posts e pesquisa;
- Interface front‑end para enviar solicitações e ver respostas.

Executar abrindo `index.html` e utilizar a interface para testar os endpoints.

### Estrutura do código
- A simulação é feita inteiramente em `script.js` com controle de rotas e dispatch para diferentes funções;
- Dados são mantidos em arrays locais para usuários e posts.

### Objetivo educacional
Fornecer um ambiente seguro para testar chamadas AJAX sem back-end real. Ideal para aprender a trabalhar com Fetch API, tratamento de erros e visualização de respostas JSON.