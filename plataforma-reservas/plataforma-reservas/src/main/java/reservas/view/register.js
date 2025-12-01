document.getElementById('registrationForm').addEventListener('submit', async (e) => {
    e.preventDefault(); 

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;

    const registrationData = { username, password, role };

    try {
        const response = await fetch('http://localhost:8082/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(registrationData)
        });

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Cuenta Creada',
                text: 'Ahora puedes iniciar sesión',
                confirmButtonText: 'Ir al Login'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = 'login.html';
                }
            });
        } else {
            const errorText = await response.text();
            Swal.fire({
                icon: 'warning',
                title: 'No se pudo registrar',
                text: errorText || 'El usuario ya existe o los datos son inválidos'
            });
        }
    } catch (error) {
        Swal.fire({
            icon: 'error',
            title: 'Error de red',
            text: 'No se pudo conectar con el servidor.'
        });
    }
});