package com.hua.flickr.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.hua.flickr.ui.common.activities.BaseActivity
import com.hua.flickr.ui.common.viewmodels.ViewModelFactory
import com.hua.flickr.ui.theme.FlickrTheme
import com.hua.flickr.ui.viewmodels.FlickrViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory<FlickrViewModel>
    lateinit var viewModel: FlickrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(FlickrViewModel::class.java)

        enableEdgeToEdge()
        setContent {
            FlickrApp()
        }

    }

    @Composable
    private fun FlickrApp() {
        FlickrTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Greeting(
                    name = "Android",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

