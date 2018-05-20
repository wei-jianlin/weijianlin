<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
<script src="<c:url value="assets/js/jquery.js"/>"></script>
<script>
    deferred();
    function deferred(){
        $.get('deferr',function(data){
            console.log(data);
            deferred();
        });
    }
</script>
</html>
