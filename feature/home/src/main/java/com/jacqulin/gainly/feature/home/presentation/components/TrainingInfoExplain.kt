/*
package com.jacqulin.gainly.feature.history.presentation.sections.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jacqulin.gainly.core.designsystem.R
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.GoogleSansFontFamily
import com.jacqulin.gainly.feature.history.presentation.model.ExerciseInfoUi
import com.jacqulin.gainly.feature.history.presentation.model.SetInfoUi

@Composable
fun TrainingInfoExplain(
    trainingInfo: ExerciseInfoUi
) {
    Column {
        TrainingInfoRow(
            iconRes = R.drawable.ic_repeats,
            text = formatReps(trainingInfo.sets),
        )
        TrainingInfoRow(
            iconRes = R.drawable.ic_weight,
            text = formatWeight(trainingInfo.sets),
        )
    }
}

@Composable
private fun TrainingInfoRow(iconRes: Int, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = text,
            style = TextStyle(
                fontFamily = GoogleSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.sp
            )
        )
    }
}

private fun formatReps(sets: List<SetInfoUi>): String {
    if (sets.isEmpty()) return "—"
    return "${sets.size}/" + sets.joinToString("-") { it.reps.toString() }
}

private fun formatWeight(sets: List<SetInfoUi>): String {
    if (sets.isEmpty()) return "—"

    if (sets.all { it.isOwnWeight }) return "Свой вес"

    val allSame = sets.all { !it.isOwnWeight && it.weight == sets.first().weight }
    if (allSame) return "${sets.first().weight} кг"

    return sets.joinToString("-") {
        if (it.isOwnWeight) "СВ" else it.weight.toString()
    } + " кг"
}

@Preview(name = "Один вес", showBackground = true)
@Composable
fun TrainingInfoExplainPreview_SameWeight() {
    GainlyTheme {
        TrainingInfoExplain(
            trainingInfo = ExerciseInfoUi(
                title = "Подтягивания",
                sets = listOf(
                    SetInfoUi(1, 10, false),
                    SetInfoUi(3, 10, false),
                    SetInfoUi(2, 10, false),
                )
            )
        )
    }
}

@Preview(name = "Разный вес", showBackground = true)
@Composable
fun TrainingInfoExplainPreview_DifferentWeights() {
    GainlyTheme {
        TrainingInfoExplain(
            trainingInfo = ExerciseInfoUi(
                title = "Жим лёжа",
                sets = listOf(
                    SetInfoUi(12, 10, false),
                    SetInfoUi(6, 15, false),
                    SetInfoUi(8, 20, false),
                )
            )
        )
    }
}

@Preview(name = "Свой вес", showBackground = true)
@Composable
fun TrainingInfoExplainPreview_OwnWeight() {
    GainlyTheme {
        TrainingInfoExplain(
            trainingInfo = ExerciseInfoUi(
                title = "Отжимания",
                sets = listOf(
                    SetInfoUi(15, 0, true),
                    SetInfoUi(12, 0, true),
                    SetInfoUi(10, 0, true),
                )
            )
        )
    }
}

@Preview(name = "Смешанный вес", showBackground = true)
@Composable
fun TrainingInfoExplainPreview_MixedWeight() {
    GainlyTheme {
        TrainingInfoExplain(
            trainingInfo = ExerciseInfoUi(
                title = "Тяга",
                sets = listOf(
                    SetInfoUi(10, 0, true),
                    SetInfoUi(8, 15, false),
                    SetInfoUi(6, 20, false),
                )
            )
        )
    }
}

@Preview(name = "Один подход", showBackground = true)
@Composable
fun TrainingInfoExplainPreview_SingleSet() {
    GainlyTheme {
        TrainingInfoExplain(
            trainingInfo = ExerciseInfoUi(
                title = "Становая",
                sets = listOf(
                    SetInfoUi(5, 100, false),
                )
            )
        )
    }
}

@Preview(name = "Пустой список", showBackground = true)
@Composable
fun TrainingInfoExplainPreview_Empty() {
    GainlyTheme {
        TrainingInfoExplain(
            trainingInfo = ExerciseInfoUi(
                title = "Приседания",
                sets = emptyList(),
            )
        )
    }
}
*/
