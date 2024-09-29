
function getVerifyCode() {
    let requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${window.location.origin}/getCaptcha`, requestOptions)
        .then(response => response.text())
        .then(r => {
            if (r) {
                let result = JSON.parse(r);
                document.getElementById('code-image').src = result.data
            }
        })
        .catch(error => console.log('error', error));
}

getVerifyCode();