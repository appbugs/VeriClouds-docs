$(function(){
  addCode('#apiJS', '/src/api/example.js', 'javascript')
  addCode('#apiJava', '/src/api/example.java', 'java')
  addCode('#apiCsharp', '/src/api/example.cs', 'cs')
  addCode('#apiPhp', '/src/api/example.php', 'php')
  addCode('#apiPython', '/src/api/example.py', 'python')

  addCode('#nistJS', '/src/nist/example.js', 'javascript')
  addCode('#nistJava', '/src/nist/example.java', 'java')
  // addCode('#nistCsharp', '/src/nist/example.cs', 'cs')
  addCode('#nistPhp', '/src/nist/example.php', 'php')
  addCode('#nistPython', '/src/nist/example.py', 'python')
})

function addCode(id, url, language){
  $.ajax({
    url: url,
    dataType: "text"
  }).done(function(data){
    var code = $(id).append('<pre><code class="'+language+'">'+data+'</code></pre>')
    hljs.highlightBlock(code.find('code')[0])
  })
}
