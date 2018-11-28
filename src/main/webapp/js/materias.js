/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    cargar_carreras();
    click_semestre();
    asignar_materia_carrera();
    click_grupo();
    cargar_profesores();
    form_crear_curso();
    agregar_horario();
    horarios_cursos();


});

function cargar_carreras() {
    datos = "action=comboBox_carreras";
    $.post("controlador_horario", datos, function (data) {
        $("#comboBox_carreras").html(data);
    });
}

function click_semestre() {
    $("#comboBox_semestres").change(function (event) {
        event.preventDefault();
        var carrera = $("#comboBox_carreras").val();
        var semestre = $("#comboBox_semestres").val();
        datos = "action=comboBox_grupos&carrera=" + carrera + "&semestre=" + semestre;
        $.post("controlador_horario", datos, function (data) {
            $("#comboBox_grupos").html(data);
        });
    });
}

function click_grupo() {
    $("#comboBox_grupos").change(function (event) {
        event.preventDefault();
        var grupo = $("#comboBox_grupos").val();
        cargar_cursos();
    });
}

function cargar_cursos() {
    var grupo = $("#comboBox_grupos").val();
    datos = "action=comboBox_cursos&grupo=" + grupo;
    $.post("controlador_horario", datos, function (data) {

        $("#comboBox_cursos").html(data);
    });
}

function cargar_materias() {
    var carrera = $("#comboBox_carreras").val();
    var semestre = $("#comboBox_semestres").val();
    datos = "action=comboBox_materias&carrera=" + carrera + "&semestre=" + semestre;
    $.post("controlador_horario", datos, function (data) {
        $("#comboBox_materias").html(data);
    });
}

function cargar_profesores() {
    var datos = "action=comboBox_profesores";
    $.post("controlador_horario", datos, function (data) {
        $("#comboBox_profesores").html(data);

    });
}


function asignar_materia_carrera() {
    $("#fom_asignar_materias").submit(function (event) {
        event.preventDefault();

        var carrera = $("#comboBox_carreras").val();
        var semestre = $("#comboBox_semestres").val();
        var materia = $("#codigo_materia").val();
        var datos = "action=asignar_materia_carrera&codigo_materia=" + materia + "&carrera=" + carrera + "&semestre=" + semestre;

        $.post("controlador_horario", datos, function (data) {
            $("#principal").html(data);
        });

    });
}

function form_crear_curso() {
    $("#form_crear_curso").submit(function (event) {
        event.preventDefault();
        profesor_curso();
    });
}

function profesor_curso() {
    var curso = $("#comboBox_cursos").val();
    var profesor = $("#comboBox_profesores").val();
    var datos = "action=profesor_curso&curso=" + curso + "&profesor=" + profesor;
    $.post("controlador_horario", datos, function (data) {
        // alert(data);
    });
}


function agregar_horario() {
    $("#boton_agregar_horario").click(function (event) {
        event.preventDefault();
        var dia = $("#dia").val();
        var hora_inicio = $("#hora_inicio").val();
        var hora_fin = $("#hora_fin").val();
        var curso = $("#comboBox_cursos").val();
        var datos = "action=agregar_horario&dia=" + dia
                + "&hora_inicio=" + hora_inicio + "&hora_fin=" + hora_fin
                + "&id_curso=" + curso;
        //alert(datos);
        $.post("controlador_horario", datos, function (data) {
            //  alert(data);
            cargar_horarios();
        });
        
    });
}

function horarios_cursos() {
    $("#comboBox_cursos").change(function (event) {
        event.preventDefault();
        cargar_horarios();

    });


}

    function cargar_horarios() {
        var curso = $("#comboBox_cursos").val();
        var datos = "action=get_horario_curso&curso=" + curso;
        // alert(datos);
        $.post("controlador_horario", datos, function (data) {
            //alert(data);
            $("#horario_de_materia").html(data);

        });
    }













