package com.oguzhanaslann.canonicalayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oguzhanaslann.canonicalayouts.ui.theme.CanonicaLayoutsTheme

private const val listDetail = "List Detail"
private const val supportingPane = "Supporting Pane"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanonicaLayoutsTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navHost = rememberNavController()
    val navBackStackEntry by navHost.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold {
        NavigationSuiteScaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(it.copy(bottom = 0.dp)),
            navigationSuiteItems = navigationSuiteItems(currentDestination, navHost)
        ) {
            MainScreenNavigationHost(navHost = navHost)
        }
    }
}

@Composable
private fun navigationSuiteItems(
    currentDestination: NavDestination?,
    navHost: NavHostController
): NavigationSuiteScope.() -> Unit = {
    item(
        selected = currentDestination?.hierarchy?.any { it.route == listDetail } == true,
        onClick = {
            navHost.navigate(listDetail)
        },
        icon = {
            Image(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "List Detail"
            )
        }
    )

    item(
        selected = currentDestination?.hierarchy?.any { it.route == supportingPane } == true,
        onClick = {
            navHost.navigate(supportingPane)
        },
        icon = {
            Image(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Supporting Pane"
            )
        }
    )
}

@Composable
fun MainScreenNavigationHost(
    modifier: Modifier = Modifier, navHost: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHost,
        startDestination = listDetail
    ) {
        composable(listDetail) {
            ListDetailView()
        }

        composable(supportingPane) {
           SupportingPaneView()
        }
    }
}

@Preview
@Composable
fun previewMainScreen() {
    MainScreen()
}
