package matheusfelipe.desafio.inchurch.data.models

import matheusfelipe.desafio.inchurch.domain.entities.Genre

data class GenreModel(
    val id: Int,
    val name: String?
){

    fun toEntity(): Genre{
        return Genre(
            this.id,
            this.name ?: ""
        )
    }
}