newPassword.onkeyup = confirmPassword.onkeyup = handlerNew;

function handlerNew(e) {

    if (newPassword.value == "" || confirmPassword.value == "") {
        newPassword.classList.remove('is-valid');
        newPassword.classList.remove('is-invalid');
        confirmPassword.classList.remove('is-valid');
        confirmPassword.classList.remove('is-invalid');
        return;
    }
    if (newPassword.value !== confirmPassword.value) {
        newPassword.classList.add("is-invalid");
        newPassword.classList.remove('is-valid');
        confirmPassword.classList.add("is-invalid");
        confirmPassword.classList.remove('is-valid');
    } else {
        newPassword.classList.add("is-valid");
        newPassword.classList.remove('is-invalid');
        confirmPassword.classList.add("is-valid");
        confirmPassword.classList.remove('is-invalid');
    }
};


function showModal(typeBackdrop) {


    updateModal(typeBackdrop);
    $('#staticBackdrop').modal('show');

}

function updateModal(typeBackdrop) {

    let param = getParamByType(typeBackdrop);
    document.getElementById('staticBackdropLabel').innerText = param.label;
    document.getElementById('staticBackdropText').innerText = param.text;
    document.getElementById('modalPassword').hidden = param.hiddenPassword;
    document.getElementById('modalEmail').hidden = param.hiddenEmail;
    document.getElementById("formModal").action = param.action;
    document.getElementById("formModal").method = param.method;

}

function getParamByType(typeBackdrop) {
    let obj = {
        label: "",
        text: "",
        hiddenPassword: true,
        action: "",
        method: "get",
        hiddenEmail: true
    };
    if (typeBackdrop == "SEND_DELETE") {
        obj.label = "Удалить аккаунт";
        obj.text = "Вы подтверждаете удаление аккаунта? Вам на почту будет выслана ссылка на удаления аккаунта.";
        obj.action = "/profile/security/deleteProfile";
    } else if (typeBackdrop == "DELETE_PROFILE") {
        obj.label = "Удалить аккаунт";
        obj.text = "Для подтверждения удаления введите пароль.";
        obj.hiddenPassword = false;
        obj.action = "/profile/security/deleteProfile";
        obj.method = "post";
    } else if (typeBackdrop == "RESET_PASSWORD"){
        obj.label = "Удалить аккаунт";
        obj.text = "Введите новый пароль";
        obj.hiddenPassword = false;
        obj.action = "/profile/security/deleteProfile";
        obj.method = "post";
    } else if (typeBackdrop == "RESET_EMAIL"){
        obj.label = "Удалить аккаунт";
        obj.text = "Для подтверждения введите пароль и новую почту";
        obj.hiddenPassword = false;
        obj.hiddenEmail = false;
        obj.action = "/profile/security/deleteProfile";
        obj.method = "post";
    }
    return obj;

}