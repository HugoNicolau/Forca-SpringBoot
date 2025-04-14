# Forca-SpringBoot

Um jogo da forca completo desenvolvido com Spring Boot, demonstrando conceitos de Programação Orientada a Objetos e arquitetura de aplicações web modernas.

## Sobre o Projeto

Este projeto implementa o clássico jogo da forca com uma arquitetura cliente-servidor:

- **Backend**: API RESTful desenvolvida com Spring Boot
- **Frontend**: Interface web responsiva usando HTML, CSS e JavaScript puro
- **Recursos**: Autenticação de usuários, ranking de jogadores, sistema de equipes e diferentes níveis de dificuldade

O jogo permite que os jogadores tentem adivinhar uma palavra secreta, letra por letra, antes que o boneco seja completamente desenhado na forca. Os jogadores podem escolher palavras aleatórias ou personalizadas e definir o nível de dificuldade.

## Como Executar o Projeto

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Git (opcional, para clonar o repositório)

### Passos para Execução

1. **Clone o repositório** (ou baixe o código-fonte):

```bash
git clone https://github.com/HugoNicolau/Forca-SpringBoot.git
cd Forca-SpringBoot
```

2. **Execute o aplicativo Spring Boot**:

```bash
cd forca
./mvnw spring-boot:run
```

3. **Acesse a aplicação**:

Abra seu navegador e acesse:
```
http://localhost:8080
```

### Execução em Ambiente de Desenvolvimento

Se você estiver desenvolvendo o projeto, recomendamos adicionar o Spring Boot DevTools para habilitar o recarregamento automático:

1. Caso não tenha, adicione a dependência no pom.xml:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

2. Reinicie o servidor para que as alterações tenham efeito.

## Estrutura do Projeto

### Backend (Java + Spring Boot)

```
src/main/java/com/jogodaforca/forca/
├── config/              # Configurações da aplicação
│   └── WebConfig.java   # Configuração CORS e Web
├── controller/          # Controladores REST
│   ├── EquipeController.java
│   ├── PartidaController.java
│   ├── PalavraController.java
│   └── UsuarioController.java
├── dto/                 # Objetos de Transferência de Dados
│   ├── EquipeDTO.java
│   ├── PartidaDTO.java
│   └── UsuarioDTO.java
├── model/               # Entidades e modelos de domínio
│   ├── BaseEntity.java  # Classe base para entidades
│   ├── Equipe.java
│   ├── EstadoDaForca.java
│   ├── Jogador.java     # Classe abstrata
│   ├── JogadorBot.java
│   ├── JogadorHumano.java
│   ├── LetraTentada.java
│   ├── Palavra.java
│   ├── Partida.java
│   ├── StatusPartida.java
│   └── Usuario.java
├── repository/          # Interfaces de repositórios JPA
│   ├── EquipeRepository.java
│   ├── PartidaRepository.java
│   └── UsuarioRepository.java
├── service/             # Serviços de negócio
│   ├── EquipeService.java
│   ├── JogadorService.java
│   ├── PartidaService.java
│   └── UsuarioService.java
├── util/                # Classes utilitárias
│   └── Resultado.java   # Wrapper para respostas da API
└── ForcaApplication.java  # Classe principal da aplicação
```

### Frontend (HTML, CSS, JavaScript)

```
src/main/resources/
├── static/              # Recursos estáticos
│   ├── css/
│   │   └── styles.css   # Estilos da aplicação
│   ├── js/
│   │   ├── api.js       # Cliente para a API REST
│   │   ├── equipe.js    # Gerenciamento de equipes
│   │   ├── forca.js     # Lógica do jogo
│   │   └── usuario.js   # Autenticação e perfil
│   ├── img/
│   │   └── forca-estados/  # Imagens do jogo
│   │       ├── forca0.png
│   │       └── ...
│   └── index.html       # Página principal
└── application.properties  # Configurações da aplicação
```

## Principais Funcionalidades

1. **Cadastro e Autenticação de Usuários**
   - Login com usuário e senha
   - Registro de novos usuários

2. **Jogo da Forca**
   - Palavras aleatórias ou personalizadas
   - Diferentes níveis de dificuldade
   - Interface visual interativa

3. **Ranking e Estatísticas**
   - Classificação de jogadores por taxa de vitória
   - Estatísticas individuais (vitórias, derrotas, etc.)

4. **Sistema de Equipes**
   - Criação e gerenciamento de equipes
   - Adição de jogadores às equipes
   - Ranking de equipes

## Modelagem de Classes

O projeto segue uma arquitetura orientada a objetos com hierarquia de classes bem definida:

- **Herança**: Classes como `JogadorHumano` e `JogadorBot` herdam de `Jogador`
- **Encapsulamento**: Atributos privados com getters/setters
- **Polimorfismo**: Diferentes tipos de jogadores com comportamentos específicos
- **Abstração**: Classes abstratas como `BaseEntity` e interfaces definem contratos

### Diagramas UML

Este projeto inclui diagramas UML detalhados para facilitar a compreensão da arquitetura:

1. **[Diagrama UML completo (UML.puml)](UML.puml)**: Diagrama completo do sistema
   - Contém todas as classes, interfaces, relacionamentos e pacotes
   - Visão abrangente da arquitetura do projeto

2. **[Diagrama de Classes (UML-classes.puml)](UML-classes.puml)**: Foco nas classes principais
   - Mostra as principais classes do modelo e suas relações
   - Simplificado para melhor visualização da estrutura principal

3. **[Diagrama de Models e DTOs (UML-models.puml)](UML-models.puml)**: Detalha as entidades e objetos de transferência
   - Visualiza a estrutura de dados e relacionamentos entre entidades
   - Mostra a conversão entre entidades e DTOs

![Diagrama UML do Sistema](uml-image.png)
*Diagrama UML gerado a partir do arquivo UML_FOR_IMAGE.puml*

Para visualizar os diagramas PlantUML, você pode:
- Usar plugins PlantUML disponíveis para IDEs como IntelliJ IDEA, Visual Studio Code ou Eclipse
- Usar o [PlantUML Server online](http://www.plantuml.com/plantuml/uml/) copiando o conteúdo dos arquivos .puml
- Visualizar diretamente no GitHub (se estiver usando o GitHub com extensões que suportam PlantUML)

## Tecnologias Utilizadas

- **Backend**:
  - Spring Boot 3.x
  - Spring Data JPA
  - H2 Database (para desenvolvimento)

- **Frontend**:
  - HTML5
  - CSS3
  - JavaScript (ES6+)

## Licença

Este projeto está licenciado sob a licença MIT - consulte o arquivo LICENSE para obter detalhes.