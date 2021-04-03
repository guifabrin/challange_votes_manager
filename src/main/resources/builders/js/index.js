
import "../scss/index.scss";
import template from "../template/index.html";

import Vue from "vue";
import 'bootstrap';

const HTTP_CODES = {
    NOT_FOUND: 404,
    UNAUTHORIZED: 401,
    CONFLICT: 409
}

new Vue({
    el: "#app",
    data: {
        cpf: null,
        password: null,
        uuid: null,
        associateds: [],
        shedules: [],
        associatedName: null,
        associatedCpf: null,
        associatedPassword: null,
        associatedEditing: false,
        sheduleId: null,
        sheduleName: null,
        sheduleDescription: null,
        sheduleMinutes: 1,
        sheduleStartDate: null,
        sheduleEditing: false,
        ableToVote: false
    },
    methods: {
        connectSocket() {
            const ws = new WebSocket('ws://' + document.location.hostname + ":" + 8081 + '');
            ws.onmessage = (data) => {
                this.shedules = JSON.parse(data.data);
            }
            ws.onopen = () => {
                ws.send(this.uuid);
            }
        },
        login: function () {
            this.ajax("POST", `/api/v1/associated/login/${this.cpf}`, this.password,
                (uuid) => {
                    this.uuid = uuid;
                    this.listAssociateds();
                    this.connectSocket();
                    this.ajax("GET", `/api/v1/associated/${this.cpf}/able`, null, (data) => {
                        this.ableToVote = true;
                    }, () => {
                        alert('Você não pode votar.')
                    });
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Senha incorreta.")
                    if (status == HTTP_CODES.NOT_FOUND)
                        alert("Associado não encontrado.")
                });
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
        newAssociated: function () {
            this.ajax("POST", `/api/v1/associated/add/`,
                JSON.stringify({
                    cpf: this.associatedCpf,
                    name: this.associatedName,
                    password: this.associatedPassword
                }),
                () => {
                    this.listAssociateds();
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                    if (status == HTTP_CODES.CONFLICT)
                        alert("Associado já cadastrado.")
                });
        },
        editAssociated: function () {
            this.ajax("PUT", `/api/v1/associated/edit/${this.associatedCpf}`,
                JSON.stringify({
                    name: this.associatedName,
                    password: this.associatedPassword
                }),
                () => {
                    this.listAssociateds();
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                    if (status == HTTP_CODES.CONFLICT)
                        alert("Associado já cadastrado.")
                });
        },
        saveAssociated: function () {
            if (this.associatedEditing) {
                return this.editAssociated();
            }
            this.newAssociated();
        },
        removeAssociated: function (cpf) {
            this.ajax("DELETE", `/api/v1/associated/del/${cpf}`, null,
                () => {
                    this.listAssociateds();
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                })
        },
        clearAssociated: function () {
            this.associatedCpf = null;
            this.associatedName = null;
            this.associatedPassword = null;
        },
        endEditAssociated: function () {
            this.associatedEditing = false;
        },
        setAssociated: function (associated) {
            this.associatedEditing = true;
            this.associatedCpf = associated.cpf;
            this.associatedName = associated.name;
            this.associatedPassword = '';
        },

        endEditShedule: function () {
            this.sheduleEditing = false;
        },
        newShedule: function () {
            this.ajax("POST", `/api/v1/shedule/add/`,
                JSON.stringify({
                    name: this.sheduleName,
                    description: this.sheduleDescription,
                    minutes: this.sheduleMinutes,
                    startDate: this.sheduleStartDate,
                }),
                () => {
                    this.listAssociateds();
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                    if (status == HTTP_CODES.CONFLICT)
                        alert("Associado já cadastrado.")
                });
        },
        editShedule: function () {
            this.ajax("PUT", `/api/v1/shedule/edit/${this.sheduleId}`,
                JSON.stringify({
                    name: this.sheduleName,
                    description: this.sheduleDescription,
                    minutes: this.sheduleMinutes,
                    startDate: this.sheduleStartDate,
                }),
                () => {
                    this.listAssociateds();
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                    if (status == HTTP_CODES.CONFLICT)
                        alert("Associado já cadastrado.")
                });
        },
        clearShedule: function () {
            this.sheduleId = null;
            this.sheduleName = null;
            this.sheduleDescription = null;
            this.sheduleMinutes = 1;
            this.sheduleStartDate = null;
        },
        saveShedule: function () {
            if (this.sheduleEditing) {
                return this.editShedule();
            }
            this.newShedule();
        },
        removeShedule: function (id) {
            this.ajax("DELETE", `/api/v1/shedule/del/${id}`, null,
                () => {
                    this.listAssociateds();
                },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                })
        },
        setShedule: function (shedule) {
            this.sheduleEditing = true;
            this.sheduleId = shedule.id;
            this.sheduleName = shedule.name;
            this.sheduleDescription = shedule.description;
            this.sheduleMinutes = shedule.minutes;
            this.sheduleStartDate = shedule.startDate;
        },

        sheduleVote: function (id) {
            this.ajax("POST", `/api/v1/vote/${id}`,
                JSON.stringify({
                    vote: confirm('Votar sim?'),
                }),
                () => { },
                (status) => {
                    if (status == HTTP_CODES.UNAUTHORIZED)
                        alert("Usuário não autenticado.")
                    if (status == HTTP_CODES.CONFLICT)
                        alert("Associado já cadastrado.")
                });
        },

        ajax: function (method, url, body, fnSuccess = () => { }, fnError = () => { }) {
            var xhr = new XMLHttpRequest();
            xhr.open(method, url, true);
            xhr.setRequestHeader("uuid", this.uuid);
            xhr.setRequestHeader("Content-Type", "application/json");
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