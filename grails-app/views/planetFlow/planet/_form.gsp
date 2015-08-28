<%@ page import="org.universe.research.Planet" %>



<div class="fieldcontain ${hasErrors(bean: planetInstance, field: 'color', 'error')} required">
	<label for="color">
		<g:message code="planet.color.label" default="Color" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="color" from="${['Red', 'Blue', 'Green', 'Magenta']}" required="" value="${planetInstance?.color}" valueMessagePrefix="planet.color"/>
	<g:select name="planetSize" from="${['Big', 'Small']}" required="" value="${planetInstance?.planetSize}" valueMessagePrefix="planet.planetSize"/>

</div>

