<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:form>
    <g:submitButton name="submit" value="Hit Alarm Clock"/>
    ${counter}
    <g:link action="${action}" event="increment">Increment</g:link>
</g:form>
</body>
</html>