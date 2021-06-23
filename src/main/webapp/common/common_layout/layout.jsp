 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<!DOCTYPE html PUBLIC "-/W3C/DTD HTML 4.01 Transitional/EN" "http:/www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <meta charset="utf-8"> -->
<!-- <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />  -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>USOnboarding</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="resources/images/favicon.ico">
<link rel="stylesheet"href="resources/css/css/normalize.min.css">
<link rel="stylesheet" href="resources/css/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/css/themify-icons.css">
<link rel="stylesheet" href="resources/css/css/pe-icon-7-stroke.min.css">
<link rel="stylesheet" href="resources/css/css/flag-icon.min.css">
<link rel="stylesheet" href="resources/css/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/style-skin.css">
<link rel="stylesheet" href="resources/css/style.css">
<link rel="stylesheet" href="resources/css/all.css">
<link type="text/css" rel="stylesheet" href="resources/js/newdt/dataTables.bootstrap4.min.css">
</head>
<style>
table td, .table th {
    padding: 0.45rem;
}
</style>
<body>
    <div>
    <tiles:insertAttribute name="left_bar" />
     <div id="right-panel" class="right-panel">
      <tiles:insertAttribute name="header" />
     </div>
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="footer" />
    </div>
<script src="resources/js/js/jquery.min.js"></script> 
<script	src="resources/js/js/popper.min.js"></script>
<script	src="resources/js/js/bootstrap.min.js"></script>
<script	src="resources/js/js/jquery.matchHeight.min.js"></script>
<script src="resources/js/js/main.js"></script>
<script src="resources/js/newdt/popper.min.js"></script>
<script src="resources/js/newdt/jquery.dataTables.min.js"></script>
<script src="resources/js/newdt/dataTables.bootstrap4.min.js"></script>
<script src="resources/js/newdt/datatables-init.js"></script>
<script type="text/javascript" src="resources/preDefined/filevalidation.js"></script>

</body>
</html> 