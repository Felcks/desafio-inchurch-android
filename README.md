# inChurch Recruitment Process 2020 - Android Developer

Nessa parte do processo de recrutamento voc� desenvolver� uma aplica��o Android. O desafio deve ser desenvolvido em Kotlin ou Java e utilizando libs conhecidas de mercado. A aplica��o ser� um cat�logo dos filmes populares, utilizando a [API](https://developers.themoviedb.org/3/getting-started/introduction) do [TheMovieDB](https://www.themoviedb.org/).

* * *

## O que foi feito:

+ ### Obrigat�rio:
    * OK - Tela de Listagem de Filmes exibindo os filmes melhores classificados. Utilizar esse [endpoint](https://developers.themoviedb.org/3/movies/get-popular-movies).
	* OK - Loading no carregamento da listagem de filmes.
	* OK -Tratamento de erros(falta de internet e erro na api) na tela de Listagem de Filmes.
	* OK -Tela de Favoritos com a listagem dos filmes marcados como favorito. Essa tela ser� acessada no �cone de favoritos na toolbar da Listagem de Filmes.
	* OK - Tela de detalhe do filme. Para as informa��es de g�neros do filme, utilize esse [endpoint](https://developers.themoviedb.org/3/genres/get-movie-list).
	* OK - A��o de favoritar um filme na tela de detalhe. Todo o controle ser� em armazenamento local.

#
+ ### Pontos extras:
	* OK - Pagina��o com scroll infinito na tela de filmes.
	* OK - Filtro de busca pelo nome do filme na tela de Favoritos. Exibir uma tela diferente para quando n�o houver resultado na busca.
	* OK - A��o de remover o filme da lista de Favoritos.
	* OK - Testes unit�rios.
	* NOT OK - Testes funcionais.
#
* * *
+ ## Sobre o projeto
    * Arquitetura geral: Clean Architecture com metodologia TDD
    * Arquitetura da camada de apresenta��o: MVVM
    * Total de testes unit�rios: 57
    * Inje��o de depend�ncia com Kotlin Koin
    * Requisi��o com Retrofit 2.6.0
    * Assincronicidade com Kotlin Coroutines
#
* * *
* ## Bibliotecas utilizadas
    * Retrofit 2.6.0 - Para interface da API, possui uma boa integra��o com Kotlin Coroutines
    * OkHttp3 e Moshi - Suporte a api, um para trabalhar com cliente e Moshi para convers�o de Json para objetos
    * Kotlin Coroutines - Programar assincronamente, principalmente na hora de fazer requisi��es
    * Kotlin Koin - Para inje��o de depend�ncia
    * Picasso - Loading de imagens (poderia ser o Glide tamb�m)
    * Mockk - Biblioteca de Mock para mockar objetos nos testes
    * Junit 4.12 - Testes
    * LifeCycle - Para controle de ciclo de vida do viewmodel
    
#
* * *
* ## Por qu� Clean Architecture?
    * O motivo a ado��o da Clean Architecture foi para deixar o aplicativo com baixo acoplamento
#
* * *
* ## Observa��es
* H� duas implementa��es para o MovieLocalDataSource. Isso porque uma implementa��o salva os favoritos na mem�ria da aplica��o, ent�o ao fechar o aplicativo os favoritos s�o perdidos. Enquanto a outra implementa��o salva os favoritos no sharedPreferences. A implementa��o default � a com sharedPreferences. Por�m, descomentando a primeira e comentando a segunda linha, o aplicativo passa a usar a implementa��o que salva na mem�ria da aplica��o.
```
// Linha 27 and 28 de DependencyModules 
// single<MovieLocalDataSource> { MovieLocalDataSourceImpl() }
single<MovieLocalDataSource> { MovieLocalDataSourceSharedPrefsImpl(get(), get()) }
```
#
* * *
## Email
* E-mail: matheusfelipecorreaalves@gmail.com
