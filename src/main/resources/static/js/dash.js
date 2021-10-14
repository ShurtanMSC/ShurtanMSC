function getWellStats() {
    axios.get("/api/well/stat/status")
        .then(function (response) {
            console.log(response)
            console.log(response.data)
        })
        .catch(function (error) {
            console.log(error)
        })
}