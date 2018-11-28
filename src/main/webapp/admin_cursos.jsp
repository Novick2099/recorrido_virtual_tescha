<%-- 
    Document   : admin_cursos
    Created on : 25/11/2018, 10:35:59 PM
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
        <h1 class="text-center">Administración De Cursos</h1>
        <div class="row">
            <div class="col-sm-12 col-md-8" 
                 <div class="form-row">
                    <div class="input-group "> <!--Inicia Renglon Formulario -->
                        <span class="input-group-addon">Materia</span>
                        <input type="text" class="form-control" name="aula">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">Buscar</button>
                        </span>
                    </div> <!--Termina Renglon Formulario-->
                    <br>
                    <div class="table-responsive">
                        <table class="table table-hover"> 
                            <thead><tr>
                                    <th scope="col" class="text-center">Carrera</th>
                                    <th scope="col" class="text-center">Materia</th>
                                    <th scope="col" class="text-center">Semestre</th>
                                    <th scope="col" class="text-center">Profesor</th>
                                    <th class="text-center">Horario</th>
                                    <th class="text-center">Día</th>
                                    <th class="text-center">Aula</th>

                                    <th scope="col" class="text-center">Eliminar</th>

                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="text-center">Ingeniería En Sistemas Computacionales</td>
                                    <td class="text-center">Cálculo Diferencial E Integral</td>
                                    <td class="text-center">1</td>
                                    <td class="text-center">Ladislao Aldama Rojano</td>
                                    <td class="text-center">7:00-9:00</td>
                                    <td class="text-center">Lunes</td>
                                    <td class="text-center">D2</td>
                                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                                </tr>
                                <tr>
                                    <td class="text-center">Ingeniería En Sistemas Computacionales</td>
                                    <td class="text-center">Cálculo Diferencial E Integral</td>
                                    <td class="text-center">1</td>
                                    <td class="text-center">Ladislao Aldama Rojano</td>
                                    <td class="text-center">7:00-9:00</td>
                                    <td class="text-center">Miércoles</td>
                                    <td class="text-center">D2</td>
                                    <td><button type="button" class="btn btn-default">Eliminar</button></td>
                                </tr>
                            </tbody>

                        </table>  
                    </div>

                </div>








                <div class="col-sm-12 col-md-4">
                    <h2 class="text-center">Crear Curso</h2>
                    <form>
                        <div class="form-row">
                            <h2 class="text-center">Crear Curso</h2>
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Carrera:</span>
                                <select class="form-control">
                                    <option value="0">Elige una Carrera</option>

                                </select>

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Profesor:</span>
                                <select class="form-control">
                                    <option value="0">Elige un Profesor</option>

                                </select>

                            </div> <!--Termina Renglon Formulario-->

                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Hora Inicio:</span>
                                <input type="text" class="form-control" name="clave">
                                <span class="input-group-addon">Hora Fin</span>
                                <input type="text" class="form-control" name="clave">

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Dia:</span>
                                <select class="form-control">
                                    <option value="0">Elige un dia</option>
                                    <option value="1">Lunes</option>
                                    <option value="2">Martes</option>
                                    <option value="3">Miércoles</option>
                                    <option value="4">Jueves</option>
                                    <option value="5">Viernes</option>


                                </select>

                            </div> <!--Termina Renglon Formulario-->
                            <div class="input-group "> <!--Inicia Renglon Formulario -->
                                <span class="input-group-addon">Clave De La Materia:</span>
                                <input type="text" class="form-control" name="materia">

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
    </body>
</html>
