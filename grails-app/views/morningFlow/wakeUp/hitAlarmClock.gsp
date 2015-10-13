<%@ page contentType="text/html;charset=UTF-8" %>
<html>6666666666666
<head>
    <title></title>
</head>

<body>
<g:form>
    <g:submitButton name="submit" value="Hit Alarm Clock"/>
    ${counter}
    %{--This is the fix recommended by AdrienGuichard here https://github.com/grails/grails-core/pull/571 It does not work for links within subflows--}%
    <g:link action="${action}" _eventId="increment">Increment with _event param</g:link>
    %{--This works with <= Grails 2.4.4 but not with Grails >= 2.4.5. The execution param is missing.--}%
    <g:link action="${action}" event="increment">Increment with event param</g:link>
</g:form>
</body>
</html>