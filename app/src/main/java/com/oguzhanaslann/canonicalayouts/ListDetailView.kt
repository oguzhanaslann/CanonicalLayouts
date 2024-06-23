package com.oguzhanaslann.canonicalayouts

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun ListDetailView() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Person>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        modifier = Modifier.fillMaxSize(),
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = { PersonListPane(navigator) },
        detailPane = { PersonDetailPane(navigator) },
        extraPane = { ContactPane(navigator) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.ContactPane(navigator: ThreePaneScaffoldNavigator<Person>) {
    AnimatedPane {
        val person = navigator.currentDestination?.content
        if (person != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Phone: ${person.phone}")
                Text("Email: ${person.email}")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.PersonDetailPane(navigator: ThreePaneScaffoldNavigator<Person>) {
    AnimatedPane {
        val person = navigator.currentDestination?.content
        if (person != null) {
            PersonDetailsView(person, navigator)
        } else {
            EmptyDetailView()
        }
    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun PersonDetailsView(person: Person, navigator: ThreePaneScaffoldNavigator<Person>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularImage(person)
            Text(text = person.personNane)
            Button(
                onClick = {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Extra, person)
                }
            ) {
                Text("Contact")
            }
        }
    }
}

@Composable
private fun EmptyDetailView() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text("No person selected")
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
    modifier: Modifier = Modifier, person: Person
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularImage(person)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = person.personNane)
        }
    }
}


@Composable
private fun CircularImage(person: Person) {
    Surface(
        shape = CircleShape,
    ) {
        Image(
            contentDescription = "Person - ${person.personNane}",
            painter = painterResource(id = person.imageRes),
        )
    }
}