const registerForm = document.getElementById('register-form')
const registerDisplayname = document.getElementById('register-displayname')
const registerPassword = document.getElementById('register-password')
const registerEmail = document.getElementById('register-email')

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/api/v1/users'

const handleSubmit = async (e) =>{
    e.preventDefault()

    let bodyObj = {
        // change to camel case
        displayName: registerDisplayname.value,
        password: registerPassword.value,
        email: registerEmail.value
    }

    const response = await fetch(`${baseUrl}/register`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))

    const responseArr = await response.json()

    if (response.status === 200){
        window.location.replace('/login.html');
    }
}

registerForm.addEventListener("submit", handleSubmit)