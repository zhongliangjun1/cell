<html>
<head></head>

<body>

<h1>Welcome cell !</h1>

<h2>查询</h2>

<form action="/read" method="post">

    <p>shopId: <input type="text" name="shopId" /></p>
    <input type="submit" value="Submit" />

</form>

<h2>修改</h2>

<form action="/update" method="post">

    <p>shopId: <input type="text" name="shopId" /></p>
    <p>type:
        <select  name="type">
            <#list list as ty>
                <option value="${ty}">${ty}</option>
            </#list>
        </select>
    </p>
    <input type="submit" value="Submit" />

</form>


</body>

</html>