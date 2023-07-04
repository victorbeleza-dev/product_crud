## Projeto Backend (SpringBoot)
# Requisitos
Certifique-se de ter o seguinte software instalado em seu ambiente de desenvolvimento:

* Java JDK (versão 17 ou superior)
* Apache Maven
* PostgreSQL

# Configuração:
* Clone o repositório do projeto backend em sua máquina local
* Construa o projeto utilizando o Maven

# Execução:
* Após a conclusão da etapa de configuração, você pode executar o projeto Spring Boot com o seguinte comando:
* O servidor backend estará em execução em http://localhost:8080.

## Projeto Frontend (React)
# Requisitos
Certifique-se de ter o seguinte software instalado em seu ambiente de desenvolvimento:
* Node.js (versão 12 ou superior)
* npm

# Configuração:
* Clone o repositório do projeto frontend em sua máquina local
* Navegue até o diretório do projeto
* Instale as dependências do projeto utilizando o npm:
npm install

# Execução:
* Após a conclusão da etapa de configuração, você pode iniciar a aplicação React com o seguinte comando:
npm start ou yarn start

* A aplicação estará disponível em seu navegador no endereço http://localhost:3000.

* Certifique-se de que o servidor backend esteja em execução para que a aplicação frontend possa se comunicar com ele corretamente.


# Fluxo de Trabalho GitFlow

## Branches Principais
* master: A branch master contém as versões estáveis do projeto. As atualizações nessa branch são feitas apenas por meio de merges de branches de release e hotfixes.
* develop: A branch develop é o ponto central para o desenvolvimento. Novas funcionalidades, correções de bugs e outras atualizações são desenvolvidas nessa branch antes de serem mescladas em branches de release.

## Branches de Funcionalidade
* feature/{nome-da-funcionalidade}: Branches de funcionalidade são usadas para desenvolver novas funcionalidades ou melhorias. Cada funcionalidade é desenvolvida em uma branch separada, criada a partir da branch develop. Quando a funcionalidade estiver concluída, ela será mesclada de volta para a branch develop.
