<%-- 
    Document   : principal
    Created on : 14/11/2018, 10:05:46 AM
    Author     : MarzoNegro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="css/bootstrap.css" type="text/css"/>
        <link rel="stylesheet" href="css/sidebar.css" type="text/css"/>
        <script type="text/javascript" src="js/jquery-3.js"></script>        
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/front-controller.js"></script>

    </head>
    <body>

        <div class="container">
            <img src="img/header_tescha.png" class="img-responsive" alt="Responsive image">




            <!--Inicio Codigo menu lateral-->
            <div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
                <div class="panel panel-default" style="border:none">
                    <div class="panel-heading menu-lateral-title-body">
                        <h2 class="menu-lateral-title">Administración del Sistema</h2>
                    </div>

                    <!-- Inicia Segmento de opcion de menu -->
                    <div class="panel-body menu-lateral-body">
                        <span class="glyphicon glyphicon-menu-right menu-lateral-text" aria-hidden="true"></span>
                        <span class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Cargos de Responsabilidad <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li> <a id="link_cargos">Administrar Cargos</a></li>
                                <li> <a>Administrar Funciones</a></li>
                            </ul>
                        </span>
                    </div>
                    <!-- Termina Segmento de opcion de menu -->

                    <!-- Inicia Segmento de opcion de menu -->
                    <div class="panel-body menu-lateral-body">
                        <span class="glyphicon glyphicon-menu-right menu-lateral-text" aria-hidden="true"></span>
                        <span class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Carreras <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li> <a id="link_admin_cursos">Cursos </a></li>
                                <li> <a id="link_carreras">Mostrar Carreras</a> </li>
                                <li> <a id="link_rubricas">Rúbrica</a></li>
                            </ul>
                        </span>
                    </div>
                    <!-- Termina Segmento de opcion de menu -->

                    <!-- Inicia Segmento de opcion de menu -->
                    <div class="panel-body menu-lateral-body">
                        <span class="glyphicon glyphicon-menu-right menu-lateral-text" aria-hidden="true"></span>
                        <span class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Edificios <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li> <a id="link_aulas">Administrar Aulas</a></li>
                                <li> <a id="link_cat_aulas">Administrar Categorias de Aulas</a></li>
                                <li> <a id="link_cat_edificios">Administrar Edificios</a> </li>

                                <!--<li> <a href="">Categoria Aulas Aulas</a></li> -->
                            </ul>
                        </span>
                    </div>
                    <!-- Termina Segmento de opcion de menu -->

                    <!-- Inicia Segmento de opcion de menu -->
                    <div class="panel-body menu-lateral-body">
                        <span class="glyphicon glyphicon-menu-right menu-lateral-text" aria-hidden="true"></span>
                        <span class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Empleados <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li> <a id="link_empleados">Administrar Datos de Empleados</a></li>
                                <li> <a>Administrar Asignaciones de empleados</a></li>
                            </ul>
                        </span>
                    </div>
                    <!-- Termina Segmento de opcion de menu -->

                    <!-- Inicia Segmento de opcion de menu -->
                    <div class="panel-body menu-lateral-body">
                        <span class="glyphicon glyphicon-menu-right menu-lateral-text" aria-hidden="true"></span>
                        <a id="link_materias">Materias</a>
                    </div>
                    <!-- Termina Segmento de opcion de menu -->

                    <!-- Inicia Segmento de opcion de menu -->
                    <div class="panel-body menu-lateral-body">
                        <span class="glyphicon glyphicon-menu-right menu-lateral-text" aria-hidden="true"></span>
                        <a id="Salir">Salir</a>
                    </div>
                    <!-- Termina Segmento de opcion de menu -->
                </div>	
            </div>
            <!--Fin Codigo menu lateral-->
            <div class="col-xs-12 col-sm-8 col-md-9 col-lg-10">

                <div class="row">
                    <div class="col-md-12">
                        <h2 class="text-center"></h2>
                        <div id="contenedor_principal">
                            <!--<p>Contenido de Load aqui</p>-->
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
