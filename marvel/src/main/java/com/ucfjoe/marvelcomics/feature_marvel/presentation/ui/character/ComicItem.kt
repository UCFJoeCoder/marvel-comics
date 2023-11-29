package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ComicItem(comic: Comic) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            comic.onSaleDate?.let {
                Text(
                    text = it.format(
                        DateTimeFormatter.ofLocalizedDate(
                            FormatStyle.LONG
                        )
                    )
                )
            }
            Text(text = comic.title, fontWeight = FontWeight.Bold)
            comic.thumbnail?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Comic Image",
                    modifier = Modifier.width(200.dp))
            }
            comic.pageCount?.let {
                Text("${comic.pageCount} pages")
            }
            comic.description?.let {
                Text(it)
            }
            comic.characters?.let {
                Text("Characters", fontWeight = FontWeight.Bold)
                val bullet = "\u2022"
                val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
                Text(
                    buildAnnotatedString {
                        it.forEach {
                            withStyle(style = paragraphStyle) {
                                append(bullet)
                                append("\t\t")
                                append(it)
                            }
                        }
                    }
                )
            }
        }
    }
}