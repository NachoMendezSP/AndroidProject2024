package com.undef.mendez.eventosculturales.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.undef.mendez.eventosculturales.viewmodel.AuthViewModel
import com.undef.mendez.eventosculturales.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onSaveChanges: () -> Unit,
    onLogout: () -> Unit,
    authViewModel: AuthViewModel,
    viewModel: ProfileViewModel = viewModel()
) {
    val username = viewModel.username
    val email = viewModel.email
    val password = remember { mutableStateOf("") }
    val notificationsEnabled = viewModel.notificationsEnabled
    val notificationDaysBefore = viewModel.notificationDaysBefore

    // Opciones para el dropdown
    val notificationOptions = listOf(1, 3, 5, 7)
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()), // Agrega desplazamiento vertical
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(text = "Mi Perfil", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de nombre de usuario
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de correo electrónico
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de contraseña
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            // Preferencias de notificaciones
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Habilitar Notificaciones", modifier = Modifier.weight(1f))
                Switch(
                    checked = notificationsEnabled.value,
                    onCheckedChange = { notificationsEnabled.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Configuración de tiempo de notificación
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Notificarme ${notificationDaysBefore.value} días antes",
                    modifier = Modifier
                        .clickable { expanded = true }
                        .padding(vertical = 8.dp)
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    notificationOptions.forEach { days ->
                        DropdownMenuItem(
                            text = { Text("$days días antes") },
                            onClick = {
                                viewModel.notificationDaysBefore.value = days
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar cambios
            Button(
                onClick = { viewModel.saveChanges(onSaveChanges) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Guardar Cambios")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para cerrar sesión
            Button(
                onClick = {
                    authViewModel.logout()
                    onLogout()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}
