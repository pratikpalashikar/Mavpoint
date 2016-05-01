<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<div class="container">
	<div class="row">
		<!-- <h1>Advisor Preferences</h1> -->
		<form id="admin_preferences" class="form-horizontal" role="form" method="post" action="<%=LinkMap.PREFERENCES%>">
			<div class="panel panel-primary">
				<div class="panel-heading">Email Notification</div>
				<div class="panel-body">
					<div class="form-group">
						<div class="col-xs-12">
							<c:choose>
								<c:when test="${user.hasEmailNotification()}">
									<input name="allowEmail" id="allowEmail" type="checkbox" checked> Allow Email Notifications
								</c:when>
								<c:otherwise>
									<input name="allowEmail" id="allowEmail" type="checkbox"> Allow Email Notifications
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>					
				<div class="panel-footer">
					<input type="hidden" name="id" value="${user.getId()}">
					<button type="submit" id="submit_btn" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</form>
	</div>
</div>