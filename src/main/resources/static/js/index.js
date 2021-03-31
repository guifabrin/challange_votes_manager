const HTTP_STATUS_CODE = {
    OK: 200,
    NOT_FOUND: 404,
    UNAUTHORIZED: 401
}

function getShedules() {
    $.ajax({
        beforeSend: function (request) {
            request.setRequestHeader("uuid", sessionStorage.getItem('uuid'));
        },
        dataType: "json",
        url: "/api/v1/shedule/list",
        complete: (xhr) => {
            if (xhr.status == HTTP_STATUS_CODE.OK) {
                var items = [];
                $.each(JSON.parse(xhr.responseText), function (key, val) {
                    items.push(`
                <li>
                    <span>${val.name}</span>
                    <span>${val.description}</span>
                    <span>${val.yesVotes}</span>
                    <span>${val.noVotes}</span>
                </li>
            `);
                });

                $("<ul/>", {
                    "class": "shedule-list",
                    html: items.join("")
                }).appendTo("#content");
            }
        }
    });
}

function loggedIn(uuid) {
    sessionStorage.setItem('uuid', uuid);
    $('#login').hide();
    getShedules();
}

function login() {
    $.ajax({
        type: "POST",
        url: `/api/v1/associated/login/${$('#cpf').val()}`,
        data: $('#password').val(),
        contentType: 'text/plain',
        complete: (xhr) => {
            if (xhr.status == HTTP_STATUS_CODE.OK)
                return loggedIn(xhr.responseText);
            if (xhr.status == HTTP_STATUS_CODE.NOT_FOUND)
                return alert('Associated not found');
            if (xhr.status == HTTP_STATUS_CODE.UNAUTHORIZED)
                return alert('Associated not authorized');
        }
    })
}

//getShedules();



