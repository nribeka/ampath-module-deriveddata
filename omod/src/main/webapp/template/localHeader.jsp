<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first">
        <a href="${pageContext.request.contextPath}/admin">
            <spring:message code="admin.title.short" />
        </a>
    </li>
	<li <c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/deriveddata/manage.form">
            <spring:message code="deriveddata.manage" />
        </a>
	</li>
</ul>
<h2>
	<spring:message code="deriveddata.title" />
</h2>
