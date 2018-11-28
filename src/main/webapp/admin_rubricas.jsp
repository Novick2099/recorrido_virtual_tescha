<%-- 
    Document   : rubrica
    Created on : 25/11/2018, 04:18:07 PM
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
        <div class="container">
            <div class="row">
                <h2 > Administración de Rúbricas</h2>
                <div class="col-sm-12 col-md-6">
                    <div class="table-responsive">
                        <table class="table table-hover"> 
                            <thead><tr>
                                    <th scope="col" class="text-center">Código De La Materia</th>
                                    <th scope="col" class="text-center">Nombre Completo De La Materia</th>
                                    <th scope="col" class="text-center">Semestre</th>
                                    <th scope="col" class="text-center">Numero De Créditos</th>
                                    <th class="text-center">Carrera</th>
                                    
                                    <th scope="col" class="text-center">Eliminar</th>

                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>ACF-09-01</td><td>Cálculo Diferencial</td><td>1</td><td>5</td><td>Ingeniería En Sistemas Computacionales</td>
                                   
                                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                                </tr>
                            </tbody>

                        </table>  


                    </div>
                </div>
                <div class="col-sm-12 col-md-3">
                    <h2 class="text-center">Asignar Materia A Semestre</h2>
                    <form>
                        <div class="form-row">
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Carrera:</span>
                                <select class="form-control">
                                    <option value="0">Elige Una Carrera</option>
                                </select>
                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Semestre:</span>
                                <select class="form-control">
                                    <option value="0">Elige un semestre</option>
                                    <option value="1">Primero</option>
                                    <option value="2">Segundo</option>
                                    <option value="3">Tercero</option>
                                    <option value="4">Cuarto</option>
                                    <option value="5">Quinto</option>
                                    <option value="6">Sexto</option>
                                    <option value="7">Séptimo</option>
                                    <option value="8">Octavo</option>
                                    <option value="9">Noveno</option>
                                </select>
                            </div> <!--Termina Renglon Formulario-->
                            <div class="form-row">
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Materia:</span>
                                <select class="form-control">
                                    <option value="0">Elige Una Materia</option>
                                </select>
                            </div> <!--Termina Renglon Formulario-->
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
    </body>
</html>
