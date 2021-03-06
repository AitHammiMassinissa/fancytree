<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
  <title>Fancytree - Editable Nodes</title>

  <script src="https://code.jquery.com/jquery-1.12.0.js" type="text/javascript"></script>
  <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js" type="text/javascript"></script>

  <link href="../fancytree/fancytree/src/skin-win7/ui.fancytree.css" rel="stylesheet" type="text/css">
  <script src="../fancytree/fancytree/src/jquery.fancytree.js" type="text/javascript"></script>
  <script src="../fancytree/fancytree/src/jquery.fancytree.edit.js" type="text/javascript"></script>

  <!-- (Irrelevant source removed.) -->
  
	<script src=
"https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.5/FileSaver.min.js">
    </script>

<style type="text/css">
  span.pending span.fancytree-title {
    font-style: italic;
  }
  span.pending span.fancytree-title:after {
    content: "\2026"; /* ellipsis */
  }
</style>
<script type="text/javascript">

$(function(){
  
    var Source_1=[{
        "title":"Lazy Challenge",
        "lazy":true,
        "refkey":"ln1",
        "folder":true,
        "expanded":true,
    }];       
  $("#tree1").fancytree({
    extensions: ["edit"],
    source:Source_1,
    lazyLoad: function(event, data) {
    },
    edit: {
      triggerStart: ["f2", "dblclick", "shift+click", "mac+enter"],
      beforeEdit: function(event, data){
        // Return false to prevent edit mode
      },
      edit: function(event, data){
        // Editor was opened (available as data.input)
      },
      beforeClose: function(event, data){
        // Return false to prevent cancel/save (data.input is available)
      },
      save: function(event, data){
        // Save  or return false to keep editor open
        console.log("save...", this, data);
        // Simulate to start a slow ajax request...
        setTimeout(function(){
          $(data.node.span).removeClass("pending");
          // Let's pretend the server returned a slightly modified
          // title:
          data.node.setTitle(data.node.title + "!");
        }, 2000);
        // We return true, so ext-edit will set the current user input
        // as title
        return true;
      },
      close: function(event, data){
        // Editor was removed
        if( data.save ) {
          // Since we started an async request, mark the node as preliminary
          $(data.node.span).addClass("pending");
        }
      }
    }
  });
  $('#AvoirFancytre').on('click', function() {
  var tree = $("#tree1").fancytree("getTree");
  var d = tree.toDict(true);
  alert(JSON.stringify(d));
  });
  $('#AddChild').on('click', function() {
	var node = $("#tree1").fancytree("getActiveNode");
      if( !node ) {
        alert("Please activate a parent node.");
        return;
      }
	  var i=0;
      node.editCreateNode("child", node.title+"."+node.key);
  });
  $('#AddSiblingFolder').on('click', function() {
	var node = $("#tree1").fancytree("getActiveNode");
      node.editCreateNode("after", {
        title: "Node title",
        folder: true
      });
  });
});
</script>

<!-- (Irrelevant source removed.) -->

</head>

<body class="example">
  <h1>Example: 'edit' extension</h1>
  <div class="description">
    <p>
      Allow to change node titles using inline editing.
    </p>
    <p>
      Edit the node titles with `dblclick`, `Shift + click` [F2], or [Enter] (on Mac only).
    </p>
    <p>
      <b>Status:</b> beta.
      <b>Details:</b>
      <a href="https://github.com/mar10/fancytree/wiki/ExtEdit"
        target="_blank" class="external">ext-edit</a>.
    </p>

  </div>

  <div id="tree1">
  </div>
  <div id="tree2">
  </div>
  <div id="tree3">
  </div>
  <div id="tree4">
  </div>
  <div id="tree5">
  </div>
  <div id="tree6">
  </div>
  <div id="tree7">
  </div>
  <div id="tree8">
  </div>


  <div>
	  <button id="AddChild">Fichier fils +</button>
	  <button id="AddSiblingFolder">Folder +</button>
      <button id="AvoirFancytre">Fancytree jason</button>
     
  </div>
</body>
</html>


<?php
/*echo ("<html>");
echo ("<body>");

echo "<h1>Welcome to my home page!</h1>";
echo "<p>Some text.</p>";
echo "<p>Some more text.</p>";


echo "</body>";
echo "</html>";*/


require "DB.inc.php";


	$db = DB::getInstance();
		if ($db == null)
			echo "Impossible de se connecter ?? la base de donn??es !";
		else
			echo 'Success';
			
	$ip =  "".get_client_ip();
	$tab = $db->getClients();
	$cpt = count($tab);
	$cpt = $cpt+1;
	foreach($tab as $key)
    {
            $tmp = "".$key->getClient_ip_Address();
			//print_r($tmp);
			//echo($tmp);
            if($tmp==$ip)
            {
                    $check = true;
            }
    }
    if($check==false)
    {          
            $db->insert($cpt, $ip, null, null, null, null, null);
    }
	// Function to get the client IP address

	function get_client_ip() {
		$ipaddress = '';
		$ip_add = $_SERVER['REMOTE_ADDR'];
		echo "The user's IP address is - ".$ip_add;
		return $ip_add;
	}
	 
	 
	//echo "Your IP address is: " . get_client_ip();

?>
