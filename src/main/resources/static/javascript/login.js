let loginForm = document.getElementById('login-form')
let logInDisplayName = document.getElementById('login-displayname')
let loginPassword = document.getElementById('login-password')

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/api/v1/users'

const handleSubmit = async (e) =>{
    e.preventDefault()

    let bodyObj = {
        displayName: logInDisplayName.value,
        password: loginPassword.value
    }

    const response = await fetch(`${baseUrl}/login`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))

    const responseArr = await response.json()

    if (response.status === 200){
        console.log(responseArr);
        document.cookie = `userId=${responseArr[1]}`;
        const tripUrl = responseArr[0];
        window.location.replace(tripUrl);
    }
}

loginForm.addEventListener("submit", handleSubmit)