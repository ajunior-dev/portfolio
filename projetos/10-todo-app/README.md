<<<<<<< HEAD
# TODO Manager Pro 📋

**Gerenciador de Tarefas Profissional com Persistence e Filtros Avançados**

## 📋 Visão Geral

TODO Manager Pro é um aplicativo web de gerenciamento de tarefas com arquitetura moderna, desenvolvido em Vanilla JavaScript com suporte completo a LocalStorage persistence, filtros avançados, ordenação dinâmica e import/export JSON.

### Conceitos Educacionais Demonstrados
- **localStorage API** - Persistência de dados no navegador
- **JSON Manipulation** - Serialização/desserialização de dados
- **DOM Manipulation** - Renderização dinâmica sem frameworks
- **Event Delegation** - Gestão eficiente de eventos
- **Filter/Sort Algorithms** - Processamento de dados em array
- **XSS Prevention** - Sanitização de entrada de usuário
- **Responsive Design** - Interface adaptável para múltiplos dispositivos
- **State Management** - Gerenciamento centralizado de estado

## ✨ Funcionalidades

### Core Features
- ✅ **Adicionar Tarefas** - Com níveis de prioridade (Alta, Média, Baixa)
- ✅ **Completar/Descometer** - Mark tasks as done
- ✅ **Editar Tarefas** - Modificar texto de tarefas existentes
- ✅ **Deletar Tarefas** - Remove individual or all completed tasks
- ✅ **6 Filtros Distintos** - Todas, Ativas, Completas, por Prioridade
- ✅ **4 Opções de Ordenação** - Data, Prioridade, Alfabética
- ✅ **StatÍsticas em Tempo Real** - Total, Completas, Ativas, Alta Prioridade
- ✅ **Barra de Progresso** - Visualização percentual de conclusão

### Advanced Features
- 📥 **Exportar JSON** - Backup de tarefas em arquivo
- 📤 **Importar JSON** - Restaurar tarefas de backup
- 🗑️ **Limpar Concluídas** - Remover apenas tarefas completas
- ☠️ **Limpar Tudo** - Reset completo com confirmação dupla
- 💾 **Persistência Automática** - LocalStorage sync em tempo real

### User Experience
- 🎨 **Design Moderno** - Gradientes, sombras e animações suaves
- ⌨️ **Atajos de Teclado** - Enter para adicionar tarefas
- 📱 **Totalmente Responsivo** - Funciona em desktop, tablet e mobile
- 🛡️ **Sanitização de Entrada** - Proteção contra XSS
- ✨ **Animações Suaves** - Transições e efeitos visuais

## 🏗️ Arquitetura

### Estrutura de Dados - Task Object
```javascript
{
    id: 1234567890,                    // Timestamp único
    text: "Estudar JavaScript",         // Descrição sanitizada
    completed: false,                  // Status de conclusão
    priority: "alta",                  // Prioridade (alta/media/baixa)
    createdAt: "2026-02-27T...",      // ISO timestamp de criação
    updatedAt: "2026-02-27T..."       // ISO timestamp de atualização
}
```

### TodoManager Class - Métodos Principais

#### Lifecycle
- `init()` - Inicia aplicação e setup de listeners
- `setupEventListeners()` - Registra handlers de eventos

#### CRUD Operations
- `addTask()` - Criar nova tarefa com validação
- `toggleTask(id)` - Alternar status completed
- `editTask(id)` - Editar texto de tarefa
- `deleteTask(id)` - Remover tarefa com confirmação

#### Data Processing
- `filterTasks()` - Aplicar filtro atual ao array
- `sortTasks(array)` - Ordenar tarefas conforme sort atual
- `updateStats()` - Calcular estatísticas e atualizar UI

#### Persistence
- `saveToStorage()` - Serializar tasks para localStorage
- `loadFromStorage()` - Desserializar tasks do localStorage

#### Import/Export
- `exportJSON()` - Gerar arquivo .json para download
- `importJSON(event)` - Parse e import de arquivo JSON

#### Render
- `render()` - Rerenderização completa do DOM
- `formatDate(dateString)` - Formatação de datas em português

## 🚀 Como Usar

### Adicionar Tarefa
1. Digite o texto da tarefa no input
2. Selecione o nível de prioridade (Baixa/Média/Alta)
3. Clique no botão "+ Adicionar" ou pressione Enter

### Filtrar Tarefas
- **Todas** - Mostra todas as tarefas
- **Ativas** - Apenas tarefas não completas
- **Completas** - Apenas tarefas finalizadas
- **🔴 Alta Prioridade** - Tarefas com prioridade alta
- **🟡 Média Prioridade** - Tarefas com prioridade média
- **🟢 Baixa Prioridade** - Tarefas com prioridade baixa

### Ordenar Tarefas
- **Data (Mais Recentes)** - Tarefas criadas recentemente primeiro
- **Data (Mais Antigas)** - Tarefas mais antigas primeiro
- **Prioridade** - Ordem: Alta → Média → Baixa
- **Alfabética** - A-Z em português

