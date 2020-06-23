<!--userCenter-->
$(function(){
    var data4Vue = {
        loadUri: 'getPersonInfo',
        changeUri:'changePersonInfo',
        user: {account: '', name: '', company: '', email: '', phone: ''}
    };
    //ViewModel
    var vue = new Vue({
        el: '#personCenter',
        data: data4Vue,
        mounted:function(){
            this.loadInfo();
        },
        methods: {
            loadInfo:function () {
                // alert("程序已加载");
                var url = this.loadUri;
                axios.get(url).then(function(response) {
                    vue.user = response.data.user;

                });
            },
            changeInfo:function () {

            }
        }
    });
})
