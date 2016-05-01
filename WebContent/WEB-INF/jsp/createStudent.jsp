<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.uta.cse.group9.model.User"%>
<%@ page import="edu.uta.cse.group9.util.JSPMap"%>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<jsp:include page="templates/header.jsp" />
<head>
<script type="text/javascript">
	function changetextbox() {

		if (document.getElementById("enrollment_type").value === "1") {
			document.getElementById("personalEmail").value = '';
			document.getElementById("personalEmail").setAttribute("disabled",
					true);
			document.getElementById("email").removeAttribute("disabled");

			document.getElementById("uta_id").removeAttribute("disabled");

		} else if (document.getElementById("enrollment_type").value === "0") {

			//Enabled Attribute
			document.getElementById("personalEmail")
					.removeAttribute("disabled");

			//Disabled attribute
			document.getElementById("email").value = '';
			document.getElementById("uta_id").value = '';
			document.getElementById("email").setAttribute("disabled", true);
			document.getElementById("uta_id").setAttribute("disabled", true);

		}
	}
	
	
	function checkPassword(form){
		if(form.password.value != "" && form.password.value == form.confirm_password.value) {
		      if(form.password.value.length < 6 && form.password.value.length > 20) {
		        alert("Error: Password must contain at least six characters!");
		        form.password.focus();
		        return false;
		      }
		      re = /[0-9]/;
		      if(!re.test(form.password.value)) {
		        alert("Error: password must contain at least one number (0-9)!");
		        form.password.focus();
		        return false;
		      }
		      re = /[a-z]/;
		      if(!re.test(form.password.value)) {
		        alert("Error: password must contain at least one lowercase letter (a-z)!");
		        form.password.focus();
		        return false;
		      }
		      re = /[A-Z]/;
		      if(!re.test(form.password.value)) {
		        alert("Error: password must contain at least one uppercase letter (A-Z)!");
		        form.password.focus();
		        return false;
		      }
		    } else {
		      alert("Error: Please check that you've entered your password!");
		      form.password.focus();
		      return false;
		    }
		alert("You entered a valid password: ");
	    return true;
	}
	
</script>
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
			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-primary">
					<div class="panel-heading">Student Registration</div>
					<div class="panel-body">
						<form name="user_create" id="user_create" onsubmit="return checkPassword(this)" class="form-horizontal"
							method="post" action="<%=LinkMap.STUDENT_CREATE_ACCOUNT%>"
							role="form">

							<div class="form-group" align="center">
								<label class="col-sm-2 control-label" for="EnrollmentType">Enrollment
									Type</label>
								<div class="col-sm-4">
									<select id="enrollment_type" name="enrollment_type"
										class="form-control" onChange="changetextbox();" required>
										<option value="">--------Select--------</option>
										<option value="0">Prospective</option>
										<option value="1">Current</option>
										<!--  <option value="2">Alumni</option> -->
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="firstname">First
									Name</label>
								<div class="col-sm-4">
									<input name="firstname" type="text" class="form-control"
										id="firstname" placeholder="First Name" required>
								</div>

								<label class="col-sm-2 control-label" for="lastname">Last
									Name</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="lastname"
										id="lastname" placeholder="Last Name" required>
								</div>
							</div>
							<!-- These should be generated dynamically -->
							<div class="form-group">
								<label class="col-sm-2 control-label" for="student_type">Student
									Type</label>
								<div class="col-sm-4">
									<select id="student_type" name="student_type"
										class="form-control" required>
										<option value="">--------Select--------</option>
										<option value="0">Undergraduate</option>
										<option value="1">Graduate</option>
										<option value="2">Doctorate</option>
									</select>
								</div>
							</div>

							<div class="form-group has-feedback">
								<label class="col-sm-2 control-label">UTA ID</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="uta_id"
										id="uta_id" placeholder="1001000000" required>
								</div>
								<label class="col-sm-2 control-label">Email</label>
								<div class="col-sm-4">
									<input type="email" class="form-control" id="personalEmail"
										name="personalEmail" placeholder="johnsmith@abc.com" required>
									<span
										class="glyphicon glyphicon-envelope form-control-feedback"
										aria-hidden="true"></span>
								</div>
							</div>

							<div class="form-group has-feedback">
								<label class="col-sm-2 control-label">UTA Email</label>
								<div class="col-sm-4">
									<input type="email" class="form-control" id="email"
										name="email" placeholder="first.last@mavs.uta.edu" required>
									<span
										class="glyphicon glyphicon-envelope form-control-feedback"
										aria-hidden="true"></span>
								</div>

								<label class="col-sm-2 control-label">Username</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="username"
										name="username" placeholder="Username"> <span
										class="glyphicon glyphicon-user form-control-feedback"
										aria-hidden="true"></span>
								</div>
							</div>

							<div class="form-group has-feedback">
								<label class="col-sm-2 control-label">Password</label>
								<div class="col-sm-4">
									<input type="password" class="form-control" id="password"
										name="password" placeholder="Password" required> <span
										class="glyphicon glyphicon-lock form-control-feedback"
										aria-hidden="true"></span>
								</div>

								<label class="col-sm-2 control-label">Confirm Password</label>
								<div class="col-sm-4">
									<input type="password" class="form-control"
										id="confirm_password" name="confirm_password"
										placeholder="Confirm Password" required> <span
										class="glyphicon glyphicon-lock form-control-feedback"
										aria-hidden="true"></span>
								</div>
							</div>

							<div class="form-group has-feedback">
								<label class="col-sm-2 control-label">Telephone</label>
								<div class="col-sm-4">
									<input type="tel" class="form-control" id="tel" name="tel"
										placeholder="telephone number" required> <span
										class="glyphicon glyphicons-earphone form-control-feedback"
										aria-hidden="true"></span>
								</div>
							</div>

							<button type="submit" class="btn btn-Primary col-md-offset-5">Create
								Student</button>
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