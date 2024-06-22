package com.oguzhanaslann.canonicalayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oguzhanaslann.canonicalayouts.ui.theme.CanonicaLayoutsTheme


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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainScreen() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Person>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    Scaffold {
        ListDetailPaneScaffold(
            modifier = Modifier.fillMaxSize().padding(it),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = { PersonListPane(navigator) },
            detailPane = { PersonDetailPane(navigator) }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.PersonDetailPane(navigator: ThreePaneScaffoldNavigator<Person>) {
    AnimatedPane {
        navigator.currentDestination?.content?.let {
            PersonDetails(it)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.PersonListPane(navigator: ThreePaneScaffoldNavigator<Person>) {
    AnimatedPane {
        PersonList(
            onItemClick = { item ->
            // Navigate to the detail pane with the passed item
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
        }
        )
    }
}

@Composable
fun PersonList(onItemClick: (Person) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(people) { person ->
            PersonCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(person)
                    },
                person = person
            )
        }
    }
}

@Composable
fun PersonCard(
    modifier: Modifier = Modifier,
    person: Person
) {
    Card {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
            ) {
                Image(
                    contentDescription = "Person - ${person.personNane}",
                    painter = painterResource(id = person.imageRes),
                )
            }
            Text(
                text = person.personNane,
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun PersonDetails(person: Person) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Surface(
                shape = CircleShape,
            ) {
                Image(
                    contentDescription = "Person - ${person.personNane}",
                    painter = painterResource(id = person.imageRes),
                )
            }
            Text(
                text = person.personNane,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun previewMainScreen() {
    MainScreen()
}