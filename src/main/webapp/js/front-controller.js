
$(document).ready(function () {
    cargar_v_aulas();
    cargar_v_materias();
    load_admin_cargos();
    load_carreras();
    load_cat_aulas();
    load_edificios();
    load_empleados();
    load_rubricas();
    load_admin_cursos();

});

function cargar_v_aulas() {
    $("#link_aulas").click(function (event) {
        event.preventDefault();
        $('#contenedor_principal').load("control_aulas.jsp");
    });
}

function cargar_v_materias() {
    $('#link_materias').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load("control_materias.jsp")
        
    });
}

function load_admin_cargos(){
    $('#link_cargos').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load("admin_cargos.jsp");
    });
    
}

function load_carreras(){
    $('#link_carreras').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load("admin_Carreras.jsp");
        
    });
}

function load_cat_aulas(){
    $('#link_cat_aulas').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load("admin_cat_aulas.jsp");
    });
}

function load_edificios(){
    $('#link_cat_edificios').click(function(event){
        event.preventDefault();
        $('#contenedor_principal').load("admin_edificios.jsp");
    });
}

function load_empleados(){
    $('#link_empleados').click(function(event){

        event.preventDefault();
        $('#contenedor_principal').load("admin_empleados.jsp");
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

