<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SSE Demo</title>
</head>
<body>

<div id="messageFormPush"></div>
</body>
<script src="<c:url value="assets/js/jquery.js"/>"></script>
<script>
    if (EventSource) {
        var source = new EventSource('push');
        var s = '';
        source.addEventListener("message", function (e) {
            s += e.data + '</br>';
            $('#messageFormPush').html(s);
        });
        source.addEventListener("open",function(e){
            console.log('连接打开');
        },false);
        source.addEventListener("error",function(e){
            if(e.readyState == EventSource.CLOSED){
                console.log('连接关闭');
            }else{
                console.log(e.readyState);
            }
        },false)
    } else {
        console.log('你的浏览器不支持SSE')
    }
</script>
</html>
