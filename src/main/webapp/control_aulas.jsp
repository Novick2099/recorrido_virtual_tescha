<%-- 
    Document   : control_aulas
    Created on : 14/11/2018, 09:19:40 AM
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

        <div class="row">
            <h2 class="text-center">Aulas Actualmente En El Edificio</h2>
            <div class="col-sm-8 col-md-4">
               
                    <div class="form-row">
                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                            <span class="input-group-addon">Edificío</span>
                            <select class="form-control">
                                <option value="0">Elige un edificio</option>
                            </select>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button">Buscar</button>
                            </span>
                        </div> <!--Termina Renglon Formulario-->

                    </div><!-- Termina Formulario-->
                 

            </div>
            <div class="col-md-5 col-sm-9">

            </div>
        </div><!---Termina row -->
        <div class="row"><!--Row -->
            <div class="col-sm-6 col-md-7 ">
                <form>
                    <div class="form-row">
                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                        <span class="input-group-addon ">Aula</span>
                        <select class="form-control">
                            <option value="0">Elige un Aula</option>
                        </select>
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">Buscar</button>
                        </span>

                        <span class="input-group-addon">Filtrar:</span>
                        <input type="text" class="form-control" name="filtro">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">Buscar</button>
                        </span>
                    </div> <!--Termina Renglon Formulario-->
                </div>
                </form>
                <br>
                <br>
               


            </div>

            <div class="col-md-5">

            </div>
        </div>
        <!--Inicia apartado de aulas-->
        <div class="row"><!--Row -->

            <div class="col-sm-12 col-md-7 ">

                <div class="form-row">
                    <!--Prototipo de funcionamiento, buscar como se va a seleccionar el id mediante ajax para mandar como parametro al controlador,
                            revisar como mandar a llamar funciones javascript desde html, podria ser que cuando se genera el codigo html para la tabla se escriba la invocacion al metodo del javascript como onclick="actualizar(id)"-->

                    <div class="row">

                        <div class="col-sm-12 col-md-12">
                            <table class="table table-hover">
                                <tr>
                                    <th style='display:none;'>id_aula</th>
                                    <th>Aula</th><th>Edificio</th><th>Categoría</th><th>Actualizar</th><th>Eliminar</th></tr>
                                <tr><td style='display:none;'>1</td><td>Laboratorio Ingles</td>
                                    <td>Nezahualcoyolt</td>
                                    <td >
                                        Laboratorio
                                    </td>
                                    <td><!-- Button trigger modal -->
                                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#Modal_Actualizar">
                                            Actualizar
                                        </button>
                                    </td>

                                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                                </tr>
                                <tr><td style='display:none;'>1</td><td>Finanzas</td>
                                    <td>Nezahualcoyolt</td>
                                    <td>Oficina</td>

                                    <td><button type="button" class="btn btn-default">Actualizar</button></td>
                                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                                </tr>

                            </table>

                        </div>
                    </div>

                </div>


            </div>
            <div class="col-sm-12 col-md-4 ">
                <h2 class="text-center">Agregar Aula Aquí</h2>
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-default center-block" data-toggle="modal" data-target="#Modal_Agregar">
                    Agregar
                </button>






            </div>



            <div class="col-md-12">

                <!-- Modal -->
                <div class="modal fade" id="Modal_Actualizar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h3 class="modal-title text-center" id="exampleModalLabel">Actualizar Aula</h3>

                            </div>
                            <div class="modal-body">
                                <!-- aqui va el codigo para el formulario  -->
                                <form>
                                    <div class="form-row">

                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Nombre del Aula:</span>
                                            <input type="text" class="form-control" name="aula">

                                        </div> <!--Termina Renglon Formulario-->

                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Edificio</span>
                                            <select class="form-control">
                                                <option value="0">Elige un Edificio</option>
                                            </select>
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Categoria:</span>
                                            <select class="form-control">
                                                <option value="0">Elige una Categoría</option>
                                            </select>
                                        </div> <!--Termina Renglon Formulario-->

                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <button type="button" class="btn btn-default">Actualizar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal -->
                <div class="modal fade" id="Modal_Agregar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h3 class="modal-title text-center" id="exampleModalLabel">Agregar Aula</h3>

                            </div>
                            <div class="modal-body">
                                <!-- aqui va el codigo para el formulario  -->
                                <form>
                                    <div class="form-row">
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Aula</span>
                                            <input type="text" class="form-control" name="aula">
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Edificio</span>
                                            <select class="form-control">
                                                <option value="0">Elige un Edificio</option>
                                            </select>
                                        </div> <!--Termina Renglon Formulario-->
                                        <div class="input-group "> <!--Inicia Renglon Formulario -->
                                            <span class="input-group-addon">Categoria:</span>
                                            <select class="form-control">
                                                <option value="0">Elige una Categoría</option>
                                            </select>
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

            <!--Termina apartado de aulas-->
            
            
            
    </body>
</html>
