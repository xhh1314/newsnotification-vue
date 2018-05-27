<#include "/fore/header.ftl">
<!-- 正文容器 -->
	<div class="ui  very padded text container segment notification-content">
		<h3 class="ui header">xiaoxi</h3>
		<div class="ui divided items" id="contentList">
			<div class="item" v-for="content in contentDataList">
				<div class="content">
					<a class="header" href="/content/{{content.id}}" target="_blank">{{content.title}}</a>
					<div class="meta">
						<span class="cinema">{{content.createTime}}</span>
					</div>
					<div class="description">
						<p>{{content.content}}</p>
					</div>
					<div class="extra" id="content-tag" v-for="tag in content.tags">
						<div class="ui label">{{tag.name}}</div>

					</div>
				</div>
			</div>
		</div>

	</div>
</body>

<script type="text/javascript">
    var contentDataList;
    var contents;
    var tags;
    var keyDate;

    var app=new Vue({
        created:function () {
            $.ajax({
                url:'/content/getIndexContentData',
                type:'get',
                dataType:'json',
                success:function(data){
                    contentDataList=data;
                    contents=data.contents;
                    tags=data.tags;
                    keyDate=data.keyDate;
                },
                error:function (data) {
                    alert(data);
                }

            });

        }
    });

    var contentList=new Vue({
        el:'#contentList',
        data:{
            contentList:contents
        }
    });

    var tagsVue=new Vue({
        el:'#tags-vue',
        data:{
            tags:tags
        }
    });

    var inputNewsTime=new Vue({
        el:'#input-newsTime-vue',
        data:{
            keyData:keyDate
        }
    });

</script>

</html>