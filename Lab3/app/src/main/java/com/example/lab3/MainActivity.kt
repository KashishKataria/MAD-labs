package com.example.lab3
import com.example.lab3.R
import com.example.lab3.ui.theme.Typography
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.lab3.ui.theme.Lab3Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController,onSignUpClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("PersonListScreen")
                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Cyan)
        ) {
            Text("Login", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Not a member? Sign up now",
            color = Color.White,
            modifier = Modifier.clickable {
                navController.navigate("RegistrationScreen")
            }
        )
    }
}
val persons = arrayListOf(
    Person("Chandier", "https://c.ndtvimg.com/2023-10/d7ciib98_matthew-_625x300_29_October_23.jpg", "Details about Person 1", 4.5f),
    Person("Joey", "https://upload.wikimedia.org/wikipedia/en/thumb/d/da/Matt_LeBlanc_as_Joey_Tribbiani.jpg/220px-Matt_LeBlanc_as_Joey_Tribbiani.jpg", "Details about Person 2", 3.5f),
    Person("Monica", "https://upload.wikimedia.org/wikipedia/en/d/d0/Courteney_Cox_as_Monica_Geller.jpg", "Details about Person 3", 5.0f),
    Person("Phoeba", "https://www.looper.com/img/gallery/phoebe-buffays-friends-timeline-explained/intro-1621661137.jpg", "Details about Person 4", 2.0f),
    Person("ROss", "https://i.pinimg.com/736x/67/53/d3/6753d3169ab19de05aa73748ebc0e7ab.jpg", "Details about Person 5", 4.0f),
    Person("Reachel", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSc1W68W6n6mYJPb7eUi6NOtkjW-FuZw2QpPQ&usqp=CAU", "Details about Person 6", 4.8f)
)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController = navController, onSignUpClick = {
                navController.navigate("LoginScreen")
            })
        }
        composable("RegistrationScreen") {
            RegistrationScreen(onLoginClick = {
                navController.navigate("LoginScreen")
            })
        }
        composable("PersonListScreen") {
            PersonListScreen(navController = navController,onLoginClick = {
                navController.navigate("PersonListScreen")
            })
        }
        composable("PersonItem") {
            StarPersonItem(person =selectedPerson.value, navController = navController, onClick = {
                navController.navigate("PersonItem")
            })
        }


    }
}
@Composable
fun RegistrationScreen(onLoginClick: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Example: Handle registration logic here
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Magenta)
        ) {
            Text("Register", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Already registered? Login me",
            color = Color.White,
            modifier = Modifier.clickable { onLoginClick() }
        )
    }
}
data class Person(
    val name: String,
    val imageUrl: String,
    val details: String,
    val rating: Float
)

@Composable
fun PersonListScreen(navController: NavController ,onLoginClick: () -> Unit) {

    val label = "Friends"
    val description = "Description goes here"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, bottom = 50.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold,fontSize = 30.sp,)) {
                    append(label)
                }
            },
            modifier = Modifier
                .padding(bottom = 10.dp)

        )
        Text(
            text = "CLick on an employee  single user to learn mode more and see if yiu are  compatible for a date!",

            modifier = Modifier.padding(start=20.dp,bottom = 150.dp)
        )


        // Description below the label


    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 180.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 130.dp)
        ) {

            items(persons.size) { index ->
                val person = persons[index]
                PersonItem(person = person, navController = navController, onClick = {

                })
            }

        }
    }
}



val selectedPerson = mutableStateOf<Person>(persons[0])
@Composable
fun PersonItem(person: Person,navController: NavController, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = {
                selectedPerson.value = person
                navController.navigate("PersonItem")
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter =rememberImagePainter(person.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),

            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = person.name, style =  MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Rating: ${person.rating}")
        }
    }
}
@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    maxRating: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.TwoTone.ThumbUp,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onRatingChanged(i.toFloat())
                    }
            )
        }
    }
}

@Composable
fun StarPersonItem(person: Person, navController: NavController, onClick: () -> Unit) {
    var userRating by remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fullStars = person.rating.toInt()
        val halfStar = if (person.rating - fullStars >= 0.5) 1 else 0

        // Star at the top
        RatingBar(
            rating = person.rating,
            onRatingChanged = { userRating = it },
            modifier = Modifier.padding(end = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Centered Image
        Image(
            painter = rememberImagePainter(person.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Name and Description
        Text(
            text = person.name,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = person.details,

            color = Color.Gray
        )
    }
}

@Composable
@Preview
fun RegistrationScreenPreview() {
    // Example: Creating a NavController for preview purposes
    val navController = rememberNavController()
    RegistrationScreen {
        // Handle navigation to the login screen here
        navController.navigate("login_screen")
    }
}




