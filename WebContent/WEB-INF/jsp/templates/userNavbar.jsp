<%@ page import= "edu.uta.cse.group9.util.LinkMap" %>
<li class="dropdown">
	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
	aria-haspopup="true" aria-expanded="false">User <span class="caret"></span></a>
	<ul class="dropdown-menu">
		<li><a href="<%=LinkMap.LOG_OUT%>">Logout</a></li>
		<li><a href="<%=LinkMap.RESET_PASSWORD%>">Change Password</a></li>
		<li><a href="<%=LinkMap.PREFERENCES%>">Assign</a></li>
		<li role="separator" class="divider"></li>
		<li><a href="#">Feedback</a></li>
	</ul>
</li>

