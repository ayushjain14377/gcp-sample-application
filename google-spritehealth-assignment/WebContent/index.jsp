<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
	background-color: black;
}

* {
	box-sizing: border-box;
}

.container {
	padding: 16px;
	background-color: white;
}

input[type=text], input[type=password] {
	width: 100%;
	padding: 15px;
	margin: 5px 0 22px 0;
	display: inline-block;
	border: none;
	background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
	background-color: #ddd;
	outline: none;
}

hr {
	border: 1px solid #f1f1f1;
	margin-bottom: 25px;
}

.login {
	background-color: #4CAF50;
	color: white;
	padding: 16px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
	opacity: 0.9;
}

.login:hover {
	opacity: 1;
}

a {
	color: dodgerblue;
}

.signup {
	background-color: #f1f1f1;
	text-align: center;
}
</style>
<title>Google Cloud Platform Application Login</title>
</head>
<body>

	<form action="/LoginService" method="post">
		<div class="container">
			<h1>Login</h1>
			<p>Please provide correct details to log into your Account</p>
			<hr>

			<label for="username"><b>Username</b></label> <input type="text"
				placeholder="Enter Username" name="username" required> <label
				for="psw"><b>Password</b></label> <input type="password"
				placeholder="Enter Password" name="psw" required>

			<button type="submit" class="login">Login</button>
		</div>

		<div class="container signin">
			<p>
				Register multiple users using Excel Spreadsheet? <a
					href="ExcelToDatastore.jsp">Continue</a>.
			</p>
		</div>
	</form>

</body>
</html>