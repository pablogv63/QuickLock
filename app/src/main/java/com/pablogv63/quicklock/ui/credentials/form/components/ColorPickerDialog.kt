package com.pablogv63.quicklock.ui.credentials.form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker

@Composable
fun ColorPickerDialog(
    onColorChanged: (HsvColor) -> Unit,
    modifier: Modifier = Modifier,
    initialColor: Color = MaterialTheme.colorScheme.primary
){
    HarmonyColorPicker(
        harmonyMode = ColorHarmonyMode.NONE,
        modifier = modifier,
        color = initialColor,
        onColorChanged = onColorChanged
    )
}

@Preview
@Composable
fun PreviewColorPickerDialog(){
    ClassicColorPicker(
        onColorChanged = { color: HsvColor ->
            // Do something with the color
        },
    )
}

@Preview
@Composable
fun PreviewOtherPickerDialog(){
    Column {
        HarmonyColorPicker(
            harmonyMode = ColorHarmonyMode.NONE,
            modifier = Modifier.size(400.dp),
            onColorChanged = { hsvColor ->
                // do stuff with new color
            })
    }
}