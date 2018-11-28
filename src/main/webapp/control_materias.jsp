<%-- 
    Document   : Materias
    Created on : 14/11/2018, 03:49:44 PM
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
        <h2 class="text-center">Administración De Materias</h2>
        <div class="col-sm-12 col-md-7">
            <h2>Materias Existentes En El Sistema</h2>
            <div class="form-row">
                <div class="input-group "> <!--Inicia Renglon Formulario -->
                    <span class="input-group-addon">Materia</span>
                    <input type="text" class="form-control" name="aula">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Buscar</button>
                    </span>
                </div> <!--Termina Renglon Formulario-->
            </div>
            <br>
            <table class="table table-hover">
                <tr>
                    <th class="text-center">Clave De La Materia</th>
                    <th class="text-center">Nombre De La Materia</th>
                    <th class="text-center">Créditos</th>
                    <th class="text-center">Actualizar</th>
                    <th class="text-center">Eliminar</th>
                </tr>
                <tr>
                    <td >CDM-10</td>
                    <td>Calculo Diferencia e Integral</td>
                    <td >
                        5
                    </td>
                    <td>
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#Modal_Actualizar">Actualizar</button>
                    </td>
                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                </tr>
                <tr><td >CDM-1056</td><td>Fundamentos de Programación</td>

                    <td>4</td>

                    <td><button type="button" class="btn btn-default">Actualizar</button></td>
                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                </tr>

            </table>


        </div>



        <div class="col-sm-8 col-md-4">
            <form>
                <div class="form-row">
                    <h2 class="text-center">Agregar Materias</h2>
                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                        <span class="input-group-addon">Clave:</span>
                        <input type="text" class="form-control" name="clave">

                    </div> <!--Termina Renglon Formulario-->
                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                        <span class="input-group-addon">Nombre De La Materia:</span>
                        <input type="text" class="form-control" name="materia">

                    </div> <!--Termina Renglon Formulario-->
                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                        <span class="input-group-addon">Número De Créditos:</span>
                        <input type="text" class="form-control" name="num_creditos">

                    </div> <!--Termina Renglon Formulario-->
                    <br>
                    <!--Buscar como centrar ese boton -->						
                    <div class="input-group center-block">
                        <span class="input-group center-block" >
                            <button type="submit" class="btn btn-default center-block">Agregar</button>
                        </span>
                    </div>

                </div>
            </form>
            <!-- Modal -->
            <div class="modal fade" id="Modal_Actualizar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title text-center" id="exampleModalLabel">Actualizar Materia</h3>

                        </div>
                        <div class="modal-body">
                            <!-- aqui va el codigo para el formulario  -->
                            <form>
                                <div class="form-row">
                                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                                        <span class="input-group-addon">Clave:</span>
                                        <input type="text" class="form-control" name="aula">
                                    </div> <!--Termina Renglon Formulario-->
                                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                                        <span class="input-group-addon">Nombre de la Materia:</span>
                                        <input type="text" class="form-control" name="aula">
                                    </div> <!--Termina Renglon Formulario-->
                                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                                        <span class="input-group-addon">Número de Créditos:</span>
                                        <input type="text" class="form-control" name="aula">
                                    </div> <!--Termina Renglon Formulario-->

                                </div>
                            </form>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-default">Insertar Nueva Aula</button>
                        </div>
                    </div>
                </div>
            </div><!--termina modal-->

        </div>    

    </body>
</html>
