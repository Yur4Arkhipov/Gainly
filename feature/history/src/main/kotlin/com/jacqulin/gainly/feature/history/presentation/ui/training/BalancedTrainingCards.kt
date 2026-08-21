package com.jacqulin.gainly.feature.history.presentation.ui.training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.feature.history.presentation.model.TrainingCardModel
import kotlin.math.abs

@Composable
fun BalancedTrainingCards(
    trainingCards: List<TrainingCardModel>,
    modifier: Modifier = Modifier
) {
    val visibleCards = remember(trainingCards) {
        trainingCards.takeLast(6)
    }

    val distribution = remember(visibleCards.map { it.id }) {
        findBestDistribution(
            cards = visibleCards
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            distribution.first.forEach { index ->
                TrainingCard(
                    modifier = Modifier.fillMaxWidth(),
                    trainingInfoList = visibleCards[index].trainingInfoList,
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            distribution.second.forEach { index ->
                TrainingCard(
                    modifier = Modifier.fillMaxWidth(),
                    trainingInfoList = visibleCards[index].trainingInfoList
                )
            }
        }
    }
}

private fun findBestDistribution(
    cards: List<TrainingCardModel>
): Pair<List<Int>, List<Int>> {

    if (cards.size <= 1) {
        return listOf(0).take(cards.size) to emptyList()
    }

    var bestLeft = emptyList<Int>()
    var bestRight = emptyList<Int>()

    var bestHeight = Int.MAX_VALUE
    var bestDifference = Int.MAX_VALUE

    for (mask in 0 until (1 shl cards.size)) {

        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        cards.indices.forEach { index ->
            if ((mask and (1 shl index)) != 0) {
                left += index
            } else {
                right += index
            }
        }

        if (left.isEmpty() || right.isEmpty()) {
            continue
        }

        val leftHeight = left.sumOf {
            estimateCardHeight(cards[it])
        }

        val rightHeight = right.sumOf {
            estimateCardHeight(cards[it])
        }

        val maxHeight = maxOf(
            leftHeight,
            rightHeight
        )

        val difference = abs(
            leftHeight - rightHeight
        )

        if (maxHeight < bestHeight ||
            (maxHeight == bestHeight && difference < bestDifference)
        ) {
            bestHeight = maxHeight
            bestDifference = difference
            bestLeft = left
            bestRight = right
        }
    }

    return bestLeft to bestRight
}

private fun estimateCardHeight(
    card: TrainingCardModel
): Int {
    val headerHeight = 60
    val trainingHeight = 45
    val padding = 20
    val spacing = 8

    return headerHeight +
            padding +
            card.trainingInfoList.size * trainingHeight +
            (card.trainingInfoList.size - 1).coerceAtLeast(0) * spacing
}