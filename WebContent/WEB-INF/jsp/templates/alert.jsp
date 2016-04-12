<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-md-10 col-md-offset-1">
		<c:choose>
			<c:when test="${error != null}">
				<div class="alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					${error}
				</div>
			</c:when>
			<c:when test="${success != null}">
				<div class="alert alert-success alert-dismissible">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					${success}
				</div>
			</c:when>
		</c:choose>
	</div>
</div>