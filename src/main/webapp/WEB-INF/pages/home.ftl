<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>

<body>

<h1>Welcome cell !</h1>
<p>当前 redis 记录总数 : ${count}</p>

<h2>查询</h2>


<p>shopId: <input class="shopid_read" type="text" name="shopId" /></p>
<p class="read_result Hide"></p>
<input type="submit" class="J_read" />


<h2>修改</h2>

<p>shopId: <input class="shopid_up" type="text" name="shopId" /></p>
<p>type:
    <select class="type_value" name="type">
    <#list list as ty>
        <option value="${ty}">${ty}</option>
    </#list>
    </select>
</p>
<p class="update_result Hide"></p>
<input type="submit" class="J_up" />

<script src="/js/zepto.js"></script>
<script>
    var read = $('.J_read');
    read.on('click',function(e){
        $.ajax({
            type:'post',
            url:'/read',
            data:{ shopId:$('.shopid_read').val()},
            dataType: 'json',
            timeout: 2000,
            success:function(data){
                console.log(data);
                $('.read_result').removeClass('Hide');
                $('.read_result').html('result : '+data.msg);
            }
        });
    });

    var update = $('.J_up');
    update.on('click',function(e){
        $.ajax({
            type:'post',
            url:'/update',
            data:{ shopId:$('.shopid_up').val(),type:$('.type_value').val()},
            dataType: 'json',
            timeout: 2000,
            success:function(data){
                console.log(data);
                $('.update_result').html('result : '+data.msg);
            }
        });
    });

    $('.shopid_read').on('click',function(){
        $('.read_result').html('');
    });

    $('.shopid_up').on('click',function(){
        $('.update_result').html('');
    });
</script>

</body>

</html>