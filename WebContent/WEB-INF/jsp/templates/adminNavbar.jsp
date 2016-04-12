<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import= "edu.uta.cse.group9.util.LinkMap" %>
<div class="container">

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div id="inversenavbar" class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="<%=LinkMap.HOME%>">
					<b>MavAdvisor</b>
				</a>
			</div>
			
			<div>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<%=LinkMap.ADMIN_CREATE_ADVISOR%>">Create Advisor</a></li>
					<jsp:include page="userNavbar.jsp" />
				</ul>
			</div>
		</div>
	</nav>
</div>