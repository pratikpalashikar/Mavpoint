<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import= "edu.uta.cse.group9.util.LinkMap" %>
<div class="container">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div id="inversenavbar" class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="<%=LinkMap.HOME%>">
					MavAdvisor
				</a>
			</div>
			
			<div>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<%=LinkMap.STUDENT_CREATE_ACCOUNT%>">Register Student</a></li>
					<form class="navbar-form navbar-right" role="form" method="post" action="<%=LinkMap.LOG_IN%>">
						<div class="form-group" style="margin-right: 20px;">
							<div class="input-group">
								<span class="input-group-addon">
									<i class="glyphicon glyphicon-user"></i>
								</span>
								<label class="sr-only" for="username">Username: </label>
								<input type="test" class="form-control" id="username" name="username" placeholder="Username">
							</div>
						</div>
						<div class="form-group" style="margin-right:20px;">
							<div class="input-group">
								<span class="input-group-addon">
									<i class="glyphicon glyphicon-lock"></i>
								</span>
								<label class="sr-only" for="password">Password: </label>
								<input type="password" class="form-control" id="password" name="password" placeholder="Password">							
							</div>
						</div>
						<div class="form-group" style="margin-right: 20px;">
							<button type="submit" class="btn btn-primary">Login</button>
						</div>
					</form>
					
				</ul>
			</div>
		</div>
	</nav>
</div>