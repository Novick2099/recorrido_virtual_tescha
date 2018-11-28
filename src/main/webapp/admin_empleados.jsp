<%-- 
    Document   : admin_empleados
    Created on : 25/11/2018, 04:34:41 PM
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

        <div class="container-fluid">
            <div class="row">

                <div class="col-sm-12 col-md-8">
                    <h2 class="text-center">Administración De Empleados</h2>
                    <form class="form">
                        <div class="form-row">
                            <div class="input-group">
                                <span class="input-group-addon">Filtrar:</span>
                                <input type="text" class="form-control" name="filtro">
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="button">Buscar</button>
                                </span>    
                            </div>

                        </div><!-- Termina Formulario-->
                    </form>
                    <div class="col-sm-8 col-md-4">


                    </div>


                </div>

            </div>
            <br><br>
            <div class="row">
                <div class="col-sm-12 col-md-8 ">
                    <div class="table-responsive">

                        <table class="table table-hover"> 
                            <thead><tr>
                                    <th scope="col">Matrícula</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Apellido Paterno</th>
                                    <th scope="col">Apellido Materno</th>
                                    <th scope="col">correo</th>
                                    <th scope="col">Telefono</th>
                                    <th scope="col">Curp</th>
                                    <th scope="col">Actualizar</th>
                                    <th scope="col">Eliminar</th>
                                </tr></thead>
                            <tbody>
                                <tr>
                                    <td>201424010</td><td>Marco Antonio</td><td>Acosta</td><td>Mendizábal</td><td>acosta.tescha@hotmail.com</td><td>3336266353</td><td>FUMJ901220HQTNNN05</td>
                                    <!-- Button trigger modal -->
                                    <td>
                                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#Modal_Actualizar">
                                            Actualizar
                                        </button>
                                    </td>
                                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                                </tr>
                            </tbody>

                        </table>    
                    </div>
                </div>
                <div class="col-sm-12 col-md-4">

                    <form>
                        <div class="form-row">
                            <h2 class="text-center">Agregar Nuevo Empleado</h2>
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Matrícula:</span>
                                <input type="text" class="form-control" name="Matricula">

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Nombre:</span>
                                <input type="text" class="form-control" name="materia">

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Apellido Paterno:</span>
                                <input type="text" class="form-control" name="num_creditos">

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Apellido Materno:</span>
                                <input type="text" class="form-control" name="num_creditos">

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Correo:</span>
                                <input type="text" class="form-control" name="num_creditos">

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Teléfono:</span>
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

                </div>

            </div>

        </div>
        
        
        <!--Modal para actualizar aquí-->
        <!-- Modal -->
                <div class="modal fade" id="Modal_Actualizar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h3 class="modal-title text-center" id="exampleModalLabel">Modificar Datos De Empleado</h3>

                            </div>
                            <div class="modal-body">
                                <!-- aqui va el codigo para el formulario  -->
                                <form>
                                    <div class="form-row">
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Matrícula:</span>
                                            <input type="text" class="form-control" name="aula">
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Nombre:</span>
                                            <input type="text" class="form-control" name="aula">
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Apellido Paterno</span>
                                            <input type="text" class="form-control" name="aula">
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Apellido Materno</span>
                                            <input type="text" class="form-control" name="aula">
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Correo</span>
                                            <input type="text" class="form-control" name="aula">
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Teléfono</span>
                                            <input type="text" class="form-control" name="aula">
                                        </div> <!--Termina Renglon Formulario-->
                                    </div>
                                </form>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <button type="button" class="btn btn-default">Actualizar Empleado</button>
                            </div>
                        </div>
                    </div>
                </div><!--termina modal-->


    </body>
</html>


