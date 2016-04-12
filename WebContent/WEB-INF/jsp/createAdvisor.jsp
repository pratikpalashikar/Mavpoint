<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.uta.cse.group9.model.User"%>
<%@ page import="edu.uta.cse.group9.util.JSPMap"%>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<jsp:include page="templates/header.jsp" />
<head>
</head>
<body>
	<c:choose>
		<c:when test="${user == null}">
			<jsp:include page="templates/navbar.jsp" />
		</c:when>
		<c:otherwise>
			<jsp:include page="${user.getHeader()}" />
		</c:otherwise>
	</c:choose>

	<!-- CONTENT AREA -->
	<div class="container">
		<jsp:include page="templates/alert.jsp" />
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-primary">
					<div class="panel-heading">Create Advisor</div>
					<div class="panel-body">
						<form name="advisor_create" id="advisor_create"
							class="form-horizontal" method="post"
							action="<%=LinkMap.ADMIN_CREATE_ADVISOR%>" role="form">

							<div class="form-group">
								<label class="col-sm-3 control-label">First Name</label>
								<div class="col-sm-9">
									<input name="firstname" type="text" class="form-control"
										id="firstname" placeholder="First Name" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">Last Name</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" name="lastname"
										id="lastname" placeholder="Last Name" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">UTA ID</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="uta_id"
										name="uta_id" placeholder="1001000000" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">UTA Email</label>
								<div class="col-sm-9">
									<input type="email" class="form-control" id="email"
										name="email" placeholder="first.last@mavs.uta.edu" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">Username
									(optional)</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="username"
										name="username" placeholder="Username">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">Password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="password"
										name="password" placeholder="Password" required>
								</div>
							</div>
							
							

							<div class="form-group">
								<label class="col-sm-3 control-label">Confirm Password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control"
										name="confirm_password" id="confirm_password"
										placeholder="Confirm Password" required>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Telephone</label>
								<div class="col-sm-9">
									<input type="tel" class="form-control" id="tel" name="tel"
										placeholder="telephone number" required> <span
										class="glyphicon glyphicons-earphone form-control-feedback"
										aria-hidden="true"></span>
								</div>
							</div>

						

							<button type="submit" class="btn btn-Primary col-md-offset-5"
								id="btnSubmit">Create Advisor</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END CONTENT AREA -->

	<jsp:include page="templates/footer.jsp" />
</body>
</html>