# 8-log-analyzer

Aplicação Java que lê arquivos de logs e extrai informações úteis.

Funcionalidades:

- Parsing de entradas no formato `[timestamp] [tipo] [ip] [usuario] [mensagem]`;
- Estatísticas gerais (contagem total, falhas de login, IPs suspeitos, ataques detectados);
- Identificação de usuários problemáticos e janela de tempo para ataques de brute force;
- Gera arquivo de amostra se nenhum log for encontrado.

Compile com `javac` e execute `LogAnalyzer` fornecendo o caminho do log como argumento.