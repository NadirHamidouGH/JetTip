package com.example.jettip

import InputField
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jettip.ui.theme.JetTipTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettip.util.calculateTotalTip
import com.example.jettip.util.totalPerPerson
import com.example.jettip.widgets.RoundIconButton
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainScreen() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {

            BillForm()
        }
    }
}

@Composable
fun PurpleCard(totalPerPerson: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val decimalFormat = DecimalFormat("#")

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total per person",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp),
            )
            Text(
                text = "${decimalFormat.format(totalPerPerson)}$",
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(12.dp),
            )
        }
    }
}

@Preview
@ExperimentalComposeUiApi
@Composable
fun WhiteCard() {

}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier,
             onValueChange: (String) -> Unit = {}
){

    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

    PurpleCard(totalPerPerson = totalPerPersonState.value)

    InputField(
        valueState = totalBillState,
        labelId = "Enter Bill",
        enabled = true,
        isSingleLine = true,
        onAction = KeyboardActions {
            if(!validState)return@KeyboardActions
            onValueChange(totalBillState.value.trim())
            keyboardController?.hide()
        }
    )
    if (true){

        var personCounter : Int by remember {
            mutableStateOf(1)
        }

        var sliderPositionState = remember {
            mutableStateOf(01f)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val decimalFormat = DecimalFormat("#")

            Text("Split"  , fontSize = 20.sp)
            RoundIconButton( imageVector = Icons.Default.Remove, onClick = {
              if(personCounter > 1) personCounter -= 1

                    totalPerPersonState.value =
                        totalPerPerson(
                            totalBill = totalBillState.value.toDouble(),
                            tipPercentage = sliderPositionState.value.toInt(),
                            splitBy = personCounter
                        )

            })
            Text("${decimalFormat.format(personCounter)}" , fontSize = 25.sp)
            RoundIconButton( imageVector = Icons.Default.Add, onClick = {
                personCounter += 1

                totalPerPersonState.value =
                    totalPerPerson(
                        totalBill = totalBillState.value.toDouble(),
                        tipPercentage = sliderPositionState.value.toInt(),
                        splitBy = personCounter
                    )  })
        }

        Card(
            border = BorderStroke(1.dp, Color.Gray),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier.fillMaxWidth(),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                var text by remember { mutableStateOf("") }

                val decimalFormat = DecimalFormat("#")
                var tipAmountState = remember {
                    mutableStateOf(0.0)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                ) {
                    Text("Tip" , fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(50.dp))
                    Text("$ ${decimalFormat.format(tipAmountState.value)}" , fontSize = 25.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Text("${decimalFormat.format(sliderPositionState.value)}%" , fontSize = 25.sp)
                }

                Slider(
                    value = sliderPositionState.value,
                    onValueChange = { newVal ->
                        sliderPositionState.value = newVal
                        Log.d("Slider", "BillForm: $newVal")
                        tipAmountState.value = calculateTotalTip(totalBill = totalBillState.value.toDouble() ,tipPercentage = sliderPositionState.value.toInt() )

                        totalPerPersonState.value =
                            totalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = sliderPositionState.value.toInt(),
                                splitBy = personCounter
                            )

                    },
                    valueRange = 0f..100f,
                    steps = 9,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }else{
        Box (){

        }
    }
}