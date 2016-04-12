<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<div class="container">
	<div class="row">
		<h1>Admin Preferences</h1>
		<form id="admin_preferences" class="form-horizontal" role="form" method="post" action="<%=LinkMap.PREFERENCES%>">
			<div class="panel panel-primary">
				<div class="panel-heading">Advisor Assignments</div>
				<div class="panel-body">
					<div class="form-group">
						<div class="col-xs-12">
							<input name="allowAny" id="allowAny" type="checkbox"> Allow any advisors?
						</div>
					</div>					
					<div class="form-group" id="assignmentTable">
						<div class="table-responsive">
							<table class="table in-panel">
								<thead>
									<tr>
										<th></th>
										<th>Name</th>
										<th>Assigned</th>
										<th>Start</th>
										<th>End</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="count" scope="page" value="${1}" />
									<c:forEach items="${advisors}" var="a">
									<tr class="assignment">
										<td><input name="id${count}" id="id${count}" type="hidden" value="${a.getId()}"></td>
										<td>${a.getFirstName()} ${a.getLastName()}</td>
										<c:choose>
											<c:when test="${a.isAssignedStudentRange()}">
												<td><input name="assigned${count}" id="assigned${count}" type="checkbox" checked></td>	
											</c:when>
											<c:otherwise>
												<td><input name="assigned${count}" id="assigned${count}" type="checkbox" ></td>
											</c:otherwise>											
										</c:choose>
										<td><input name="startVal${count}" id="startVal${count}" type="text" value="${a.getStartStudent()}"></td>
										<td><input name="endVal${count}" id="endVal${count}" type="text" value="${a.getEndStudent()}"></td>
									</tr>
									<c:set var="count" value="${count + 1}" />
									</c:forEach>		
								</tbody>			
							</table> 
						</div>
					</div>
				</div>
				<div class="panel-footer">
					<input type="hidden" name="numAdv" value="${advisors.size()}">
					<button type="submit" id="submit_btn" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#allowAny").click(function() {
				if($(this).is(":checked")) {
					$("#assignmentTable").hide();
				} else {
					$("#assignmentTable").show();
				}
			});
		});
	</script>
</div>