### Estatísticas
- **Total** - Quantidade total de tarefas
- **Completas** - Quantas foram finalizadas
- **Ativas** - Quantas ainda estão pendentes
- **Alta Prioridade** - Tarefas urgentes restantes

### Backup e Restauração
```javascript
// Exportar
Clique em "📥 Exportar JSON" → Salva tasks_YYYY-MM-DD.json

// Importar
Clique em "📤 Importar JSON" → Selecione arquivo .json
```

## 🔒 Segurança

### XSS Prevention
```javascript
sanitizeInput(input) {
    const div = document.createElement('div');
    div.textContent = input;  // textContent escapa caracteres especiais
    return div.innerHTML;     // Retorna string sanitizada
}
```
Todas as entradas de usuário são sanitizadas antes de armazenar/exibir.

### Input Validation
- Verifica se texto não está vazio
- Limita comprimento máximo a 200 caracteres
- Valida arquivo JSON antes de importar

## 📦 Estrutura de Arquivos

```
10-todo-app/
├── index.html          # Markup semântico com estrutura
├── style.css           # Design responsivo com gradientes
├── script.js           # Lógica da aplicação (450+ linhas)
└── README.md           # Este arquivo
```

## 🎨 Design & Responsividade

### Paleta de Cores
```css
--primary: #6366f1        /* Indigo - Ações principais */
--secondary: #8b5cf6      /* Purple - Gradientes */
--danger: #ef4444         /* Red - Ações destrutivas */
--success: #10b981        /* Green - Prioridade baixa */
--warning: #f59e0b        /* Amber - Prioridade média */
```

### Breakpoints
- Desktop: 900px max-width container
- Tablet: Ajusta grid para 2 colunas
- Mobile: Stack single column, full width buttons

## 🧪 Testar Funcionalidades

### Teste 1: Persistência
1. Adicione 3 tarefas
2. Recarregue a página
3. ✅ Tarefas devem estar lá

### Teste 2: Filtros
1. Crie tarefas com diferentes prioridades
2. Clique em cada filtro
3. ✅ Lista deve atualizar corretamente

### Teste 3: Export/Import
1. Adicione 5 tarefas
2. Clique "Exportar JSON"
3. Abra arquivo salvo
4. Apague todas as tarefas manualmente
5. Clique "Importar JSON" → selecione arquivo
6. ✅ Tarefas devem ser restauradas

### Teste 4: Sorting
1. Crie tarefas com datas e prioridades variadas
2. Teste cada opção de ordenação
3. ✅ Resultados devem ser consistentes

## 📊 Performance

- **Renderização** - O(n) onde n = número de tarefas visíveis
- **Filtro** - O(n) iteração única sobre array
- **Sort** - O(n log n) usando native sort()
- **LocalStorage** - Sincronizado a cada mudança
- **Max Tasks Recomendado** - 1000+ tarefas (otimizado)

## 🎓 Conceitos Avançados Demonstrados

1. **ES6+ Classes** - OOP em JavaScript com métodos
2. **Array Methods** - filter(), map(), find(), sort()
3. **Date Handling** - toISOString(), toLocaleDateString()
4. **FileAPI** - Leitura/escrita de arquivos
5. **Blob & ObjectURL** - Geração de downloads
6. **Event Listeners** - Delegação e bubbling
7. **localStorage API** - Persistência no navegador
8. **String Sanitization** - Proteção contra XSS
9. **Responsive Design** - CSS Grid, Flexbox, Media Queries
10. **State Management** - Centralizado na classe TodoManager

## 📚 Instruções de Compilação/Execução

```bash
# Nenhuma compilação necessária!
# Apenas abra em um navegador moderno:

# Opção 1: Double click no index.html
# Opção 2: Arrastar index.html para navegador
# Opção 3: Usar live server (recomendado para debugging)

# Suporte: Chrome, Firefox, Safari, Edge (todos modernos)
```

## 🔄 Próximas Melhorias Possíveis

- [ ] Sincronização com backend/API
- [ ] Autenticação com login
- [ ] Compartilhamento de listas
- [ ] Notificações para tarefas vencidas
- [ ] Categorias/Tags adicionais
- [ ] Drag & drop reordering
- [ ] Busca/Search avançada
- [ ] Modo escuro (dark mode)
- [ ] Sincronização com Google Calendar

## 📝 Notas de Desenvolvimento

### Decisões de Design
- **LocalStorage em vez de Backend** - Simplicidade e offline-first
- **Selector único .task-item** - Melhor performance que múltiplos listeners
- **Timestamp para IDs** - Garantia de unicidade sem backend
- **JSON Export** - Permite portabilidade e backup manual

### Otimizações
- Render único consolidado (não atualiza item isolado)
- Filtro/sort processados em array antes de render
- Sanitização em addTask (não no render)
- Event delegation através de onclick (vs addEventListener em cada item)

## 👨‍💻 Autor

**Junior Silva** - Desenvolvedor Full-Stack com foco em Segurança

## 📄 Licença

This project is open source and available for educational purposes.
