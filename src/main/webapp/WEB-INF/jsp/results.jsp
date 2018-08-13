<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<title>Processed logs</title>
</head>
<body>
	<div class="container-fluid bg">
		
		<table class="table">
		<thead class="thead-dark">
			<tr>
				<th>Event Id</th>
				<th>Id</th>
				<th>Duration</th>
				<th>Type</th>
				<th>Host</th>
				<th>Alert</th>
			</tr>
		</thead>
		
		<c:forEach items="${eventsList}" var="event">
			<tr>
				<td>${event.eventId}</td>
				<td>${event.id}</td>
				<td>${event.duration}]</td>
				<td>${event.type}/</td>
				<td>${event.host}</td>
				<td>${event.alert}</td>
			</tr>
		</c:forEach>
	</table>
		
	
		<div class="row">
			<div class="col"></div>
			<div class="col">
				<a class="dropdown-item" href="${contextPath}/process-logs/"><button
						type="button" class="btn btn-dark">Back</button></a>
			</div>
			<div class="col"></div>
		</div>
		
		
	</div>
	
	
</body>
</html>