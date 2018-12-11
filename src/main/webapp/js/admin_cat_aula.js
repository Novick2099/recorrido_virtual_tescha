/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    agregar_cat_aula();
});

function agregar_cat_aula() {

    $('#btn_agregar_cat').click(function (event) {
        var cat_aula = $("#text_in_categoria").val();
        var datos = "action=guardar_registro&cat_aula=" + cat_aula;
        event.preventDefault();
        alert("datos=" + datos);
        $.post("cat_aula_servlet", datos, function (data) {
            alert(data);
            leer_registros();
        });
        $('#Modal_Agregar').modal('hide');
    });
}

function actualizar_cat_aula(){
    
}

function eliminar_cat_aula(){
    
}

function leer_registros(){
    var datos="action=cargar_registros";
    $.post("cat_aula_servlet",datos,function(data){
        $('#contenedor_principal').html(data);
    });
}

function leer_unico(){
    
}





















