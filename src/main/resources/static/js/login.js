let localStorageToken = localStorage.getItem("token");

const pathname = window.location.pathname; // Returns path only (/path/example.html)

if (pathname !== "/" && (localStorageToken === null || localStorageToken === "")) {
    window.location.replace("/");
}

function logOutBtn() {
    window.location.replace("/");
    localStorage.removeItem("token");
}

function loginForm(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    const data = {}
    formData.forEach((value, key) => (data[key] = value));

    let config = {
        method: 'post',
        url: '/api/auth/login',
        data
    };

    let token = "";
    let tokenType = "";

    axios(config)
        .then(function (response) {
            console.log(response)
            if (response.status === 200) {
                tokenType = response.data.type;
                token = response.data.token;
                localStorage.setItem("token", tokenType + token);

                let localStorageToken = localStorage.getItem("token");

                axios.get("/admin", {
                    headers: {
                        authorization: localStorageToken
                    }
                })
                    .then(function () {
                        window.location.href = "/admin";
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}













