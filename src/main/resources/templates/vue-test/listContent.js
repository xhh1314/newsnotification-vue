var contextPath="http://localhost:8082";

var contentList='';

$(window).on('load',function () {
 $.ajax({
     type:'GET',
     dataType:'JSON',
     url:contextPath+'/listContent',
     success:function (data) {
         contentList=data;
     }
 });

});
var contents=new Vue({
    el:"#contents",
    data:{contentLists:contentList}
});
