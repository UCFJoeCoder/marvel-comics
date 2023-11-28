package com.ucfjoe.marvelcomics.feature_marvel.data.remote.dto



data class Comic(
    val characters: CharacterList?,
    val collectedIssues: List<ComicSummary>?,
    val collections: List<ComicSummary>?,
    val creators: CreatorList?,
    val dates: List<ComicDate>?,
    val description: String?,
    val diamondCode: String?,
    val digitalId: Int?,
    val ean: String?,
    val events: EventList?,
    val format: String?,
    val id: Int?,
    val images: List<Image>?,
    val isbn: String?,
    val issn: String?,
    val issueNumber: Double?,
    val modified: String?,
    val pageCount: Int?,
    val prices: List<ComicPrice>?,
    val resourceURI: String?,
    val series: SeriesSummary?,
    val stories: StoryList?,
    val textObjects: List<TextObject>?,
    val thumbnail: Image?,
    val title: String?,
    val upc: String?,
    val urls: List<Url>?,
    val variantDescription: String?,
    val variants: List<ComicSummary>?
)