# 5-brute-force-simulator

Projeto web que simula um ataque de força bruta contra uma senha aleatória.

Características:

- Configuração de comprimento, inclusão de números e símbolos;
- Estatísticas de combinações possíveis e tempo médio de quebra assumindo 1k tentativas/segundo;
- Execução visual em tempo real mostrando tentativas, progresso e senha atual;
- Mensagens de sucesso ou falha;
- Código em HTML/CSS/JavaScript puro (sem dependências).

Abra `index.html` num navegador moderno para testar e alterar parâmetros.

### Como funciona
O simulador gera uma senha aleatória com base nas opções e então tenta combinações sequenciais exibindo progresso. Serve para ilustrar a exponencialidade das combinações e por que senhas longas são críticas.