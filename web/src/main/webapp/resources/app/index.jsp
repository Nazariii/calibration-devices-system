<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="uk,en,ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Виконавець послуг</title>
    <link href="/resources/assets/bower_components/ng-table/ng-table.css" rel="stylesheet">
    <link href="/resources/assets/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/assets/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/assets/bower_components/chosen/chosen.min.css">

    <link href="/resources/assets/css/provider.css" rel="stylesheet">
    <link href="/resources/assets/css/calibrator.css" rel="stylesheet">


    <link href="/resources/assets/css/application-form.css" rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<link href="/resources/assets/bower_components/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.css" rel="stylesheet">
<link rel="stylesheet"href="/resources/assets/winmarkltd-BootstrapFormHelpers-0d89ab4/dist/css/bootstrap-formhelpers.min.css">
<link rel="stylesheet" href="/resources/assets/css/loader.css">

    <!-- Ionicons -->
    <link href="/resources/assets/AdminLTE-master/css/ionicons.min.css" rel="stylesheet" type="text/css" />



    <!-- jvectormap -->
    <link href="/resources/assets/AdminLTE-master/css/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
    <!-- fullCalendar -->
    <link href="/resources/assets/AdminLTE-master/css/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css" />
    <!-- Daterange picker -->
    <link href="/resources/assets/AdminLTE-master/css/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap wysihtml5 - text editor -->
    <link href="/resources/assets/AdminLTE-master/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="/resources/assets/AdminLTE-master/css/AdminLTE.css" rel="stylesheet" type="text/css" />

</head>

<body  class="skin-blue">

<div id="employeeModule" class="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-fixed-top" role="navigation"
         ng-controller="TopNavBarControllerProvider">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand"style="color:#383838;">Централізована система повірки лічильників</a>
        </div>

        <div ng-controller="InternationalizationController" >
            <ul class="nav navbar-nav">
                <li class="dropdown" dropdown on-toggle="toggled(open)">
                    <a class="dropdown-toggle" dropdown-toggle>
                        {{ 'LANG' | translate }} <span class="caret"></span>
                    </a>

                    <ul class="dropdown-menu">
                        <li ng-repeat="lang in languages">
                            <a ng-click="changeLanguage(lang.key)">{{ lang.name }}</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>

        <!-- Nav bar top right links -->
        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown" dropdown>
                <a class="dropdown-toggle" dropdown-toggle>
                    User name   <i class="fa fa-user fa-fw"></i><i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user"  style="background-color: lightskyblue ">
                    <li class="user-header bg-light-blue">
                        <img src="/resources/assets/AdminLTE-master/img/avatar3.png" class="img-circle" alt="User Image" />
                        <p>
                               INPUT PARAM OF USER HERE
                        </p>
                    </li>
                    <li class="user-footer" >
                        <div  style="width: 100%">
                            <a href="#" class="btn btn-default" style="color: #2C1919 ;width:230px">Profile</a>
                        </div>
                        <div  style="width: 100%">
                            <a class="btn btn-default" style="color: #2C1919;width:230px"> {{ 'SETTINGS' | translate }}</a>
                        </div>
                        <div  style="width: 100%" >
                            <a ng-click="logout()" class="btn btn-default" style="color: #2C1919 ;width:230px">{{ 'LOG_OUT' | translate
                                }} </a>
                        </div>
                    </li>
                </ul>
            </li>
        </ul>

        <!-- Sidebar -->
        <div class="row-offcanvas row-offcanvas-left" role="navigation">
            <div class="left-side sidebar-offcanvas">

                <section class="sidebar">
                <ul class="sidebar-menu" id="side-menu">
                    <sec:authorize url="/provider">
                        <li ui-sref-active="active">
                            <a ui-sref="main-panel-provider"><i class="fa fa-home fa-fw"></i>  Головна панель
                                (постачальник послуг)</a>
                        </li>
                    </sec:authorize>

                    <sec:authorize url="/calibrator">
                        <li ui-sref-active="active">
                            <a ui-sref="main-panel-calibrator"><i class="fa fa-home fa-fw"></i>  Головна панель
                                (вимірювальна лабораторія)</a>
                        </li>
                    </sec:authorize>

                    <sec:authorize url="/verificator">
                        <li ui-sref-active="active">
                            <a ui-sref="main-panel-verificator"><i class="fa fa-home fa-fw"></i>  Головна панель
                                (уповноважена повірочна лабораторія)</a>
                        </li>
                    </sec:authorize>


                    <sec:authorize url="/provider">
                        <li ui-sref-active="active" ng-controller="NotificationsControllerProvider">
                            <a ui-sref="new-verifications-provider" ng-click="reloadVerifications()"><i
                                    class="fa fa-list-alt fa-fw"></i>  Нові заявки (постачальник послуг)
                              <span id="coloredBadge" class="badge pull-right" ng-bind="countOfUnreadVerifications" ng-show="countOfUnreadVerifications>0" ng-cloak>
                              </span>
                            </a>
                        </li>
                    </sec:authorize>

                    <sec:authorize url="/calibrator">
                        <li ui-sref-active="active" ng-controller="NotificationsControllerCalibrator">
                            <a ui-sref="new-verifications-calibrator" ng-click="reloadVerifications()"><i
                                    class="fa fa-list-alt fa-fw"></i>   Нові заявки (вимірювальна лабораторія)
                           		<span id="coloredBadge" class="badge pull-right" ng-bind="countOfUnreadVerifications"  ng-show="countOfUnreadVerifications>0"  ng-cloak>
                              	</span>
                            </a>
                        </li>
                        <li ui-sref-active="active" ng-controller="MeasuringEquipmentControllerCalibrator">
                            <a ui-sref="measuring-equipment-calibrator" ng-click="onTableHandling()"><i
                                    class="fa fa-desktop"></i>  Довідник засобів вимірювальної техніки (вимірювальна лабораторія)
                            </a>
                        </li>


                    </sec:authorize>


                    <sec:authorize url="/verificator">
                        <li ui-sref-active="active" ng-controller="NotificationsControllerVerificator">
                            <a ui-sref="new-verifications-verificator" ng-click="reloadVerifications()"><i
                                    class="fa fa-list-alt fa-fw"></i> Нові заявки (уповноважена повірочна лабораторія)
                                <span id="coloredBadge" class="badge pull-right" ng-bind="countOfUnreadVerifications"  ng-show="countOfUnreadVerifications>0" ng-cloak>
                              	</span>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize url="/employee/admin/">

                        <li ui-sref-active="active">
                            <a ui-sref="employee-show-provider"><i class="fa fa-users"></i> Переглянути усіх працівників</a>
                        </li>
                    </sec:authorize>

                    <sec:authorize url="/calibrator">
                        <li ui-sref-active="active">
                            <a ui-sref="employee-show-calibrator"><i class="fa fa-users"></i> Переглянути усіх працівників</a>
                        </li>
                    </sec:authorize>

                    <sec:authorize url="/provider">
                        <li ui-sref-active="active">
                            <a ui-sref="verifications-archive-provider"><i class="fa fa-archive fa-fw"></i>   Архів
                                повірок</a>
                        </li>
                    </sec:authorize>
					 <sec:authorize url="/calibrator">
                        <li ui-sref-active="active">
                            <a ui-sref="verifications-archive-calibrator"><i class="fa fa-archive fa-fw"></i> Архів
                                повірок</a>
                        </li>
                    </sec:authorize>
                     <sec:authorize url="/verificator">
                        <li ui-sref-active="active">
                            <a ui-sref="verifications-archive-verificator"><i class="fa fa-archive fa-fw"></i> Архів
                                повірок</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize url="/provider/admin/">
                        <li ui-sref-active="active">
                            <a ui-sref="statistic-show-providerEmployee"><i class="fa fa-bar-chart"></i>   Статистика
                                продуктивності працівників</a>
                        </li>
                    </sec:authorize>
                </ul>
                </section>
            </div>

        </div>
    </nav>
    <div ui-view></div>
