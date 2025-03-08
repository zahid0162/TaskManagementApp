package com.zahid.taskmaster.presentation.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun BodyLargeText(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = style,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis

    )
}

@Composable
fun DisplayMediumText(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displayMedium,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis

    )
}

@Composable
fun WarningText(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.error,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.bodySmall
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = style


    )
}

@Composable
fun HeadlineSmallText(
    modifier: Modifier = Modifier.padding(7.dp),
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    textDecoration: TextDecoration? = null,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier,
        text = text,

        color = color,
        style = style,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textDecoration = textDecoration,

        )
}

@Composable
fun NavTitleText(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = style,
        textAlign = textAlign,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,

        )
}

@Composable
fun BodySmallText(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis

    )
}

@Composable
fun LabelSmallText(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelSmall,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis

    )
}


