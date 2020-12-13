### Vue的使用

在前端显示数据，使用js jquery方法：先获取数据，再获取DOM节点，最后显示数据

使用Vue方法：获取数据 再将数据与对应的视图相关联

格式：

<script>
    new Vue({
        el:'#...',
        data:{...}
    })
</script>



< ... v-if=“...”>  判断显示，true显示 false不显示

<... v-on:click/submit=“...”> 监听DOM事件

<... v-bind: ...>响应更新HTML DOM属性

<... v-model=“...”>实现数据双向绑定