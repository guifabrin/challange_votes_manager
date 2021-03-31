
import "../scss/index.scss";
import template from "../template/index.html";

import Vue from "vue";
import 'bootstrap';

const HTTP_CODES = {
    NOT_FOUND: 404,
    UNAUTHORIZED: 401
}

new Vue({
    el: "#app",
    data: {
        cpf: null,
        password: null,
        uuid: null,
        associateds: [],
        shedules: []
    },
    methods: {
        login: function () {
            this.ajax("POST", `/api/v1/associated/login/${this.cpf}`, this.password,
                (uuid) => {
                    this.uuid = uuid;
                    this.listAssociateds();
                    this.listShedules();
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Senha incorreta.")
                    if (status == HTTP_CODES.NOT_FOUND)
                        alert("Associado não encontrado.")
                })
        },
        listAssociateds: function () {
            this.ajax("GET", `/api/v1/associated/list`, this.password,
                (text) => {
                    this.associateds = JSON.parse(text);
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                })
        },
        listShedules: function () {
            this.ajax("GET", `/api/v1/shedule/list`, this.password,
                (text) => {
                    this.shedules = JSON.parse(text);
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                })
        },
        ajax: function (method, url, body, fnSuccess = () => { }, fnError = () => { }) {
            var xhr = new XMLHttpRequest();
            xhr.open(method, url, true);
            xhr.setRequestHeader("uuid", this.uuid);
            xhr.send(body);
            xhr.onreadystatechange = function () {
                if (this.readyState != 4) return;
                if (this.status == 200) {
                    return fnSuccess(this.responseText);
                }
                fnError(this.status, this.responseText);
            };
        }
    },
    template
})