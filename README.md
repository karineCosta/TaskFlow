# TaskFlow - Android Application

Aplicativo Android para gerenciamento de tarefas e atividades corporativas, desenvolvido como projeto acadêmico incremental.

## Descrição do projeto

O TaskFlow foi desenvolvido para auxiliar colaboradores no gerenciamento de tarefas diárias, lembretes e informações importantes da empresa.

A proposta é centralizar atividades em uma única aplicação, reduzindo a dependência de anotações em papel ou aplicativos de mensagens e facilitando a organização das atividades.

## Problema que o aplicativo pretende resolver

Pequenas empresas podem utilizar diferentes meios para registrar e acompanhar atividades internas, o que pode causar esquecimentos, perda de informações e dificuldade de acompanhamento.

O TaskFlow busca centralizar essas informações e oferecer uma interface simples para criação, consulta e organização de tarefas.

## Plataforma

- Android
- Desenvolvido com Android Studio
- Linguagens utilizadas: Java e Kotlin
- Interface desenvolvida com XML

## Público-alvo

O aplicativo é direcionado principalmente a colaboradores de pequenas empresas que precisam organizar tarefas, atividades e informações relacionadas ao trabalho cotidiano.

Administradores também podem utilizar o aplicativo para organizar e acompanhar atividades.

## Interface do usuário

A interface do TaskFlow foi desenvolvida com foco em simplicidade, organização e facilidade de navegação.

Principais características:

- Interface minimalista;
- Paleta de cores azul e branca;
- Ícones e elementos visuais;
- Navegação por menus;
- Layout otimizado para smartphones Android;
- Uso de ScrollView para facilitar a visualização do conteúdo.

## Principais funcionalidades

- Cadastro de tarefas;
- Inserção de título e descrição;
- Exibição da tarefa cadastrada;
- Banners com navegação anterior/próximo;
- Galeria de funcionalidades;
- Menu de opções;
- Menu de contexto para tarefas;
- Página de ajuda utilizando WebView;
- Persistência de dados;
- Armazenamento de tarefas em arquivos;
- Exportação de tarefas;
- Banco de dados SQLite.

# Funcionalidades implementadas

## Entrada e gerenciamento de tarefas

O aplicativo permite que o usuário informe:

- Nome da tarefa;
- Descrição da tarefa.

Após o preenchimento, a tarefa pode ser salva através do botão "Salvar tarefa".

O aplicativo valida se o título foi informado antes de realizar o salvamento.

## ImageView

Um ImageView é utilizado para apresentar o logotipo do TaskFlow na interface principal.

## ImageSwitcher

O aplicativo utiliza ImageSwitcher para apresentar banners.

O usuário pode navegar entre as imagens utilizando os botões:

- Anterior;
- Próximo.

Também foram utilizadas animações simples de entrada e saída para realizar a transição entre as imagens.

## GridView

O TaskFlow utiliza GridView para apresentar uma organização visual em formato de grade, permitindo representar diferentes áreas e funcionalidades do aplicativo.

## Menus

### Menu de opções

O aplicativo possui um menu global com opções como:

- Atualizar;
- Sobre o TaskFlow;
- Ajuda;
- Ler tarefa salva;
- Exportar tarefa;
- Informações do TaskFlow;
- Ver tarefas salvas.

### Menu de contexto

O aplicativo possui um menu de contexto associado à área de tarefas.

Ao pressionar e segurar a tarefa, são apresentadas opções relacionadas ao conteúdo:

- Editar tarefa;
- Excluir tarefa.

## WebView

O TaskFlow possui uma Activity de ajuda utilizando WebView.

O WebView permite apresentar conteúdo da Web dentro do próprio aplicativo, sem depender da abertura de um navegador externo.

A navegação é controlada utilizando WebViewClient.

# Persistência de dados - Módulo 5

O Módulo 5 adicionou diferentes mecanismos de armazenamento ao aplicativo.

## SharedPreferences

O TaskFlow utiliza SharedPreferences para armazenar informações simples da última tarefa cadastrada.

São armazenados:

- `ultima_tarefa_titulo`
- `ultima_tarefa_descricao`

