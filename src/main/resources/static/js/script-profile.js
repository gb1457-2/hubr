let articles = JSON.parse(sessionStorage.articles);
let sizePage = 10;
let currentPage = 1;
let table = document.getElementById('articlesTable');
let pagPrev = document.getElementById('pagPrev');
let pagNext = document.getElementById('pagNext');
let pagNumber = document.getElementById('pagNumber');
drowPage();

function onNextPage() {
    if (pagNext.classList.contains("disabled")) {
        return;
    }
    currentPage++;
    drowPage();
}

function onPrevPage() {
    if (pagPrev.classList.contains("disabled")) {
        return;
    }
    currentPage--;
    drowPage();
}

function drowPage() {

    while (table.rows.length > 0) {
        table.deleteRow(0);
    }
    let firstNumberPage = (currentPage - 1) * sizePage;

    for (let i =firstNumberPage; i<firstNumberPage+sizePage && i < articles.length; i++) {
        let article = articles[i];
        let htmlTableRowElement = table.insertRow(i);
        htmlTableRowElement.insertCell(0).innerText = i;
        htmlTableRowElement.insertCell(1).innerText = article.name;
        htmlTableRowElement.insertCell(2).innerText = article.topic;
        htmlTableRowElement.insertCell(3).innerText = article.likesCount;
        htmlTableRowElement.insertCell(4).innerText = article.createdAt;
        htmlTableRowElement.insertCell(5).appendChild(createButtom(article));

    }

    if (currentPage * sizePage > articles.length) {
        pagNext.classList.add("disabled");
        console.log(1);
    } else {
        pagNext.classList.remove("disabled");
        console.log(2);
    }
    if (currentPage == 1) {
        pagPrev.classList.add("disabled");
    } else {
        pagPrev.classList.remove("disabled");
    }
    pagNumber.innerHTML = currentPage.toString();

}

function createButtom(article) {
    var btn = document.createElement('button');
    btn.className = "btn";
    if ((article.published)){
        btn.classList.add("btn-primary")
        btn.innerHTML = 'Изменить статью';
    } else {
        btn.classList.add("btn-secondary")
        btn.innerHTML = 'Изменить черновик';
    }

    btn.addEventListener ("click", function() {
        window.open("/articles/add?id="+article.id);
    });
    return btn;
}