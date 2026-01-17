package com.CU.blink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CU.blink.HomePage.homePage
import com.CU.blink.ui.theme.BlinkTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var page by rememberSaveable { mutableStateOf<PageLocation>(PageLocation.HOME) }
            BlinkTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBarBlink(
                                modifier = Modifier.padding(
                                    top = 20.dp,
                                    bottom = 8.dp,
                                    start = 8.dp,
                                    end = 8.dp
                                ),
                                onChange = { page = it }
                            )
                        },
                        bottomBar = {
                            BottomAppBarBlink(
                                modifier = Modifier.fillMaxWidth(),
                                onChange = { page = it },
                                page
                            )
                        }) { innerPadding ->
                        when (page) {
                            PageLocation.HOME -> homePage(Modifier.padding(innerPadding))
                            PageLocation.SEARCH -> Text("Search")
                            PageLocation.ACCOUNT -> Text("Account")
                        }
                    }
                }
            }
        }
    }
}


