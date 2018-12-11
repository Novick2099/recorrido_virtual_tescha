$(document).ready(function () {
    load_v_aulas();
    load_v_materias();
    load_admin_cargos();
    load_carreras();
    load_cat_aulas();
    load_edificios();
    load_empleados();
    load_rubricas();
    load_admin_cursos();
});

function load_v_aulas() {
    $("#link_aulas").click(function (event) {
        event.preventDefault();
        $('#contenedor_principal').load("control_aulas.jsp");
    });
}

function load_v_materias() {
    var datos="";
    $('#link_materias').click(function(event){
        event.preventDefault();
        //$('#contenedor_principal').load("control_materias.jsp")
        var datos="action=cargar_registros";
        $.post("materias_servlet",datos,function(data){
            $('#contenedor_principal').html(data);
        });
    });
}

function load_admin_cargos(){
    $('#link_cargos').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load("admin_cargos.jsp");
    });
}

function load_carreras(){
    datos="";
    $('#link_carreras').click(function(event){
        event.preventDefault();
        //$('#contenedor_principal').load("admin_Carreras.jsp");
        var datos="action=cargar_registros";
        $.post("Carreras_Servlet",datos,function(data){
            
            $('#contenedor_principal').html(data);
        });        
    });
}



function load_cat_aulas(){
    $('#link_cat_aulas').click(function(event){
        event.preventDefault();
        //$('#contenedor_principal').load("admin_cat_aulas.jsp");
        datos="action=cargar_registros";
        $.post("Cat_aula_servlet",datos,function(data){
            $('#contenedor_principal').html(data);
        });
    });
}

function load_edificios(){
    var datos="";
    $('#link_cat_edificios').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load("admin_edificios.jsp");
    });
}

function load_empleados(){
    $('#link_empleados').click(function(event){

        event.preventDefault();
        //$('#contenedor_principal').load("admin_empleados.jsp");
        $.post("empleado_servlet",datos,function(data){
            $('#contenedor_principal').html(data);
            
        });
    });
    
}

function load_rubricas(){
    $('#link_rubricas').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load('admin_rubricas.jsp');;
    });
}

function load_admin_cursos(){
    $('#link_admin_cursos').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load('admin_cursos.jsp');
    });
}












