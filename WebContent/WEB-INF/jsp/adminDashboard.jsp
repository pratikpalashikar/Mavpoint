<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<div class="container">
	<div class="row">
		<h1>Admin Dashboard</h1>
		<h5>Advisors</h5>
		<div class="panel panel-default">
			<table class="table blacktable">
				<thead>
					<tr>
						<th>First</th>
						<th>Last</th>
						<th>Email</th>
						<th>Status</th>
						<th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${advisors}" var="a">
					<tr>
						<td>${a.getFirstName()}</td>
						<td>${a.getLastName()}</td>
						<td>${a.getEmail()}</td>
						<td>${a.getStatus().description()}</td>
						<td><span id="adv-btn-${a.getId()}" class="delete glyphicon glyphicon-remove" aria-hidden="true"></span></td>
					</tr>
					</c:forEach>		
				</tbody>			
			</table> 
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".delete").click(function() {
				if (confirm("Really remove this advisor? Any upcoming appointments will be canceled.")) {
					var url = '<%=LinkMap.ADMIN_DELETE_ADVISOR%>';
					var params = {
						adv_id: this.id.replace("adv-btn-","")
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
				}
			});			
		});
	</script>
</div>