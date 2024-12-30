package com.mrunknown101331.pokerpay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun NGDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit
) {
    var playerId by rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = playerId,
                    onValueChange = { newId ->
                        playerId = newId.uppercase()
                    },
                    singleLine = true,
                    placeholder = {
                        Text(text = "Player ID")
                    },
                    label = {
                        Text(text = "Enter Player ID")
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onConfirmation(playerId) }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun WinnerDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit
) {
    var playerId by rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = playerId,
                    onValueChange = { newId ->
                        playerId = newId.uppercase()
                    },
                    singleLine = true,
                    placeholder = {
                        Text(text = "Player ID")
                    },
                    label = {
                        Text(text = "Enter Winner's Player ID")
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onConfirmation(playerId) }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun RaiseDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (Int) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Select Raise Amount")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    ElevatedButton(onClick = { onConfirmation(1) }) {
                        Text(text = "1")
                    }
                    ElevatedButton(onClick = { onConfirmation(2) }) {
                        Text(text = "2")
                    }
                    ElevatedButton(onClick = { onConfirmation(5) }) {
                        Text(text = "5")
                    }
                    ElevatedButton(onClick = { onConfirmation(10) }) {
                        Text(text = "10")
                    }
                }
            }
        }
    }
}