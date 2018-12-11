<%-- 
    Document   : admin_Carreras
    Created on : 21/11/2018, 09:32:26 AM
    Author     : MarzoNegro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de carreras</title>
        <script type="text/javascript" src="js/admin_carreras.js"></script>

    </head>
    <body>
        <div class="row">
            <h2 class="text-center">Carreras Existentes En El Sistema</h2>
            <div class="col-sm-12 col-md-6">
                <table class="table table-hover">
                    <tr>
                        <th>Carrera</th><th>Actualizar</th><th>Eliminar</th>

                        <c:forEach items="${lista_carrera}" var="Carrera">
                        <tr>
                            <td style='display:none;'>${Carrera.idCarrera}</td>
                            <td>${Carrera.nombreCarrera}</td>
                            
                            <td><!-- Button trigger modal -->
                                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#Modal_Actualizar">
                                    Actualizar
                                </button>
                            </td>
                            <td><button type="button" class="btn btn-default">Eliminar</button></td>                   
                        </tr>
                    </c:forEach>


                </table>
            </div>
            <div class="col-md-1">

            </div>
            <div class="col-sm-12 col-md-4">
                <div class="input-group center-block">
                <h2 class="text-center">Agregar Carrera</h2>
                <button  type="button" class="btn btn-default center-block" data-toggle="modal" data-target="#Modal_Insercion">Agregar Carrera</button>    
                    
                </div>
                
                


            </div>


        </div><!--Termina renglón-->

        <!-- Modal -->
        <div class="modal fade" id="Modal_Actualizar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title text-center" id="exampleModalLabel">Actualizar Carrera</h3>
                    </div>
                    <div class="modal-body">
                        <!-- aqui va el codigo para el formulario  -->
                        <form>
                            <div class="form-row">
                                <div class="input-group "> <!--Inicia Renglon Formulario -->
                                    <span class="input-group-addon">Carrera:</span>
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
        <div class="modal" id="Modal_Insercion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title text-center" id="exampleModalLabel">Agregar Carrera</h3>
                    </div>
                    <div class="modal-body">
                        <!-- aqui va el codigo para el formulario  -->
                        <form>
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon" >Carrera:</span>
                                <input id="txt_carrera" type="text" class="form-control" name="carrera">
                            </div> <!--Termina Renglon Formulario-->
                        </form>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button id="guardar_reg_c" type="button" class="btn btn-default">Agregar Carrera</button>
                    </div>
                </div>
            </div>

        </div><!--termina modal-->


    </body>
</html>





