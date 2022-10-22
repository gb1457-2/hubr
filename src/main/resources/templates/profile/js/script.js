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

    $('#staticBackdrop').modal('show');

    let param = getParamByType(typeBackdrop);
    document.getElementById('staticBackdropLabel').innerText = param.label;
    document.getElementById('staticBackdropText').innerText = param.text;
    document.getElementById('modalPassword').hidden = param.showPassword;
    document.getElementById("formModal").action = param.action;
}

function closeModal() {
    $('#staticBackdrop').modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();

}
function getParamByType(typeBackdrop) {
    let obj = {
        label: "",
        text: "",
        showPassword: false,
        action: "",
        method: "get"
    };
    if (typeBackdrop = "Удаление") {
        obj.label = "Удалить аккаунт";
        obj.text = "Вы подтверждаете удаление аккаунта? Вам на почту будет выслана ссылка на удаления аккаунта.";
        obj.action = "/profile/security/deleteProfile";
    } else if (typeBackdrop = "Подтверждение удаления") {
        obj.label = "Удалить аккаунт";
        obj.text = "Вы подтверждаете удаление аккаунта? Вам на почту будет выслана ссылка на удаления аккаунта.";
        obj.showPassword = true;
        obj.action = "/profile/security/deleteProfile";
    }
    return obj;

}