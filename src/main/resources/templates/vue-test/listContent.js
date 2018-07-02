var contextPath = "http://localhost:8082";

Vue.http.options.root = 'http://localhost:8082/';
var contents = new Vue({
    el: "#contentBody",
    data: {
        contentLists: {},
        tags: {},
        keyWord: '',
        keyDate: '',
        keyTag: ''

    },
    created: function () {
        this.$http.get('http://localhost:8082/content/getIndexContentData').then(response => {
            this.contentLists = response.body.contents;
            this.tags = response.body.tags;
            console.log("contentLists:" + response.body.contents);
        }, response => {
            console.log("get fail!")


        })
    },
    methods: {
        doSearch() {
            //日期正则
            var patter1 = new RegExp("^[2][0]\\d{2}\\-\\d{2}\\-\\d{2}", "g");
            //非空字符正则
            var patter2 = new RegExp("\\S", "g");
            //把tag的id转换成name传入
            if (this.keyTag !== '') {
                for (var i in this.tags) {
                    if (this.tags[i].id === this.keyTag) {
                        this.keyTag = this.tags[i].name;
                        break;
                    }
                }
            }
            this.$http.get('searchJson', {
                params: {
                    'keyWord': this.keyWord,
                    'keyDate': this.keyDate,
                    'keyTag': this.keyTag
                }
            }, {emulateJSON: true}).then(response => {
                this.contentLists = response.body.contents;
            });

        }
    }
});
