<%@page import="com.capg.rms.beans.Retailer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Data</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<jsp:include page="navBar.jsp"></jsp:include>

<body>

	<% Retailer retailer = (Retailer)session.getAttribute("retailer");
	if(retailer != null){
		out.print("<h1>Hello , " + retailer.getName() +"</h1>");
	}else{
		response.sendRedirect("./login");
	}
%>

	<h5>${retailer}</h5>


	<div class="col-md-4 offset-4 mt-4 card">
		<div class="card-body">
			<h1 class="h1 text-center">Update Password</h1>
			<form action="./userUpdate" method="post">

				<label for="username">User Id</label>
				<div class="form-group">
					<input type="text" class="form-control" name="userId"
						disabled="disabled" value="${retailer.getId()}">
				</div>
				<label for="username">Username</label>
				<div class="form-group">
					<input type="text" class="form-control" name="userName"disabled="disabled"
						value="${retailer.getName()}" required>
				</div>

				<label for="password">New Password</label>
				<div class="form-group">

					<input type="password" class="form-control" name="password"
						value="${retailer.getPassword()}" required>

				<div class="form-group">
					<button class="btn btn-info float-right">Update</button>
				</div>
			</form>
		</div>
	</div>
	<center>
		<h4>${msg}</h4>
	</center>
</body>
</html>