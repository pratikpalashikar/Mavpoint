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
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">Reset Password</div>
					<div class="panel-body">
						<form name="reset_password" id="reset_password" class="form-horizontal" method="post"
							action="<%=LinkMap.RESET_PASSWORD%>" role="form">

							<div class="form-group">
								<label class="col-xs-5 control-label">Password</label>
								<div class="col-xs-7">
									<input name="current_password" type="password" class="form-control"
										id="current_password" placeholder="Current Password" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-xs-5 control-label">New Password</label>
								<div class="col-xs-7">
									<input type="password" class="form-control" name="new_password"
										id="new_password" placeholder="New Password" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-5 control-label">Confirm New Password</label>
								<div class="col-xs-7">
									<input type="password" class="form-control" name="c_new_password"
										id="c_new_password" placeholder="Confirm New Password" required>
								</div>
							</div>
							<button type="submit" class="btn btn-Primary col-md-offset-5" >Reset Password</button>
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