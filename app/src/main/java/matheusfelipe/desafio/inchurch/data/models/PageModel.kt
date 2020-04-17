package matheusfelipe.desafio.inchurch.data.models

data class PageModel(
    val page: Int,
    val results: List<MovieModel>,
    val total_results: Int,
    val total_pages: Int
)