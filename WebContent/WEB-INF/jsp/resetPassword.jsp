<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.uta.cse.group9.model.User"%>
<%@ page import="edu.uta.cse.group9.util.JSPMap"%>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<jsp:include page="templates/header.jsp" />
<div class="container">
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div id="inversenavbar" class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="<%=LinkMap.HOME%>"> MavAdvisor </a>
		</div>
	</div>
	</nav>
</div>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>
</head>
<body>
	<div></div>

	<div class="container">
		<jsp:include page="templates/alert.jsp" />
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-heading">Password Reset</div>
					<div class="panel-body">
					<form name="reset_password" id="reset_password" class="form-horizontal"
							method="post" action="<%=LinkMap.STUDENT_RESET_FORGOT_ACCOUNT%>"
							role="form">
						<div class="col-sm-6">
							<input type="email" class="form-control" id="resetEmail"
								name="resetEmail" placeholder="johnsmith@abc.com" required>
						</div>
						<div class="col-sm-6">
							<button type="submit" class="btn btn-Primary col-md-offset-5">Send Verification</button>
						</div>
						</form>

					</div>

				</div>
			</div>
		</div>
	</div>
	<jsp:include page="templates/footer.jsp" />
</body>
</html>