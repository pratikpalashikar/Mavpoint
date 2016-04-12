<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<%@ page import="edu.uta.cse.group9.model.User"%>
<head>
	<title>MavAdvisor</title>
	<jsp:include page="templates/header.jsp" />
	
	<%-- FullCalendar includes --%>
	<script type='text/javascript' src='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.5.0/fullcalendar.min.js'></script>
	<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.5.0/fullcalendar.min.css'>
	<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.5.0/fullcalendar.print.css' media='print'> 		
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
<!-- CONTENT BEGINS HERE -->
	<div class="container">
		<jsp:include page="templates/alert.jsp" />
		<div id="calendar"></div>
	</div>
<!-- CONTENT ENDS HERE -->
	<jsp:include page="templates/footer.jsp" />
</body>
<c:choose>
	<c:when test='${user.getClass().getSimpleName().equals("Advisor")}'>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#calendar').fullCalendar({
					weekends: false,
					timezone: 'local',
					defaultView: 'agendaWeek',
					header: {
						left: 'prev,today,next',
						center: 'title',
						right: 'month,agendaWeek,agendaDay'
					},
					businessHours: {
						start: '07:00',
						end: '19:00'
					},
					eventLimit: true,
					height: 550,
					eventSources: [
						{
							url: '<%=LinkMap.ASYNC_TIME_SLOTS%>',
							type: 'POST',
							data: {
								advisor_id: '${user.id}'
							},
							error: function(){
								console.log('Error retrieving time slots!');
							}
						},
						{
							url: '<%= LinkMap.ASYNC_APPOINTMENTS %>',
							type: 'POST',
							data: {
								user_id: '${user.id}'
							},
							error: function(){
								console.log("Error retrieving appointments.");
							}
						}
					],
					selectable: true,
					select: function(startDate, endDate, jsEvent, view) {
						window.location.assign('<%=LinkMap.ADVISOR_CREATE_TIME_SLOT%>?starttime=' + startDate);
					},
					eventClick: function(event, jsEvent, view) {
						var sourceUrl = event.source.url;
						if (sourceUrl === "<%= LinkMap.ASYNC_APPOINTMENTS %>") {
							window.location.assign('<%=LinkMap.APPOINTMENT_DETAIL%>?appt_id=' + event.id);
						} else if (sourceUrl == "<%= LinkMap.ASYNC_TIME_SLOTS %>") {
							window.location.assign('<%=LinkMap.ADVISOR_EDIT_TIME_SLOT%>?id=' + event.id);	
						}
					}
				})
			});		
		</script>
	</c:when>
	<c:when test='${user.getClass().getSimpleName().equals("Student")}'>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#calendar').fullCalendar({
					weekends: false,
					timezone: 'local',
					defaultView: 'agendaWeek',
					header: {
						left: 'prev,today,next',
						center: 'title',
						right: 'month,agendaWeek,agendaDay'
					},
					businessHours: {
						start: '07:00',
						end: '19:00'
					},
					eventLimit: true,
					height: 550,
					eventSources: [
						{
							url: '<%=LinkMap.ASYNC_TIME_SLOTS%>',
							type: 'POST',
							data: {
								student_id: '${user.id}'
							},
							error: function(){
								console.log('Error retrieving time slots!');
							}
						},
						{
							url: '<%= LinkMap.ASYNC_APPOINTMENTS %>',
							type: 'POST',
							data: {
								user_id: '${user.id}'
							},
							error: function(){
								console.log("Error retrieving appointments.");
							}
						}
					],
					selectable: true,
					eventClick: function(event, jsEvent, view) {
						var sourceUrl = event.source.url;
						if (sourceUrl === "<%= LinkMap.ASYNC_APPOINTMENTS %>") {
							if (event.color === 'green') {
								window.location.assign('<%=LinkMap.APPOINTMENT_DETAIL%>?appt_id=' + event.id);	
							}
						} else if (sourceUrl == "<%= LinkMap.ASYNC_TIME_SLOTS %>") {
							window.location.assign('<%=LinkMap.STUDENT_CREATE_APPOINTMENT%>?starttime=' + event.start + '&timeslot_id=' + event.id);	
						}
					}
				})
			});		
		</script>
	</c:when>
</c:choose>
</html>