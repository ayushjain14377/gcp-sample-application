<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Excel file with User Details here:</title>
<script type="text/javascript">
function checkfile(sender) {
    var validExts = new Array(".xlsx");
    var fileExt = sender.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("Invalid file selected, valid files are of " +
               validExts.toString() + " types.");
      return false;
    }
    else return true;
}
</script>
</head>
<body>

	<form action="/ExcelToDatastore" method="post"
		enctype="multipart/form-data" >
		<b>Select .xlsx Excel file to Upload :</b> <input type="file"
			name="filetoupload" onchange="checkfile(this);"> <br /> <input type="submit"
			value="Upload File">
	</form>

	<p>
		Strictly follow the format given in given .xlsx file. <a
			href="https://storage.googleapis.com/spritehealth-gcp-application.appspot.com/SampleContent/Sample.xlsx">Download Sample .xlsx</a>.
	</p>
</body>
</html>