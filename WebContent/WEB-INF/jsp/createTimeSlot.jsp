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
					<div class="panel-heading">Create Time Slot</div>
					<div class="panel-body">
						<form name="time_slot_create" id="time_slot_create" class="form-horizontal"
							role="form" method="post" action="<%=LinkMap.ADVISOR_CREATE_TIME_SLOT%>">

							<div class="form-group has-feedback">
								<label class="col-xs-3 control-label">Start Date</label>
								<div class="col-xs-3">
									<input type="text" class="form-control datepicker" name="start_date" id="start_date" value="${startDate}" required>
									<span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
								</div>
							</div>
							
							<div class="form-group has-feedback">
								<label class="col-xs-2 col-xs-offset-1 control-label">Start Time:</label>
								<div class="col-xs-3">
									<input type="text" class="form-control datepicker" name=start_time id="start_time" value="${startTime}" required>
									<span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>										
								</div>

								<label class="col-xs-2 control-label">End Time:</label>
								<div class="col-xs-3">
									<input type="text" class="form-control datepicker" name=end_time id="end_time" required>
									<span class="glyphicon glyphicon-time form-control-feedback" aria-hidden="true"></span>
								</div>
							</div>

							<hr/>
						
							<h4>Recurrence (optional)</h4>

							<div class="form-group has-feedback">
								<label class="col-xs-3 control-label">End Date</label>
								<div class="col-xs-3">
									<input type="text" class="form-control datepicker" name="end_date" id="end_date">
									<span class="glyphicon glyphicon-calendar form-control-feedback" aria-hidden="true"></span>
								</div>
							</div>
							
							<div class="form-group" >
								<label class="col-xs-3 control-label">Days</label>
								<div class="col-xs-9">
									<span class="col-xs-2">
										<input type="checkbox" name="mon" id="mon"> M
									</span>
									<span class="col-xs-2">
										<input type="checkbox" name="tue" id="tue"> Tu
									</span>
									<span class="col-xs-2">
										<input type="checkbox" name="wed" id="wed"> W
									</span>
									<span class="col-xs-2">
										<input type="checkbox" name="thu" id="thu"> Th
									</span>
									<span class="col-xs-2">
										 <input type="checkbox" name="fri" id="fri"> F
									</span>
								</div>				
							</div>
							<button type="submit" class="btn btn-Primary col-xs-offset-5">Create Time Slot</button>			
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END CONTENT AREA -->
	<jsp:include page="templates/footer.jsp" />
	<script type="text/javascript">
		$(document).ready(function() {
			$("#start_date").datetimepicker({
				format: 'MM/DD/YYYY',
				keepOpen: true
			});
			$("#end_date").datetimepicker({
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
</body>
</html>
