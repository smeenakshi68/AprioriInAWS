<%if (session.isNew() == false )
{
    response.sendRedirect("Expired.jsp");
}
%> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Elastic bean server application to generate asssociation rules</title>
<script type="text/javascript">
function validateForm()
{
var a=document.forms["frm"]["support"].value;
var b=document.forms["frm"]["confidence"].value;
var c=document.forms["frm"]["fileselect[]"].value;
alert(c);
if (a==null || a==""||b==null || b==""||c==null || c=="")
  {
  alert("Please Fill All Required Field");
  return false;
  }
  return true;
}

</script>
</head>
<body>
<form id="uploadServer" name="frm" action="UploadtoServer" onsubmit="return validateForm()" method="POST" enctype="multipart/form-data">

<fieldset>
<legend>Association Rules</legend><br></br>
<hr>

<input type="hidden" id="MAX_FILE_SIZE" name="MAX_FILE_SIZE" value="900000" />
 Min Support Value:    <input type="text"  name="support" /><p><font color ="red" size="1">value should be like- eg. 0.23</font></p><br></br>
 Min Confidence Value: <input type="text"  name="confidence"  /><p><font color ="red" size="1">value should be like- eg. 0.56</font></p> <br></br>

<div>
	<label for="fileselect">Upload Transactions file:</label>
	<input type="file" id="fileselect" name="fileselect[]"  /><font color ="red" size="2">choose Only<b> text file</b>  to upload data</font><p><font color ="red" size="1">max file size tested- 52.3KB</font></p>
	
</div>

<div >
	<button type="submit">Generate Association Rules</button>
</div>

</fieldset>

</form>

<div id="messages">
<p>Status Messages</p>
</div>
<script src="filedrag.js"></script>
</body>
</html>
