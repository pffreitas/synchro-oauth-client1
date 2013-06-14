<html>
<head>
<meta name="layout" content="main">

<style type="text/css">
#login-w-synchro {
	border: 1px solid #C0C0C0;
	border-radius: 3px;
	margin: 4px;
	padding: 4px 10px 4px 48px;
	background-image: url("../images/brasil.png");
	background-size: 50px;
	background-repeat: no-repeat;
	box-shadow: 1px 2px 2px 0px rgba(0, 0, 0, 0.2);
	background-position: -6px -5px;
	cursor: pointer;
	color: #2875ce;
	font-weight: 600;
	text-shadow: 0 2px 2px rgba(0, 0, 0, 0.2);
	text-transform: uppercase;
	font-size: 12px;
}

#login-w-synchro:hover {
	box-shadow: 1px 2px 2px 0px rgba(0, 0, 0, 0.2) inset;
}

#container {
	margin: 20px;
}
</style>

</head>

<body>
	<div id="container">
		<span id="login-w-synchro"
			onclick="javascript: window.location = '${createLink(controller:"loginWSycnhroID", action: "auth")}';">
			Synchro Connect</span>
	</div>
</body>
</html>