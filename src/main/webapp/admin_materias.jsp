<%-- 
    Document   : Materias
    Created on : 14/11/2018, 03:49:44 PM
    Author     : MarzoNegro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="js/admin_materias.js"></script>
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
                <c:forEach items="${lista_materia}" var="Materia">
                    <tr>
                        <td >${Materia.claveMateria}</td>
                        <td>${Materia.nombreMateria}</td>
                        <td>${Materia.creditos}</td>
                        <td>
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#Modal_Actualizar">Actualizar</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-default">Eliminar</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>

        <div class="col-sm-8 col-md-4">
            <h2 class="text-center">Agregar Materias</h2>
            <!--Buscar como centrar ese boton -->						
            <div class="input-group center-block">
                <span class="input-group center-block" >
                    <button type="submit" class="btn btn-default center-block" data-toggle="modal" data-target="#Modal_Agregar" >Agregar</button>
                </span>
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
                        <button type="button" class="btn btn-default">Actualizar Carrera</button>
                    </div>
                </div>
            </div>
        </div><!--termina modal-->
        <!-- Modal -->
        <div class="modal fade" id="Modal_Agregar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                                    <input id="txt_in_clave" type="text" class="form-control" name="clave">
                                </div> <!--Termina Renglon Formulario-->
                                <div class="input-group "> <!--Inicia Renglon Formulario -->
                                    <span class="input-group-addon">Nombre De La Materia:</span>
                                    <input id="txt_in_nombre" type="text" class="form-control" name="materia">
                                </div> <!--Termina Renglon Formulario-->
                                <div class="input-group "> <!--Inicia Renglon Formulario -->
                                    <span class="input-group-addon">Número De Créditos:</span>
                                    <input id="txt_in_creditos" type="text" class="form-control" name="num_creditos">
                                </div> <!--Termina Renglon Formulario-->
                                <br>
                            </div>
                        </form>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button id="agregar_materia" type="button" class="btn btn-default">Agregar Materia</button>
                    </div>
                </div>
            </div>
        </div><!--termina modal-->

    </body>
</html>
