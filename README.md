# inChurch Recruitment Process 2020 - Android Developer

Nessa parte do processo de recrutamento você desenvolverá uma aplicação Android. O desafio deve ser desenvolvido em Kotlin ou Java e utilizando libs conhecidas de mercado. A aplicação será um catálogo dos filmes populares, utilizando a [API](https://developers.themoviedb.org/3/getting-started/introduction) do [TheMovieDB](https://www.themoviedb.org/).

* * *

## O que foi feito:

+ ### Obrigatório:
    * OK - Tela de Listagem de Filmes exibindo os filmes melhores classificados. Utilizar esse [endpoint](https://developers.themoviedb.org/3/movies/get-popular-movies).
	* OK - Loading no carregamento da listagem de filmes.
	* OK -Tratamento de erros(falta de internet e erro na api) na tela de Listagem de Filmes.
	* OK -Tela de Favoritos com a listagem dos filmes marcados como favorito. Essa tela será acessada no ícone de favoritos na toolbar da Listagem de Filmes.
	* OK - Tela de detalhe do filme. Para as informações de gêneros do filme, utilize esse [endpoint](https://developers.themoviedb.org/3/genres/get-movie-list).
	* OK - Ação de favoritar um filme na tela de detalhe. Todo o controle será em armazenamento local.

#
+ ### Pontos extras:
	* OK - Paginação com scroll infinito na tela de filmes.
	* OK - Filtro de busca pelo nome do filme na tela de Favoritos. Exibir uma tela diferente para quando não houver resultado na busca.
	* OK - Ação de remover o filme da lista de Favoritos.
	* OK - Testes unitários.
	* NOT OK - Testes funcionais.
#
* * *
+ ## Sobre o projeto
    * Arquitetura geral: Clean Architecture com metodologia TDD
    * Arquitetura da camada de apresentação: MVVM
    * Total de testes unitários: 57
    * Injeção de dependência com Kotlin Koin
    * Requisição com Retrofit 2.6.0
    * Assincronicidade com Kotlin Coroutines
#
* * *
* ## Bibliotecas utilizadas
    * Retrofit 2.6.0 - Para API, possui uma boa integração com Kotlin Coroutines
    * OkHttp3 e Moshi - Suporte a api, um para trabalhar com cliente e Moshi para conversão de Json para objetos
    * Kotlin Coroutines - Programar assincronamente, principalmente na hora de fazer requisições
    * Kotlin Koin - Para injeção de dependência
    * Picasso - Loading de imagens (poderia ser o Glide também)
    * Mockk - Biblioteca de Mock para mockar objetos nos testes
    * Junit 4.12 - Para testes
    * LifeCycle - Para controle de ciclo de vida do view model
    
#
* * *
* ## Por quê Clean Architecture?
    * O motivo a adoção da Clean Architecture foi para deixar o aplicativo com baixo acoplamento e mais testável 
#
* * *
* ## Observações
* Há duas implementações para o MovieLocalDataSource. Isso porque uma implementação salva os favoritos na memória da aplicação, então ao fechar o aplicativo os favoritos são perdidos. Enquanto a outra implementação salva os favoritos no sharedPreferences. A implementação default é a com sharedPreferences. Porém, descomentando a primeira e comentando a segunda linha, o aplicativo passa a usar a implementação que salva na memória da aplicação.
    ```javascript
    // Linha 27 and 28 de DependencyModules 
    // single<MovieLocalDataSource> { MovieLocalDataSourceImpl() }
       single<MovieLocalDataSource> { MovieLocalDataSourceSharedPrefsImpl(get(), get()) }
    ```
#
* * *
## Email
* E-mail: matheusfelipecorreaalves@gmail.com