</div>
<script type="text/javascript" data-main="/resources/app/runApp"
        src="/resources/assets/bower_components/requirejs/require.js"></script>
<script src="/resources/assets/bower_components/jquery/dist/jquery.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js" type="text/javascript"></script>
<script   src="/resources/assets/bower_components/chosen/chosen.jquery.min.js"
        type="text/javascript"></script>
<script type="text/javascript">
    $(".chzn-select").chosen();
    $(".chzn-select-deselect").chosen({
        allow_single_deselect: true
    });
    $("#states").chosen();
</script>

<script src="/resources/assets/js/vendor/modernizr-2.6.2.min.js"></script>
<script src="/resources/assets/bower_components/bootstrap-switch/dist/js/bootstrap-switch.js"></script>
<script src="/resources/assets/winmarkltd-BootstrapFormHelpers-0d89ab4/dist/js/bootstrap-formhelpers.js"></script>
<script src="/resources/assets/js/main.js"></script>


<!-- jvectormap -->
<script src="/resources/assets/AdminLTE-master/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js" type="text/javascript"></script>
<script src="/resources/assets/AdminLTE-master/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js" type="text/javascript"></script>
<!-- fullCalendar -->
<script src="/resources/assets/AdminLTE-master/js/plugins/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
<!-- jQuery Knob Chart -->
<script src="/resources/assets/AdminLTE-master/js/plugins/jqueryKnob/jquery.knob.js" type="text/javascript"></script>
<!-- daterangepicker -->
<script src="/resources/assets/AdminLTE-master/js/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="/resources/assets/AdminLTE-master/js/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js" type="text/javascript"></script>
<!-- iCheck -->
<script src="/resources/assets/AdminLTE-master/js/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
!-- AdminLTE App -->
<script src="/resources/assets/AdminLTE-master/js/AdminLTE/app.js" type="text/javascript"></script>
</body>
</html>