Os dados podem posteriormente ser recuperados por outra Activity utilizando `getSharedPreferences()`.

## Compartilhamento entre Activities

A `MainActivity` salva os dados da tarefa utilizando SharedPreferences.

A `HelpActivity` acessa o mesmo conjunto de preferências e recupera a última tarefa cadastrada.

Isso demonstra o compartilhamento de dados entre diferentes Activities do aplicativo.

## Armazenamento interno

As tarefas também são armazenadas em um arquivo interno chamado:

`taskflow_task.txt`

A gravação é realizada utilizando `FileOutputStream`.

## Leitura de arquivos internos

O aplicativo consegue ler o conteúdo do arquivo interno utilizando:

- `FileInputStream`;
- `InputStreamReader`.

O conteúdo recuperado pode ser apresentado na interface do aplicativo.

## Armazenamento externo

O TaskFlow possui uma funcionalidade de exportação da tarefa para o armazenamento externo.

A implementação utiliza `Environment.getExternalStorageDirectory()` e um diretório externo específico do aplicativo para realizar a gravação do arquivo.

O arquivo de exportação é:

`TaskFlow_tarefa.txt`

## Recursos em res/raw

Foi criado o arquivo:

`res/raw/taskflow_info.txt`

Esse arquivo contém informações sobre o projeto e suas funcionalidades.

O aplicativo utiliza `openRawResource()` para acessar e ler o conteúdo desse recurso.

## Banco de dados SQLite

Foi criada a classe `DatabaseHelper`, que estende:

`SQLiteOpenHelper`

O banco utilizado pelo aplicativo é:

`TaskFlow.db`

A tabela principal é:

`tarefas`

Com os campos:

- `id`;
- `titulo`;
- `descricao`.

O aplicativo consegue:

- Inserir tarefas no banco;
- Consultar tarefas armazenadas;
- Exibir as tarefas cadastradas na interface.

## Organização do projeto

A aplicação utiliza diferentes componentes Android para separar responsabilidades, incluindo:

- `MainActivity` — tela principal e interação com o usuário;
- `HelpActivity` — apresentação do conteúdo Web;
- `DatabaseHelper` — gerenciamento do banco SQLite;
- `GridAdapter` — organização dos elementos apresentados no GridView;
- Arquivos XML — definição das interfaces e menus;
- `res/raw` — armazenamento de recursos de texto utilizados pelo aplicativo.

## Tecnologias e componentes utilizados

- Android Studio
- Java
- Kotlin
- XML
- Android SDK
- ImageView
- ImageSwitcher
- GridView
- EditText
- ScrollView
- WebView
- WebViewClient
- SharedPreferences
- FileOutputStream
- FileInputStream
- InputStreamReader
- SQLite
- SQLiteOpenHelper
- Menus e menus de contexto

## Evolução do projeto

O TaskFlow está sendo desenvolvido de forma incremental ao longo dos módulos da disciplina.

As etapas mais recentes adicionaram:

### Módulo 3
- Estruturação da interface em XML;
- Entrada de dados com EditText;
- Eventos de clique;
- Recuperação dos valores digitados;
- ScrollView.

### Módulo 4
- Componentes visuais;
- ImageView;
- ImageSwitcher;
- GridView;
- Métodos auxiliares;
- Menu de opções;
- Menu de contexto;
- WebView.

### Módulo 5
- Persistência com SharedPreferences;
- Compartilhamento de dados entre Activities;
- Arquivos internos;
- Leitura de arquivos;
- Armazenamento externo;
- Recursos em res/raw;
- Banco de dados SQLite.

## Próximas etapas

As próximas versões do projeto continuarão incorporando os conceitos apresentados nos módulos seguintes da disciplina, ampliando as funcionalidades do TaskFlow e sua integração com recursos do Android.

## Objetivo acadêmico

O TaskFlow é um projeto desenvolvido para fins acadêmicos, com o objetivo de aplicar progressivamente conceitos de desenvolvimento de aplicativos Android, organização de código, interfaces, persistência de dados e interação com os recursos da plataforma.
