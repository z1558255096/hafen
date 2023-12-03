// $(function(){
    //设置jQuery Ajax全局的参数,捕捉csrf校验异常错误
$.ajaxSetup({
    beforeSend: function(request) {
        request.setRequestHeader('Authentication', localStorage.getItem("token")); // 增加一个自定义请求头
    },
    aysnc: false , // 默认同步加载
    error: function(jqXHR, textStatus, errorThrown){
        if(jqXHR.status == 401){
            // alert('未认证，请在前端系统进行认证');
            location.href='/login.html';
        }
    }
});
// });
