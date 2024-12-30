package com.mrunknown101331.pokerpay

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Thread.sleep
import java.util.StringTokenizer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalLayout(
    modifier: Modifier = Modifier,
    navController: NavController,
    gameID: String,
    playerID: String
) {
    val database: DatabaseReference = Firebase.database.reference
    var round by rememberSaveable { mutableIntStateOf(0) }
    var chips by rememberSaveable { mutableIntStateOf(0) }
    var bet by rememberSaveable { mutableIntStateOf(0) }
    var noPlayers by rememberSaveable { mutableIntStateOf(0) }
    var numFolded by rememberSaveable { mutableIntStateOf(0) }
    var numBets by rememberSaveable { mutableIntStateOf(0) }
    var pile by rememberSaveable { mutableIntStateOf(0) }
    var isDealer by rememberSaveable { mutableStateOf(false) }
    var isFolded by rememberSaveable { mutableStateOf(false) }
    var hasPlacedBet by rememberSaveable { mutableStateOf(false) }
    var totalBet by rememberSaveable { mutableIntStateOf(0) }

    var call by rememberSaveable { mutableStateOf(false) }
    var fold by rememberSaveable { mutableStateOf(false) }
    var raise by rememberSaveable { mutableIntStateOf(0) }
    var raiseDialog by rememberSaveable { mutableStateOf(false) }
    var incRound by rememberSaveable { mutableStateOf(false) }
    var nxtGame by rememberSaveable { mutableStateOf(false) }
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var showToast by rememberSaveable { mutableStateOf("") }

    val roundListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                round = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val chipsListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                chips = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val noPlayersListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                noPlayers = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val pileListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                pile = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val noFoldedListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                numFolded = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val noBetsListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                numBets = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val betListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                bet = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val totalBetListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                totalBet = dataSnapshot.getValue<Int>() as Int
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val dealerListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                isDealer = dataSnapshot.getValue<Boolean>() as Boolean
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val foldedListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                isFolded = dataSnapshot.getValue<Boolean>() as Boolean
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }
    val hasPlacedBetListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists())
                hasPlacedBet = dataSnapshot.getValue<Boolean>() as Boolean
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled")
        }
    }

    database.child("Games/$gameID/Round").addValueEventListener(roundListener)
    database.child("Games/$gameID/currentBet").addValueEventListener(betListener)
    database.child("Games/$gameID/numPlayers").addValueEventListener(noPlayersListener)
    database.child("Games/$gameID/numFolded").addValueEventListener(noFoldedListener)
    database.child("Games/$gameID/Pile").addValueEventListener(pileListener)
    database.child("Games/$gameID/Players/$playerID/Chips").addValueEventListener(chipsListener)
    database.child("Games/$gameID/Players/$playerID/isDealer").addValueEventListener(dealerListener)
    database.child("Games/$gameID/Players/$playerID/isFolded").addValueEventListener(foldedListener)
    database.child("Games/$gameID/numBets").addValueEventListener(noBetsListener)
    database.child("Games/$gameID/Players/$playerID/TotalBet")
        .addValueEventListener(totalBetListener)
    database.child("Games/$gameID/Players/$playerID/hasPlacedBet")
        .addValueEventListener(hasPlacedBetListener)

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Poker Pay")
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back Button"
                )
            }
        }
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.poker_pay),
            contentDescription = "Logo for App",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        ElevatedCard(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width((0.8f * LocalConfiguration.current.screenWidthDp).dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Game ID : $gameID", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Round : $round", style = MaterialTheme.typography.titleMedium)
                Text(text = "Current Bet : $bet", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Player ID : $playerID", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Chips : $chips", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Folded : ${if (isFolded) "Yes" else "No"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Called : ${if (hasPlacedBet) "Yes" else "No"}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No. of Players : $noPlayers",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "Folded : $numFolded", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Current Pile : $pile", style = MaterialTheme.typography.bodyMedium)
            }

        }
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ElevatedButton(
                onClick = { fold = true },
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
            ) {
                Text(text = "Fold")
            }
            ElevatedButton(
                onClick = { call = true },
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
            ) {
                Text(text = "Call")
            }
            ElevatedButton(
                onClick = { raiseDialog = true },
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
            ) {
                Text(text = "Raise")
            }
        }

        if (isDealer) {
            ElevatedCard(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width((0.8f * LocalConfiguration.current.screenWidthDp).dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Folded = $numFolded")
                    Text(text = "Bets = $numBets")
                    val tot = numFolded + numBets
                    Text(text = "Fold + Call = $tot/$noPlayers")
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ElevatedButton(onClick = { incRound = true }) {
                            Text(text = "Next Round")
                        }
                        ElevatedButton(onClick = { nxtGame = true }) {
                            Text(text = "Next Game")
                        }
                    }
                }
            }
        }
    }

    if (incRound) {
        if (numBets + numFolded != noPlayers)
            Toast.makeText(LocalContext.current, "Not All Called", Toast.LENGTH_SHORT).show()
        else if (round == 4)
            Toast.makeText(
                LocalContext.current,
                "Already at the end of the game",
                Toast.LENGTH_SHORT
            ).show()
        else {
            database.child("Games/$gameID/Round").ref.setValue(ServerValue.increment(1))
            database.child("Games/$gameID/numBets").setValue(0)

            val playersRef = database.child("Games/$gameID/Players")
            playersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (playerSnapshot in snapshot.children) {
                        playerSnapshot.child("hasPlacedBet").ref.setValue(false)
                        if (playerSnapshot.child("Chips").getValue<Int>() as Int == 0) {
                            playerSnapshot.child("isFolded").ref.setValue(true)
                            database.child("Games/$gameID/numFolded").ref.setValue(
                                ServerValue.increment(
                                    1
                                )
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
        incRound = false
    }

    if (nxtGame) {
        if (round != 4 || (numBets + numFolded != noPlayers))
            Toast.makeText(LocalContext.current, "Game Not finished", Toast.LENGTH_SHORT).show()
        else {
            openDialog = true
        }

        nxtGame = false
    }

    if (openDialog) {
        WinnerDialog(
            onDismissRequest = { openDialog = false },
            onConfirmation = { listWinnerID ->
                if (listWinnerID.isEmpty())
                    showToast = "Please enter a valid Player ID"
                else {
                    val winnerIDs = listWinnerID.split(",")
                    var flag = true
                    var numChecks = 0
                    for (winnerID in winnerIDs) {
                        database.child("Games/$gameID/Players/$winnerID")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    flag = flag && snapshot.exists()
                                    numChecks++

                                    if (numChecks == winnerIDs.size) {
                                        if (flag) {
                                            val amount = pile / winnerIDs.size
                                            for (winnerID2 in winnerIDs)
                                                database.child("Games/$gameID/Players/$winnerID2/Chips")
                                                    .ref.setValue(ServerValue.increment(amount.toLong()))

                                            database.child("Games/$gameID/Pile").setValue(0)
                                            database.child("Games/$gameID/Round").setValue(1)
                                            database.child("Games/$gameID/currentBet").setValue(3)
                                            database.child("Games/$gameID/numFolded").setValue(0)
                                            database.child("Games/$gameID/numBets").setValue(0)

                                            val playersRef = database.child("Games/$gameID/Players")
                                            playersRef.addListenerForSingleValueEvent(object :
                                                ValueEventListener {
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    for (playerSnapshot in snapshot.children) {
                                                        playerSnapshot.child("TotalBet").ref.setValue(
                                                            0
                                                        )
                                                        playerSnapshot.child("hasPlacedBet").ref.setValue(
                                                            false
                                                        )
                                                        if (playerSnapshot.child("Chips")
                                                                .getValue<Int>() as Int == 0
                                                        ) {
                                                            playerSnapshot.child("isFolded").ref.setValue(
                                                                true
                                                            )
                                                            database.child("Games/$gameID/numFolded").ref.setValue(
                                                                ServerValue.increment(1)
                                                            )
                                                        } else
                                                            playerSnapshot.child("isFolded").ref.setValue(
                                                                false
                                                            )
                                                    }
                                                }

                                                override fun onCancelled(error: DatabaseError) {}
                                            })
                                            openDialog = false
                                        } else
                                            showToast = "Player ID does not exist"

                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {}
                            })
                    }
                }
            })
    }



    if (showToast.isNotEmpty()) {
        Toast.makeText(LocalContext.current, showToast, Toast.LENGTH_SHORT).show()
        showToast = ""
    }

    if (call) {
        if (chips < bet - totalBet)
            Toast.makeText(LocalContext.current, "Not Enough Chips", Toast.LENGTH_SHORT).show()
        else if (isFolded)
            Toast.makeText(LocalContext.current, "Already Folded", Toast.LENGTH_SHORT).show()
        else if (hasPlacedBet)
            Toast.makeText(LocalContext.current, "Already Placed Bet", Toast.LENGTH_SHORT).show()
        else {
            val amount = bet - totalBet
            database.child("Games/$gameID/Pile").ref.setValue(ServerValue.increment(amount.toLong()))
            database.child("Games/$gameID/numBets").ref.setValue(ServerValue.increment(1))

            database.child("Games/$gameID/Players/$playerID/Chips").ref.setValue(
                ServerValue.increment(-amount.toLong())
            )
            database.child("Games/$gameID/Players/$playerID/hasPlacedBet").setValue(true)
            database.child("Games/$gameID/Players/$playerID/TotalBet").ref.setValue(
                ServerValue.increment(amount.toLong())
            )
        }
        sleep(100)
        call = false
    }

    if (fold) {
        if (isFolded)
            Toast.makeText(LocalContext.current, "Already Folded", Toast.LENGTH_SHORT).show()
        else {
            database.child("Games/$gameID/numFolded").ref.setValue(ServerValue.increment(1))
            database.child("Games/$gameID/Players/$playerID/isFolded").setValue(true)
            if (hasPlacedBet)
                database.child("Games/$gameID/numBets").ref.setValue(ServerValue.increment(-1))
            database.child("Games/$gameID/Players/$playerID/hasPlacedBet").setValue(false)
        }
        sleep(1000)
        fold = false
    }

    if (raiseDialog) {
        RaiseDialog(
            onDismissRequest = { raiseDialog = false },
            onConfirmation = { amount ->
                raise = amount
                raiseDialog = false
            }
        )
    }

    if (raise > 0) {
        if (isFolded)
            Toast.makeText(LocalContext.current, "Already Folded", Toast.LENGTH_SHORT).show()
        else {
            val value = raise
            if (bet + value - totalBet <= chips) {
                database.child("Games/$gameID/currentBet").ref.setValue(ServerValue.increment(value.toLong()))
                database.child("Games/$gameID/numBets").setValue(0)

                val playersRef = database.child("Games/$gameID/Players")
                playersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (playerSnapshot in snapshot.children) {
                            playerSnapshot.child("hasPlacedBet").ref.setValue(false)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
//                if (chips >= bet - totalBet)
//                    call = true                          TODO
            } else
                Toast.makeText(LocalContext.current, "Not Enough Chips", Toast.LENGTH_SHORT)
                    .show()
        }
        raise = 0
    }

}
