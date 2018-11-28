<%-- 
    Document   : admin_edificios
    Created on : 21/11/2018, 03:58:01 PM
    Author     : MarzoNegro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <body>
        <div class="row">
            <div class="col-sm-12 col-md-5">
                <h2 class="text-center">Edificios actualmente registrados en el sistema</h2>
                <table class="table table-hover">
                    <tr>
                        <th>Edificio</th><th>Actualizar</th><th>Eliminar</th>
                    </tr>
                    <tr>
                        <td>Nezahualcoyotl</td>

                        <td>
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#Modal_Actualizar">Actualizar</button>
                        </td>
                        <td><button type="button" class="btn btn-default">Eliminar</button></td>
                    </tr>

                </table>


            </div>
            <div class="col-sm-12 col-md-4">
                <h2 class="text-center">Agregar Nueva Edificio</h2>
                <form>
                    <div class="form-row">
                        <h2 class="text-center">Agregar Edificio</h2>
                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                            <span class="input-group-addon">Edificio:</span>
                            <input type="text" class="form-control" name="clave">
                        </div>    

                        <div class="input-group center-block">
                            <span class="input-group center-block" >
                                <button type="submit" class="btn btn-default center-block">Agregar</button>
                            </span>
                        </div>


                    </div>                    
                </form>

            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="Modal_Actualizar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title text-center" id="exampleModalLabel">Actualizar Nombre Del Edificio</h3>

                    </div>
                    <div class="modal-body">
                        <!-- aqui va el codigo para el formulario  -->
                        <form>
                            <div class="form-row">
                                <div class="input-group "> <!--Inicia Renglon Formulario -->
                                    <span class="input-group-addon">Edificio:</span>
                                    <input type="text" class="form-control" name="aula">
                                </div> <!--Termina Renglon Formulario-->

                            </div>
                        </form>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-default">Actualizar Cargo</button>
                    </div>
                </div>
            </div>
        </div><!--termina modal-->
    </body>
</body>
</html>
