package com.mrunknown101331.pokerpay

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryLayout(modifier: Modifier = Modifier, navController: NavController) {
    var gameId by rememberSaveable { mutableStateOf("") }
    var openNGDialog by rememberSaveable { mutableStateOf(false) }
    var openOGDialog by rememberSaveable { mutableStateOf(false) }
    var showToast by rememberSaveable { mutableStateOf("") }
    var noPlayer by rememberSaveable { mutableIntStateOf(0) }
    val database: DatabaseReference = Firebase.database.reference

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Poker Pay")
        }
    )
    ConstraintLayout {
        val upGuideline = createGuidelineFromTop(0.15f)
        val topGuideline = createGuidelineFromTop(0.25f)
        val midGuideline = createGuidelineFromTop(0.45f)
        val lowGuideline = createGuidelineFromTop(0.45f)
        val (logoImg, txt, nGame, oGame) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.poker_pay),
            contentDescription = "Logo for App",
            modifier = modifier.constrainAs(logoImg) {
                top.linkTo(upGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(topGuideline)
            }
        )
        Text(text = "Select mode",
            modifier = modifier.constrainAs(txt) {
                top.linkTo(topGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(midGuideline)
            })
        ElevatedButton(
            onClick = { openNGDialog = true },
            elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
            modifier = modifier.constrainAs(nGame) {
                top.linkTo(midGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(lowGuideline)
            }
        ) {
            Text(text = "New Game")
        }
        OutlinedCard(
            modifier = modifier
                .constrainAs(oGame) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .width((0.8f * LocalConfiguration.current.screenWidthDp).dp)
                .heightIn()
        ) {
            Column(modifier = modifier.align(Alignment.CenterHorizontally)) {
                OutlinedTextField(
                    value = gameId,
                    onValueChange = { newId ->
                        gameId = newId.uppercase()
                    },
                    singleLine = true,
                    placeholder = {
                        Text(text = "Game ID")
                    },
                    label = {
                        Text(text = "Enter a Game ID")
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(0.dp, (-25).dp)
                )
                ElevatedButton(
                    onClick = {
                        if (gameId.isEmpty())
                            showToast = "Please enter a valid Game ID"
                        else {
                            val gameRef = database.child("Games/$gameId/numPlayers")

                            gameRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        noPlayer = snapshot.getValue<Int>() as Int
                                        openOGDialog = true
                                    } else
                                        showToast = "Game ID does not exist"
                                }

                                override fun onCancelled(error: DatabaseError) {}
                            })
                        }
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(0.dp, (-50).dp)
                ) {
                    Text(text = "Join Game")
                }
            }
        }
    }

    if (openOGDialog) {
        NGDialog(
            onDismissRequest = { openOGDialog = false },
            onConfirmation = { playerID ->
                if (playerID.isEmpty())
                    showToast = "Please enter a valid Player ID"
                else {
                    if (playerID.isEmpty())
                        showToast = "Please enter a valid Player ID"
                    else {
                        openOGDialog = false
                        initializeOldGame(gameId, playerID, noPlayer)
                        navController.navigate("Final/$gameId/$playerID")
                    }
                }
            }
        )
    }

    if (openNGDialog) {
        NGDialog(
            onDismissRequest = { openNGDialog = false },
            onConfirmation = { playerID ->
                if (playerID.isEmpty())
                    showToast = "Please enter a valid Player ID"
                else {
                    val random = Math.round(Math.random() * 9999 + 1)
                    gameId = "GAME$random"
                    openNGDialog = false
                    initializeNewGame(gameId, playerID)
                    navController.navigate("Final/$gameId/$playerID")
                }
            }
        )
    }

    if (showToast.isNotEmpty()) {
        Toast.makeText(LocalContext.current, showToast, Toast.LENGTH_SHORT)
            .show()
        showToast = ""
    }
}

fun initializeNewGame(gameId: String, playerID: String) {
    val database = Firebase.database.reference

    database.child("Games/$gameId/Pile").setValue(0)
    database.child("Games/$gameId/Round").setValue(1)
    database.child("Games/$gameId/currentBet").setValue(3)
    database.child("Games/$gameId/numFolded").setValue(0)
    database.child("Games/$gameId/numPlayers").setValue(1)
    database.child("Games/$gameId/numBets").setValue(0)

    database.child("Games/$gameId/Players/$playerID/Chips").setValue(15)
    database.child("Games/$gameId/Players/$playerID/TotalBet").setValue(0)
    database.child("Games/$gameId/Players/$playerID/isDealer").setValue(true)
    database.child("Games/$gameId/Players/$playerID/isFolded").setValue(false)
    database.child("Games/$gameId/Players/$playerID/hasPlacedBet").setValue(false)
}


fun initializeOldGame(gameId: String, playerID: String, noPlayer: Int) {
    val database = Firebase.database.reference
    val playerRef = database.child("Games/$gameId/Players/$playerID")

    playerRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (!snapshot.exists()) {
                database.child("Games/$gameId/numPlayers").setValue(noPlayer.plus(1))

                database.child("Games/$gameId/Players/$playerID/Chips").setValue(15)
                database.child("Games/$gameId/Players/$playerID/TotalBet").setValue(0)
                database.child("Games/$gameId/Players/$playerID/isDealer").setValue(false)
                database.child("Games/$gameId/Players/$playerID/isFolded").setValue(false)
                database.child("Games/$gameId/Players/$playerID/hasPlacedBet").setValue(false)
            }
        }

        override fun onCancelled(error: DatabaseError) {}
    })
}
