<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html ng-app="DutyPad">
  <head>
    <base href="<%=basePath%>">
    
    
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
	
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
		<title>管理 - 本子</title>
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
  
  <body style="display: none;">
		<div class="ui left demo vertical sidebar labeled icon menu inverted active">
			<a class="item" href="pad">
				<i class="book icon"></i> 本子
			</a>
			<a class="item" href="times">
				<i class="tasks layout icon"></i> 工时
			</a>
			<a class="item">
				<i class="settings icon green"></i> 管理
			</a>
			<a class="item" href="logout">
				<i class="sign out icon"></i> 注销
			</a>
		</div>

		<div ng-controller="ManageController" class="pad-ctrler pusher" id="container">

			<div class="showSidebar" id="SideBar">
				<div class="ui button animated fade white" tabindex="0">
					<div class="visible content"><i class="sidebar icon button"></i></div>
					<div class="hidden content">菜单</div>
				</div>
			</div>

			<div class="ui container piled segment" id="Main">
				<div class="ui top attached tabular menu">
					<span class="item"><i class="settings icon"></i></span>
					<a class="green item active" ng-click="changePad(0)">值班详情</a>
					<a class="green item" ng-click="changePad(1)">统计工时</a>
					<a class="green item" ng-click="changePad(2)">助理管理</a>
				</div>
				<div class="pad0" ng-show="nowPad == 0">
					<div class="ui column grid">
						<div class="row">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">本月值班详情 - {{nameList[nowWho]}}</h1>
								</div>
							</div>
							<div class="seven wide column"></div>
							<div class="four wide column fluid">
								<select id="selectUser1"class="ui search dropdown" ng-model="nowWho" ng-options="key as value for (key , value) in nameList" ng-change="selectPerson()"></select>
							</div>
						</div>
						<div class="row">
							<div class="sixteen wide column">
								<table class="ui table selectable complex">
									<thead>
										<tr>
											<th class="one wide column">日期</th>
											<th class="two wide column">时间</th>
											<th class="one wide column">教室</th>
											<th class="one wide column">工时</th>
											<th class="five wide column">记录说明</th>
											<th class="six wide column">待办事项</th>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="record in personalRecord" class="{{record.rewardPunish | setColor}}">
											
											<td>{{record.recordDate | limitTo : 10 | adjustrDate}}</td>
											<td>{{record.startTime | limitTo : 5}} - {{record.endTime | limitTo : 5}}</td>
											<td>{{record.classroom}}</td>
											<td>{{record.hournums | number : 2}}</td>
											<td>{{record.remarks | isEmpty}}</td>
											<td>{{record.todosth | isEmpty}}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

						<div class="row centered">
							<div class="four wide column">
								<div class="ui button basic animated fade grey fluid" tabindex="0" ng-click="NewRecord()">
									<div class="visible content">新增奖惩</div>
									<div class="inverted hidden content"><i class="icon plus"></i> New Record</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="pad1" ng-show="nowPad == 1">
					<div class="ui column grid">
						<div class="row">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">本月工时统计</h1>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="column">
								<table class="ui table selectable complex">
									<thead>
										<tr>
											<th class="one wide column">#</th>
											<th class="two wide column">姓名</th>
											<th class="two wide column">学号</th>
											<th class="two wide column">本月工时</th>
											<th class="two wide column">上月累积</th>
											<th class="two wide column">本月实发</th>
											<th class="two wide column">本月累积</th>
											<th class="two wide column">奖惩说明</th>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="row in thisMonthAll">
											<td>{{$index+1}}</td>
											<td>{{row.name}}</td>
											<td>{{row.sid}}</td>
											<td>{{row.earnhour| number : 2}}</td>
											<td>{{row.oldsave| number : 2}}</td>
											<td>{{row.payhour| number : 2}}</td>
											<td>{{row.newsave| number : 2}}</td>
											<td data-html="<h6 class='ui header'>总奖惩：{{row.rpall | number:2}}</h6>{{row.remarks | replaceBR}}" class="ui popup-hovor">
												<a><i class="list icon"></i> 详情</a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

						<div class="row centered">
							<div class="four wide column">
								<div class="ui button basic animated fade green fluid" tabindex="0" ng-click="NewDeadTable()">
									<div class="visible content">截止本月工时</div>
									<div class="inverted hidden content"><i class="payment icon"></i> 发发发发发工资</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="pad2" ng-show="nowPad == 2">
					<div class="ui column grid">
						<div class="row">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">新增助理</h1>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">学号： </div>
									<input type="text" placeholder="用于登陆" ng-model="newUser.sid">
								</div>
							</div>
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">密码： </div>
									<input type="text" placeholder="多弱都行..." ng-model="newUser.password">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">姓名： </div>
									<input type="text" placeholder="拒绝非主流昵称" ng-model="newUser.name">
								</div>
							</div>
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">邮箱： </div>
									<input type="text" placeholder="其实..." ng-model="newUser.email">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">手机： </div>
									<input type="text" placeholder="长号" ng-model="newUser.phone">
								</div>
							</div>
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">短号： </div>
									<input type="text" placeholder="没有就留空" ng-model="newUser.cornet">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="eight wide column">
								<div class="ui labeled input fluid">
									<div class="ui label">银行卡号： </div>
									<input type="text" placeholder="好多位别填错了..." ng-model="newUser.bankcard">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">身份： </div>
									<select class="ui search dropdown" ng-model="newUser.power">
										<option value="">这就是人生啊...</option>
										<option value="1">助理	</option>
										<option value="2">后勤组</option>
										<option value="3">老大</option>
									</select>
								</div>
							</div>
							<div class="four wide column">
								<div class="ui approve green inverted button" ng-click="newUser_check()">确定</div>
							</div>

						</div>
						<div class="row">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">查看/修改助理信息</h1>
								</div>
							</div>
							<div class="seven wide column"></div>
							<div class="four wide column fluid">
								<select id="selectUser3"class="ui search dropdown" ng-model="User_nowWho" ng-options="key as value for (key , value) in nameList" ng-change="User_selectPerson()"></select>
							</div>
						</div>
						<div class="row">
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">学号： </div>
									<input type="text"  ng-model="User.sid">
								</div>
							</div>
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">密码： </div>
									<input type="text" ng-model="User.password">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">姓名： </div>
									<input type="text"  ng-model="User.name">
								</div>
							</div>
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">邮箱： </div>
									<input type="text" ng-model="User.email">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">手机： </div>
									<input type="text" ng-model="User.phone">
								</div>
							</div>
							<div class="four wide column">
								<div class="ui labeled input">
									<div class="ui label">短号： </div>
									<input type="text" ng-model="User.cornet">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="eight wide column">
								<div class="ui labeled input fluid">
									<div class="ui label">银行卡号： </div>
									<input type="text" ng-model="User.bankcard">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="one wide column">
								<div class="ui labeled input">
									<div class="ui label">身份： </div>
									<input type="hidden"/>
								</div>
							</div>
							<div class="three wide column">
								<div class="ui selection dropdown" id="User-select">
									<input type="hidden" id="md_power"name="gender" ng-model="User.power" />
									<div id="md_power_text"class="default text" ></div>
									<div class="menu">
										<div class="item" data-value="1" data-text="助理">
											<i class="user icon"></i> 助理
										</div>
										<div class="item" data-value="2" data-text="后勤组">
											<i class="user icon"></i> 后勤组
										</div>
										<div class="item" data-value="3" data-text="老大">
											<i class="user icon"></i> 老大
										</div>
									</div>
								</div>
							</div>
							<div class="two wide column">
								<div class="ui approve green inverted button" ng-click="User_check()">修改</div>
							</div>
							<div class="two wide column">
								<div class="ui approve red inverted button" ng-click="User_delete()">删除助理</div>
							</div>

						</div>
					</div>
				</div>

				<div class="ui modal small" id="setNewRecord">
					<div class="header">
						正在给 {{nameList[nowWho]}} 新增奖惩记录...
					</div>
					<div class="content">
						<div class="ui grid middle aligned">
							<div class="row">
								<div class="column three wide">
									改动工时：
								</div>
								<div class="column four wide">
									<div class="ui left icon input fluid">
										<input type="number" placeholder="默认为*奖励*" min="0" id="RPrecord-hournums">
										<i class="wait icon"></i>
									</div>
								</div>
								<div class="column three wide">
									<div class="ui slider checkbox">
										<input type="checkbox" id="RPrecord-rp">
										<label>惩罚</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="column three wide">
									原因备注：
								</div>
								<div class="column ten wide">
									<div class="ui left icon input fluid">
										<input type="text" placeholder="这一切是为了什么呢..." id="RPrecord-remark">
										<i class="edit icon"></i>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="actions">
						<div class="ui approve green inverted button" ng-click="setNewRPRecord()">确定</div>
					</div>
				</div>

				<div class="ui modal small" id="setNewDeadTable">
					<div class="header">
						正在截止本月的工时统计...
					</div>
					<div class="content">
						<div class="ui grid middle aligned">
							<div class="row">
								<div class="column three wide">
									统计表标题：
								</div>
								<div class="column ten wide">
									<div class="ui left icon input fluid">
										<input type="text" placeholder="请以yyyy/MM开头，便于搜索" id="setHis-title" />
										<i class="edit icon"></i>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="actions">
						<div class="ui approve green inverted button" ng-click="setNewHistory()">确定本月工时统计</div>
					</div>
				</div>

			</div>

			<div class="ui vertical footer segment" id="footer">
				<div class="ui center aligned container">
					<img src="<%=basePath%>img/logo.png" class="ui centered mini image">
					<div class="ui horizontal small divided link list">
						<a class="item popup" title="教学实验中心B栋4楼">SYSU Lab</a>
						<a class="item popup" href="http://www.leezing.com" title="Front-end">Zing Lee</a>
						<a class="item popup" title="Back-end">Ziwei Lai</a>
						<a class="item">Copyright © 2016</a>
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
