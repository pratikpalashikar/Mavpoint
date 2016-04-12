<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.uta.cse.group9.model.User"%>
<%@ page import="edu.uta.cse.group9.util.JSPMap"%>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<jsp:include page="templates/header.jsp" />
<head>
	<!-- DateTime Picker Files -->
	<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">
	<script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
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
					<div class="panel-heading">Create Appointment</div>
					<div class="panel-body">
						<form name="appointment_create" id="appointment_create" class="form-horizontal" role="form"
							method="post" action="<%=LinkMap.STUDENT_CREATE_APPOINTMENT%>">
						
							<div class="form-group">
								<label class="col-sm-3 control-label" for ="Advisor">Advisor</label>
								<div class="col-sm-8">
									<input type="hidden" name="advisor_id" id="advisor_id" value="${advisor.getId()}">
									<input type="text" class="form-control" name="advisor" id="advisor" value="${advisor.getFirstName()} ${advisor.getLastName()}" disabled>
    							</div>
							</div>

							<!-- This should be dynamically pulled from the database -->
							<div class="form-group">
								<label class="col-sm-3 control-label" for ="Advsing Task">Advising Task</label>
								<div class="col-sm-8">
									<select id="advising_task" name="advising_task" class="form-control">
	      								<option value="">-------Select--------</option>
                                        <c:forEach items="${tasks}" var="task">
                                            <option value="${task.getId()}">${task.getName()}</option>
                                        </c:forEach>
	    							</select>
    							</div>
							</div>

							<div class="form-group has-feedback">
								<label class="col-xs-3 control-label">Date</label>
								<div class="col-xs-3">
									<input type="text" class="form-control datepicker" name="date" id="date" value="${date}" required>
									<span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
								</div>
							</div>
							
							<div class="form-group has-feedback">
								<label class="col-sm-2 col-sm-offset-1 control-label">Start</label>
								<div class="col-sm-3">
									<input type="text" class="form-control datepicker" name="start_time" id="start_time" value="${start_time}" required>
									<span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>
								</div>
								
								<label class="col-sm-2 control-label">End</label>
								<div class="col-sm-3">
									<input type="text" class="form-control datepicker" name="end_time" id="end_time" required>
									<span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Notes</label>
								<div class="col-sm-8">
									<textarea class="form-control" name="student_notes" id="student_notes"></textarea>
								</div>
							</div> 
 
							<button type="submit" class="btn btn-Primary col-md-offset-5">Create Appointment</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END CONTENT AREA -->
	<script type="text/javascript">
		$(document).ready(function() {
			$("#date").datetimepicker({
				format: 'MM/DD/YYYY',
				keepOpen: true
			});
			$("#start_time").datetimepicker({
				format: 'LT',
				stepping: 15
			});
			$("#end_time").datetimepicker({
				format: 'LT',
				stepping: 15
			});
		});
	</script>
	<jsp:include page="templates/footer.jsp" />
</body>
</html>