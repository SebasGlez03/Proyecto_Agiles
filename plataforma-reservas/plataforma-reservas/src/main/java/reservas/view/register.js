// Espera a que todo el documento HTML se cargue
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registrationForm');
    const messageDiv = document.getElementById('message');

    form.addEventListener('submit', async (e) => {
        e.preventDefault(); 

        //1. Recoger los datos del formulario
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const role = document.getElementById('role').value;

        const registrationData = {
            username: username,
            password: password,
            role: role
        };
        
        //Se ejecuta en el puerto 8080 (URL del Endpoint)
        const endpointUrl = 'http://localhost:8082/api/auth/register'; 

        //2. Enviar los datos al Backend (Spring Boot Controller)
        try {
            const response = await fetch(endpointUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(registrationData)
            });

            //3. Manejar la respuesta
            if (response.ok) {
                //Registro exitoso (HTTP Status 201 CREATED)
                showMessage('Registro exitoso. Puedes iniciar sesión.', 'success');
                form.reset(); // Limpiar el formulario
            } else {
                //Error de validación o duplicado (HTTP Status 400 BAD_REQUEST)
                const errorText = await response.text();
                showMessage(`Error al registrar: ${errorText}`, 'error');
            }
        } catch (error) {
            //Error de conexión a la red
            showMessage('Error de conexión con el servidor. Intenta más tarde.', 'error');
        }
    });

    /**
     * Función auxiliar para mostrar mensajes al usuario.
     */
    function showMessage(text, type) {
        messageDiv.textContent = text;
        messageDiv.className = `message ${type}`;
        messageDiv.style.display = 'block';
    }
});