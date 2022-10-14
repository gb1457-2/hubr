newPassword.onkeyup = confirmPassword.onkeyup = handlerNew;

function handlerNew(e) {

    if (newPassword.value == "" || confirmPassword.value ==""){
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

function showModal(typeModal) {

    let staticBackdrop = new bootstrap.Modal(document.getElementById('staticBackdrop'), {
        keyboard: false
    });

    staticBackdrop.show();

    let param = getParamByType(typeModal);
    document.getElementById('staticBackdropLabel').innerText = param.label;
    document.getElementById('staticBackdropText').innerText = param.text;

}
function getParamByType(typeModal) {
    let obj = {
        label: "",
        text: ""
    };
    if (typeModal = "Удаление"){
        obj.label = "Удалить аккаунт";
        obj.text = "Вы подтверждаете удаление аккаунта? Вам на почту будет выслана ссылка на удаления аккаунта.";
        return obj;
    }
    return obj;

}