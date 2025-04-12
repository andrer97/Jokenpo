package com.example.jokenpo

import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jokenpo.ui.theme.JokenpoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val move: MutableList<Int> = mutableListOf(
            R.drawable.rock,
            R.drawable.paper,
            R.drawable.scissors,
            R.drawable.jokenpo
        )

        setContent {
            JokenpoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding),
                        move
                    )
                }
            }
        }
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    move: MutableList<Int>
) {

    var machineMove by rememberSaveable { mutableStateOf((3)) }

    var playerMove by rememberSaveable { mutableStateOf((0)) }

    var wins by rememberSaveable { mutableStateOf(0) }
    var loses by rememberSaveable { mutableStateOf(0) }
    var draws by rememberSaveable { mutableStateOf(0) }

    AppScreen(
        modifier = modifier,
        move = move,
        onClick = { value ->

            playerMove = value

            machineMove = (0..2).random()

            //Implementar lógica de vitória, e apresentar para o usuário

            if (playerMove == machineMove) {
                draws++
            } else {
                // Cálculo modular para determinar o resultado
                val result = (playerMove - machineMove + 3) % 3
                if (result == 1) wins++ else loses++
            }

            /*if (playerMove == machineMove) {
                draws++
            }
            else if (playerMove == 0) {
                if (machineMove == 1) {
                    loses++
                }
                else {
                    wins++
                }
            }
            else if (playerMove == 1) {
                if (machineMove == 2) {
                    loses++
                }
                else {
                    wins++
                }
            }
            else if (playerMove == 2) {
                if (machineMove == 0) {
                    loses++
                }
                else {
                    wins++
                }
            }*/
        },
        machineMove = machineMove,
        playerMove = playerMove,
        wins = wins,
        loses = loses,
        draws = draws,
        onClickStart = { valor ->
            machineMove = valor
            wins = 0
            loses = 0
            draws = 0
        }
    )
}

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    move: MutableList<Int>,
    onClick: (Int) -> Unit,
    machineMove: Int,
    playerMove: Int,
    wins: Int,
    loses: Int,
    draws: Int,
    onClickStart: (Int) -> Unit
) {


    Column(
        modifier = modifier.fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (machineMove != 3) {
            if ( !(wins == 0 && loses == 0 && draws == 0) ) {
                Image(
                    painterResource(move[machineMove]),
                    contentDescription = "move",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp).padding(20.dp)
                )
                Image(
                    painterResource(move[playerMove]),
                    contentDescription = "move",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp).padding(20.dp)
                )
                Spacer(modifier = Modifier.size(50.dp))
            }
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround,

                ) {
                Column (modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onClick(0) },
                        modifier = Modifier.size(100.dp).padding(8.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black),
                    ) {
                        Image(
                            painterResource(R.drawable.rock),
                            contentDescription = null
                        )
                    }
                    Text(text = "Pedra")
                }
                Column (modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onClick(1) },
                        modifier = Modifier.size(100.dp).padding(8.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Image(
                            painterResource(R.drawable.paper),
                            contentDescription = null
                        )
                    }
                    Text(text = "Papel")
                }
                Column (modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onClick(2) },
                        modifier = Modifier.size(100.dp).padding(8.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Image(
                            painterResource(R.drawable.scissors),
                            contentDescription = null
                        )
                    }
                    Text(text = "Tesoura")
                }
            }
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(text = "Vitórias: ${wins}",
                    modifier = Modifier.padding(15.dp))
                Text(text = "Empates: ${draws}",
                    modifier = Modifier.padding(15.dp))
                Text(text = "Derrotas: ${loses}",
                    modifier = Modifier.padding(15.dp))
            }
            Button(
                onClick = {onClickStart(3)},
                colors = ButtonDefaults.buttonColors(Color.Black)){
                Text("Sair", color = Color.Yellow)
            }
        }
        else {
            Image(
                painterResource(move[3]),
                contentDescription = "Jokenpo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = { onClickStart(0) },
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text(text = "Iniciar Jogo", color = Color.Yellow)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    JokenpoTheme {
        val move: MutableList<Int> = mutableListOf(
            R.drawable.rock,
            R.drawable.paper,
            R.drawable.scissors,
            R.drawable.jokenpo
        )
        AppScreen(modifier = Modifier, move, onClick = {}, 0, playerMove = 0, 0, 0, 0, {})
    }
}