# Portfólio de Junior Silva

Este repositório contém o site estático do portfólio pessoal de Junior Silva, com projetos, contato e informações profissionais.

## Publicação com GitHub Pages

1. Crie um repositório no GitHub chamado `portfolio` (ou outro nome desejado).
2. No seu computador, inicialize o repositório e adicione o remoto:
   ```bash
   git init
   git add .
   git commit -m "Initial portfolio site"
   git remote add origin https://github.com/ajunior-dev/portfolio.git
   git branch -M main
   git push -u origin main
   ```
3. Ações de GitHub Pages estão configuradas no `.github/workflows/gh-pages.yml`; ao dar push para `main`, o conteúdo será publicado automaticamente na branch `gh-pages`.
4. No **Settings &gt; Pages** do repositório no GitHub, selecione a branch `gh-pages` (ou `main` se preferir publicar diretamente) como fonte e salve. A URL pública estará disponível em alguns minutos.

### Deploy manual (sem GitHub Actions)

Se preferir, configure o GitHub Pages para servir a partir da branch `main` ou da pasta `/docs` e publique sem ações automatizadas.

## Estrutura do projeto

- `index.html` – página principal
- `style.css`, `script.js` – arquivos de estilo e script
- `projetos/` – subpastas com exemplos de projetos
- `.gitignore` – evita subir arquivos desnecessários
- `.github/workflows/gh-pages.yml` – workflow para deploy contínuo

## E-mail de contato

`dev.adilsonj@gmail.com` (também disponível na página de contato)

---

Utilize este repositório para hospedar seu portfólio e manter o conteúdo atualizado; os links no site apontam para projetos e repositórios GitHub correspondentes.