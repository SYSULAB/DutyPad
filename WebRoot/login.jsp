<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<title>登陆 - 本子</title>
		<!--angular JS-->
		<script type="text/javascript" src="http://libs.useso.com/js/angular.js/1.2.9/angular.min.js"></script>
		<!--semantic css-->
		<link href="//cdn.bootcss.com/semantic-ui/2.1.8/semantic.min.css" rel="stylesheet" />
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/sweetalert.css" />
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/reset.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/site.min.css">

		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/container.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/grid.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/header.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/image.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/menu.min.css">

		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/divider.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/segment.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/form.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/input.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/button.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/list.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/message.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/icon.min.css">
		
		<!--sementic js-->
		<script src="http://libs.useso.com/js/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/semantic.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/form.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/transition.min.js"></script>
		<!-- custom JS-->
		<script src="<%=basePath%>js/script.js" type="text/javascript" charset="utf-8"></script>
		<!--My CSS-->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />

  </head>
  
 <body class="login-body">
		<div class="ui middle aligned center aligned grid">
			<div class="column login-col">
				<h2 class="ui teal image header">
					<img src="img/kumamon.png" class="image"/>
					<div class="content">
						登录进本子
					</div>
				</h2>
				<form class="ui large form">
					<div class="ui stacked segment">
						<div class="field">
							<div class="ui left icon input">
								<i class="user icon"></i>
								<input type="text" name="username" placeholder="学号">
							</div>
						</div>
						<div class="field">
							<div class="ui left icon input">
								<i class="lock icon"></i>
								<input type="password" name="password" placeholder="密码">
							</div>
						</div>
						<div class="ui fluid large teal submit button" id="login-btn" ><i class="sign in icon"></i> 登陆</div>
					</div>

					<div class="ui error message"></div>

				</form>

				<div class="ui message">
					每日格言：好吃又好玩。
				</div>
			</div>
		</div>
		
		<script src="<%=basePath%>js/sweetalert.min.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>
