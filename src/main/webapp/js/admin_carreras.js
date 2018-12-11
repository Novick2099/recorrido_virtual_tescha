
$(document).ready(function(){
    agregar_carrera();
});

function agregar_carrera(){
    $('#guardar_reg_c').click(function(event){
        event.preventDefault();
        var carrera=$("#txt_carrera").val();
        datos="action=guardar_registro&carrera="+carrera;
        $.post("Carreras_Servlet",datos,function(){
        leer_reg_carrera(carrera);
        });
        $('#Modal_Insercion').modal('hide');
    });
}

function actualizar_carrera(){
    
}

function eliminar_carrera(){
    
}

function leer_reg_carrera(){

    var datos="action=cargar_registros";
    $.post("Carreras_Servlet",datos,function(data){
        $('#contenedor_principal').html(data);
    });
}

function leer_unico_carrera(){
    
}


