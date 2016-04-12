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

	<!-- MODAL WINDOW -->
	<div class="modal fade" id="cancel_modal" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">Cancel Appointment</h4>
				</div>
				<div class="modal-body">
					<form name="appointment_show" id="appointment_show" class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-3 control-label" for ="reason">Reason</label>
							<div class="col-sm-9">
								<textarea name="reason" id="reason" class="form-control" style="resize:none"></textarea>
    						</div>
						</div>							
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Go Back</button>
					<button type="button" name="confirm_cancel_btn" id="confirm_cancel_btn" class="btn btn-warning">Cancel Appointment</button>
				</div>
			</div>
		</div>
	</div>

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
					<div class="panel-heading">Appointment</div>
					<div class="panel-body">
						<form name="appointment_show" id="appointment_show" class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-sm-3 control-label" for ="Advisor">Advisor</label>
								<div class="col-sm-9">
									<p class="form-control-static">${appt.getAdvisor().getFirstName()} ${appt.getAdvisor().getLastName()}</p>
    							</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" for ="Student">Student</label>
								<div class="col-sm-9">
									<p class="form-control-static">${appt.getStudent().getFirstName()} ${appt.getStudent().getLastName()}</p>
    							</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" for ="Advsing Task">Advising Task</label>
								<div class="col-sm-9">
									<p class="form-control-static">${appt.getTask().getName()}</p>
    							</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Start Date/Time</label>
								<div class="col-sm-9">
									<p class="form-control-static">${appt.getStartTime()}</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">End Date/Time</label>
								<div class="col-sm-9">
									<p class="form-control-static">${appt.getEndTime()}</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">Advisor Notes</label>
								<div class="col-sm-9">
									<p class="form-control-static">${appt.getAdvisorNotes()}</p>
								</div>
							</div> 
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Student Notes</label>
								<div class="col-sm-9">
									<p class="form-control-static">${appt.getStudentNotes()}</p>
								</div>
							</div> 							
						</form>
					</div>
					
					<div class="panel-footer">
						<button type="button" class="btn btn-info" name="edit_btn" id="edit_btn">Edit</button>
						<c:if test="${appt.getStudentNotes().equals('')}">
      <button type="button" class="btn btn-danger" name="cancel_btn" id="cancel_btn">Cancel</button> 
     </c:if>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END CONTENT AREA -->
	<script type="text/javascript">
		$(document).ready(function() {
			$("#cancel_btn").click(function() {
				$("#cancel_modal").modal();
			});
			
			$("#edit_btn").click(function() {
				alert("Edit functionality coming soon!");
			});

			$("#confirm_cancel_btn").click(function() {
				var url = '<%=LinkMap.APPOINTMENT_CANCEL%>';
				var params = {
					appt_id: '${appt.getId()}',
					reason: $("#reason").val()
				};
				$.post(url, params, function(result){
					var forwardUrl = '<%=LinkMap.HOME%>';
					var json = JSON.parse(JSON.stringify(result));
					var success = result.success;
					var error = result.error;
					if (success) {
						forwardUrl += "?success=" + success;
					} else if (error) {
						forwardUrl += "?error=" + error;
					}
					window.location.assign(forwardUrl);	
				});				
			});
		});
	</script>
	<jsp:include page="templates/footer.jsp" />
</body>
</html>