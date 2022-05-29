package com.pablogv63.quicklock.ui.generator.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pablogv63.quicklock.ui.tools.AppPaddingValues
import java.lang.Integer.min
import kotlin.math.roundToInt

const val MIN_LENGTH = 1f
const val MAX_LENGTH = 30f


@ExperimentalMaterial3Api
@Composable
fun LengthField(
    title: String,
    length: Int,
    onValueChange: (Int) -> Unit
) {
    // Slider logic from:
    // https://www.develou.com/slider-en-compose/#Modificar_Valor_Seleccionado_Por_Campo_De_Texto
    val range = MIN_LENGTH..MAX_LENGTH
    val steps = (MAX_LENGTH - MIN_LENGTH).toInt()
    var sliderSelection by remember {
        mutableStateOf(length.toFloat())
    }
    var selectionNumber by remember { mutableStateOf(length.toString()) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.width(AppPaddingValues.Medium))
            TextField(
                modifier = Modifier.width(50.dp),
                value = selectionNumber,
                onValueChange = {
                    var newValue = it.ifBlank { "1" }
                    newValue = min(newValue.toInt(), MAX_LENGTH.toInt()).toString()
                    val segment = calculateSegment(newValue, range, steps)
                    sliderSelection = segment
                    selectionNumber = newValue
                    onValueChange(newValue.toInt())
                },
                textStyle = MaterialTheme.typography.labelLarge,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.primary
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.width(AppPaddingValues.Medium))
            Slider(
                modifier = Modifier.weight(0.9f),
                value = sliderSelection,
                onValueChange = { sliderSelection = it },
                valueRange = range,
                onValueChangeFinished = {
                    // launch some business logic update with the state you hold
                    // viewModel.updateSelectedSliderValue(sliderPosition)
                    selectionNumber = sliderSelection.toInt().toString()
                    onValueChange(sliderSelection.toInt())
                },
                steps = (MAX_LENGTH - MIN_LENGTH).toInt()
            )

            Spacer(modifier = Modifier.width(AppPaddingValues.Medium))
            Text(
                text = MAX_LENGTH.toInt().toString(),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

fun calculateSegment(input: String, range: ClosedFloatingPointRange<Float>, steps: Int): Float {
    if (input.isBlank()) return 0.0F

    val selection = input.toFloat()

    if (selection >= range.endInclusive) return range.endInclusive

    val segments = steps + 1
    val subRangeSize = (range.endInclusive - range.start) / segments

    val fraction: Float = range.endInclusive / selection
    val location = (segments / fraction).roundToInt()

    return location * subRangeSize
}

fun inRange(value: Float): Boolean{
    return value in MIN_LENGTH..MAX_LENGTH
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewLengthField(){
    LengthField(
        title = "Length",
        length = 22,
        onValueChange = {}
    )
}