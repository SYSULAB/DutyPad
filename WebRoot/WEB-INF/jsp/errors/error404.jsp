<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isErrorPage="true"%>
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
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<title>404 - 本子</title>
		<!--angular JS-->
		<script type="text/javascript" src="http://libs.useso.com/js/angular.js/1.2.9/angular.min.js"></script>
		<!--semantic css-->
		<link href="//cdn.bootcss.com/semantic-ui/2.1.8/semantic.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/reset.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/site.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/container.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/grid.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/header.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/image.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/menu.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/divider.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/list.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/segment.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/dropdown.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/icon.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/semantic-ui/2.1.8/components/transition.min.css">
		<link href="//cdn.bootcss.com/semantic-ui/2.1.8/components/sidebar.min.css" rel="stylesheet">
		<link href="//cdn.bootcss.com/semantic-ui/2.1.8/components/modal.min.css" rel="stylesheet">
		<!--My CSS-->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/sweetalert.css" />
		<!--sementic js-->
		<script src="http://libs.useso.com/js/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/semantic.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/site.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/checkbox.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/dropdown.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/transition.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/visibility.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/modal.min.js"></script>
		<script src="//cdn.bootcss.com/semantic-ui/2.1.8/components/sidebar.min.js"></script>
	

  </head>
  
  <body>
		<div class="ui middle aligned center aligned grid login-body">
			<div class="column">
				<div class="row">
					<div class="ui middle aligned medium image popup-hover" data-title="老司机：" data-content="在下熊本，想领教一下各位的车技" data-position="right center">
						<img src="<%=basePath%>img/motor.png" />
							
					</div>
				</div>
				<div class="row">
				<div class="ui middle aligned">
					<h4 class="ui header"></h4>
					<h2 class="ui header">Page not Found</h2>
					<h2 class="ui header">你访问的页面飙车去了</h2>
					<a class="ui button" href="./pad.html">
						<b>返回主页</b>
					</a>
				</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="<%=basePath%>js/script.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/controllers.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/style.js"></script>
		<script src="<%=basePath%>js/sweetalert.min.js" type="text/javascript" charset="utf-8"></script>
		
	</body>

	</html>
