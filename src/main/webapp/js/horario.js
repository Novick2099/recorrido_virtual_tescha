
function horario() {
    alert('horario');
    $('#principal').html("<h1>asdasd</h1>");
}

function vista_carrera() {
    //esto es si se usa un div
    // $('#contenido').attr("src","form_ncarrera.jsp");
    //se puede mandar a llamar todo el jsp mediante load
    $('#principal').load("form_ncarrera.jsp");
}

function vista_materia() {
    $('#principal').load("insertar_materias.jsp");
}

function alerta() {
    alert('alerta');
}

function insertar_carrera() {
    var nombre_carrera = $('#nombre_carrera').val();
    var action = "insertar_carrera";
    var datos = "action=insertar_carrera&nombre_carrera=" + nombre_carrera;
    $.post("controlador_horario", datos, function (data) {
        vista_carrera();
        //$('#principal').html(data);
    }
    );

    
}

function insertar_materia(event) {
    event.preventDefault();
    var clave_materia = $('#clave_materia').val();
    var nombre_materia = $('#nombre_materia').val();
    var numero_creditos = $('#numero_creditos').val();
    var datos = "action=insertar_materia&clave_materia=" + clave_materia +
            "&nombre_materia=" + nombre_materia + "&numero_creditos=" + numero_creditos;
    //alert(datos);
    $.post("controlador_horario", datos, function (data) {
        $('#principal').html(data);
    }
    );
}

$(document).ready(function(){
    $( "#asignar_materias" ).click(function(e) {
        e.preventDefault();
  $('#principal').load("asignar_materias.jsp");
});
});

function vista_crear_curso(event){
    event.preventDefault();
    $("#principal").load("crear_curso.jsp");
}





























