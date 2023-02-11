package com.ravisharma.zen_music.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ravisharma.zen_music.R

@Composable
fun FullScreenSadMessage(
    message: String? = null,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
) = Column(
    modifier = modifier
        .fillMaxSize()
        .padding(paddingValues)
        .alpha(0.4f),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = {
        Icon(
            modifier = Modifier
                .weight(1f,false)
                .aspectRatio(1f,false)
                .fillMaxSize()
                .padding(24.dp),
            painter = painterResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_40),
            contentDescription = "sad-face",
        )
        message?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                text = it,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    }
)