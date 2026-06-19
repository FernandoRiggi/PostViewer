# PostViewer

**Aluno:** Fernando Riggi
**Disciplina:** Programação para Dispositivos Móveis

## Descrição

O **PostViewer** é um aplicativo Android desenvolvido em Kotlin que consome a API pública JSONPlaceholder para exibir uma lista de posts e os comentários associados a cada post.

O aplicativo também permite adicionar comentários locais, que são salvos no dispositivo com Room e associados ao ID do post correspondente.

## Requisitos implementados

* Listagem de posts da API
* Tela de detalhes com comentários da API
* Navegação entre lista e detalhes
* Adição de comentários locais
* Persistência local dos comentários com Room
* Tratamento de estados de carregamento e erro

## Como executar

1. Clone o repositório:

```bash
git clone URL_DO_REPOSITORIO
```

2. Abra o projeto no Android Studio.

3. Aguarde a sincronização do Gradle.

4. Execute o app em um emulador ou dispositivo Android com Android 8.0, API 26, ou superior.

## Tecnologias utilizadas

* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose
* Retrofit
* Room
* KSP
* ViewModel
* StateFlow
* Coroutines

## Decisões de design

O projeto foi organizado separando responsabilidades entre acesso remoto, persistência local, repositório e interface.

A comunicação com a API foi concentrada na camada `remote`, usando Retrofit.
A persistência dos comentários locais foi implementada na camada `local`, usando Room.
O `PostRepository` centraliza o acesso aos dados da API e do banco local.
As telas utilizam ViewModel com StateFlow para representar estados de carregamento, erro e sucesso.

A interface foi construída com Jetpack Compose e Material 3, utilizando tema escuro e componentes simples para manter a navegação e leitura dos dados claras.
