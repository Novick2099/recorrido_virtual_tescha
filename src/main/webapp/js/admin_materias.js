/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function (){
   agregar_materia(); 
});

function agregar_materia(){
    $('#agregar_materia').click(function(event){
        event.preventDefault();
        var clave_materia=$('#txt_in_clave').val();
        var nombre_materia=$('#txt_in_nombre').val();
        var creditos = $('#txt_in_creditos').val();
        var datos="action=guardar_registro&clave_materia="+clave_materia+"&nombre_materia="+nombre_materia+"&creditos="+creditos;
        //alert("Click en Agregar Materia: "+datos);
        $.post("materias_servlet",datos,function(data){
           // alert(data);
            leer_reg_materia();
        });
        $('#Modal_Agregar').modal('hide');
    });
}

function actualizar_materia(){
    
}

function eliminar_materia(){
    
}

function leer_reg_materia(){
    var datos="action=cargar_registros";
    $.post("materias_servlet",datos,function(data){
        $('#contenedor_principal').html(data);
    });
}

function leer_unico_materia(){
    
}



