$(function(){
  addCode('#apiJS', '/src/api/example.js', 'javascript')
})

function addCode(id, url, language){
  $.get(url).done(function(data){
    var code = $(id).append('<pre><code class="'+language+'">'+data+'</code></pre>')
    hljs.highlightBlock(code.find('code')[0])
  })
}
