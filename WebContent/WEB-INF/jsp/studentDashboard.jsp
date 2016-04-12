<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import= "edu.uta.cse.group9.model.Student" %>
<%@ page import= "edu.uta.cse.group9.util.LinkMap" %>
<div class="container">
	<div class="row">
		<h1>${user.getFirstName()}'s Dashboard</h1>
		<div class="col-xs-12 col-md-8 col-md-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading">Basic Info</div>
				<div class="panel-body">
					<div class="col-xs-6 col-md-4">
						Name: ${user.getLastName()}, ${user.getFirstName()}
					</div>
					<div class="col-xs-6 col-md-4">
						UTA ID: ${user.getUtaId()}
					</div>
					<div class="col-xs-6 col-md-4">
						Email: ${user.getEmail()}
					</div>
					<div class="col-xs-6 col-md-4">
						Type: ${user.getStudentType().description()}
					</div>
					<div class="col-xs-6 col-md-4">
						Status: ${user.getEnrollmentStatus().description()}
					</div>					
				</div>
			</div>
		</div>
	</div>
	<h3>Upcoming Appointments</h3>
	<div class="row">
		<div class="col-xs-12">		
			<div class="panel panel-default">
				<table class="table blacktable">
					<thead>
						<tr>
							<th>ID</th>
							<th>Advisor</th>
							<th>Reason</th>
							<th>Start</th>
							<th>End</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${appointments}" var="entry">
						<tr>
							<td><a href="<%=LinkMap.APPOINTMENT_DETAIL%>?appt_id=${entry.getId()}">Details</a></td>
							<td>${entry.getAdvisor().getLastName()}, ${entry.getAdvisor().getFirstName()}</td>
							<td>${entry.getTask().getName()}</td>
							<td>${entry.getStartTime().toString()}</td>
							<td>${entry.getEndTime().toString()}</td>
							<td>${entry.getStatus().description()}</td>
						</tr>
						</c:forEach>		
					</tbody>			
				</table> 
			</div>
		</div>
	</div>
</div>