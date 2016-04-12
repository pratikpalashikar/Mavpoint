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
			<div class="col-xs-12 col-sm-4 col-sm-offset-4">
				<div class="panel panel-primary">
					<form name="time_slot_create" id="time_slot_create" class="form-horizontal" role="form" method="post" action="<%=LinkMap.ADVISOR_EDIT_TIME_SLOT%>">

					<div class="panel-heading">Edit Time Slot</div>
					<div class="panel-body">
						<div class="form-group has-feedback">
							<label class="col-xs-6 control-label">Date</label>
							<div class="col-xs-6">
								<input type="text" class="form-control datepicker" name="date" id="date" value="${date}" required>
								<span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
							</div>
						</div>
													
						<div class="form-group has-feedback">
							<label class="col-xs-6 control-label">Start Time:</label>
							<div class="col-xs-6">
								<input type="text" class="form-control datepicker"  name=start_time id="start_time" value="${startTime}" required>
								<span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>
							</div>
						</div>
						
						<div class="form-group has-feedback">
							<label class="col-xs-6 control-label">End Time:</label>
							<div class="col-xs-6">
								<input type="text" class="form-control datepicker" name=end_time id="end_time" value="${endTime}" required>
								<span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>
							</div>
						</div>
						
						<div class="col-xs-12">
							<input type="hidden" name="id" id="id" value="${id}">
							<button type="button" id="delete_btn" class="btn btn-danger">Delete</button>
							<button type="submit" class="btn btn-success pull-right">Update</button>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- END CONTENT AREA -->
	<jsp:include page="templates/footer.jsp" />
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
			
			$("#delete_btn").click(function(){
				alert("Delete functionality coming soon!");
			});
		});
	</script>
</body>
</html>
