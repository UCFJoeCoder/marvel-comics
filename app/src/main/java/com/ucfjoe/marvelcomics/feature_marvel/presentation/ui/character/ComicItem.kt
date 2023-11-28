package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ucfjoe.marvelcomics.feature_marvel.domain.model.Comic

@Composable
fun ComicItem(comic: Comic) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(6.dp)
        ){
            Text("Issue # ${comic.issueNumber}")
            Text(text = comic.title, fontWeight = FontWeight.Bold)
            if (comic.description.isNotBlank()) {
                Text(comic.description)
            }
        }
    }
}