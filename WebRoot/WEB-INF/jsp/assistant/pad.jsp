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
		<title>总览 - 本子</title>
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
		<div class="ui left demo vertical sidebar labeled icon menu inverted">
			<a class="item">
				<i class="book icon green"></i> 本子
			</a>
			<a class="item" href="times">
				<i class="tasks layout icon"></i> 工时
			</a>
			<a class="item" id="manage">
				<i class="settings icon"></i> 管理
			</a>
			<a class="item" href="logout">
				<i class="sign out icon"></i> 注销
			</a>
		</div>

		<div ng-controller="PadController" class="pad-ctrler pusher" id="container">

			<div class="showSidebar" id="SideBar">
				<div class="ui button animated fade white" tabindex="0">
					<div class="visible content"><i class="sidebar icon button"></i></div>
					<div class="hidden content">菜单</div>
				</div>
			</div>

			<div class="ui container piled segment" id="Main">
				<div class="ui top attached tabular menu">
					<span class="item"><i class="book icon"></i></span>
					<a class="green item active" ng-click="changePad(0)">值班总览</a>
					<a class="green item" ng-click="changePad(1)">B401</a>
					<a class="orange item" ng-click="changePad(2)">B402</a>
					<a class="blue item" ng-click="changePad(3)">B403</a>
					<a class="green item" ng-click="changePad(4)">奖惩记录</a>
					<a class="green item" ng-click="changePad(5)">通讯录</a>
				</div>

				<div class="ui bottom attached segment">
					<div class="pad0" ng-show="nowPad == 0">
						<div class="ui two column grid">
							<div class="row fluid">
								<div class="five wide column fluid">
									<div class="ui top-title fluid">
										<h1 class="fluid">我的本月工时</h1>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="thirteen wide column">
									<div class="ui progress-row">
										<div class="ui large indicating progress" data-value="{{nowTimes|number:2}}" data-total="{{Math.max(40, nowTimes)}}" id="my-progress">
											<div class="bar">
												<div class="progress"></div>
											</div>
											<div class="label">本月工时：{{nowTimes|number:2}}， 累积工时：{{accTimes|number:2}}，总值：{{(nowTimes+accTimes)*perWorth | currency : '￥'}}</div>
										</div>
									</div>
								</div>
								<div class="three wide column right aligned">
									<div class="ui progress-row" id="dutyDetail">
										<a class="ui inverted green button large" href="times">值班详情</a>
									</div>
								</div>
							</div>
						</div>
						<br />

						<div class="ui four column grid">
							<div class="row fluid">
								<div class="five wide column fluid">
									<div class="ui top-title fluid">
										<h1 class="fluid">待办事项</h1>
									</div>
								</div>
							</div>

							<div class="row progress-row">
								<div class="column" ng-click="showFixModal(todo.id)" ng-repeat="todo in todoList">
									<div class="card-col">
										<a class="ui card green">
											<div class="content">
												<div class="header">{{todo.classroom}}</div>
												<div class="meta">
													<span class="category">{{todo.recordDate}}</span>
												</div>
												<div class="description">
													<p>{{todo.todosth}}</p>
												</div>
											</div>
											<div class="extra content">
												<div class="right floated author">
													<img src="http://www.semantic-ui.cn/examples/assets/images/wireframe/square-image.png" class="ui avatar image">{{todo.assistname}}
												</div>
											</div>
										</a>
									</div>
								</div>
							</div>

						</div>
					</div>
					<div class="pad1" ng-show="nowPad == 1">
						<div class="progress-row">
							<div class="ui one column grid">
								<div class="sixteen wide column">
									<div class="ui fluid green inverted button huge" ng-click="newRecordShow('B401')">
										<i class="icon plus"></i> 签到
									</div>
								</div>
								<div class="row fluid">
									<div class="five wide column fluid">
										<div class="ui top-title fluid">
											<h1 class="fluid">值班记录本</h1>
										</div>
									</div>
									<div class="seven wide column fluid">

									</div>
									<div class="four wide column">
										<div class="ui button basic animated fade black fluid" tabindex="0">
											<div class="visible content">{{checkBeginDate401 | date: 'yyyy年MM月dd日'}}</div>
											<div class="inverted hidden content" ng-click="setBeginDayShow()">
												<i class="calendar outline icon"></i> 更改起始日期
											</div>
										</div>
									</div>
								</div>
								<div class="column">
									<table class="ui table selectable complex">
										<thead>
											<tr>
												<th class="two wide column">日期</th>
												<th class="two wide column">时间</th>
												<th class="one wide column">工时</th>
												<th class="one wide column">姓名</th>
												<th class="five wide column">值班说明</th>
												<th class="six wide column">待办事项</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="record in PadList401" class="{{record.todoState | padHighlight}}">
												<td>{{record.recordDate | limitTo : 10 | adjustrDate}}</td>
												<td>{{record.startTime | limitTo : 5}} - {{record.endTime | limitTo : 5}}</td>
												
												<td>{{record.hournums | number : 2}}</td>
												<td>{{record.assistname}}</td>
												<td>{{record.remarks | isEmpty}}</td>
												<td>{{record.todosth | isEmpty | replaceBR}}</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="row centered">
									<div class="ui button basic animated fade grey" tabindex="0" id="addMore401">
										<div class="visible content">加载更多</div>
										<div class="inverted hidden content"><i class="icon plus"></i> more</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="pad2" ng-show="nowPad == 2">
						<div class="progress-row">
							<div class="ui one column grid">
								<div class="sixteen wide column">
									<div class="ui fluid orange inverted button huge" ng-click="newRecordShow('B402')">
										<i class="icon plus"></i> 签到
									</div>
								</div>
								<div class="row fluid">
									<div class="five wide column fluid">
										<div class="ui top-title fluid">
											<h1 class="fluid">值班记录本</h1>
										</div>
									</div>
									<div class="seven wide column fluid">

									</div>
									<div class="four wide column">
										<div class="ui button basic animated fade black fluid" tabindex="0">
											<div class="visible content">{{checkBeginDate402 | date: 'yyyy年MM月dd日'}}</div>
											<div class="inverted hidden content" ng-click="setBeginDayShow()">
												<i class="calendar outline icon"></i> 更改起始日期
											</div>
										</div>
									</div>
								</div>
								<div class="column">
									<table class="ui table selectable complex">
										<thead>
											<tr>
												<th class="two wide column">日期</th>
												<th class="two wide column">时间</th>
												<th class="one wide column">工时</th>
												<th class="one wide column">姓名</th>
												<th class="five wide column">值班说明</th>
												<th class="six wide column">待办事项</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="record in PadList402" class="{{record.todoState | padHighlight}}">
												<td>{{record.recordDate | limitTo : 10 | adjustrDate}}</td>
												<td>{{record.startTime | limitTo : 5}} - {{record.endTime | limitTo : 5}}</td>
												<td>{{record.hournums | number : 2}}</td>
												<td>{{record.assistname}}</td>
												<td>{{record.remarks | isEmpty}}</td>
												<td>{{record.todosth | isEmpty}}</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="row centered">
									<div class="ui button basic animated fade grey" tabindex="0" id="addMore402">
										<div class="visible content">加载更多</div>
										<div class="inverted hidden content"><i class="icon plus"></i> more</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="pad3" ng-show="nowPad == 3">
						<div class="progress-row">

							<div class="ui one column grid">
								<div class="sixteen wide column">
									<div class="ui fluid blue inverted button huge" ng-click="newRecordShow('B403')">
										<i class="icon plus"></i> 签到
									</div>
								</div>
								<div class="row fluid">
									<div class="five wide column fluid">
										<div class="ui top-title fluid">
											<h1 class="fluid">值班记录本</h1>
										</div>
									</div>
									<div class="seven wide column fluid">

									</div>
									<div class="four wide column">
										<div class="ui button basic animated fade black fluid" tabindex="0">
											<div class="visible content">{{checkBeginDate403 | date: 'yyyy年MM月dd日'}}</div>
											<div class="inverted hidden content" ng-click="setBeginDayShow()">
												<i class="calendar outline icon"></i> 更改起始日期
											</div>
										</div>
									</div>
								</div>
								<div class="column">
									<table class="ui table selectable complex">
										<thead>
											<tr>
												<th class="two wide column">日期</th>
												<th class="two wide column">时间</th>
												<th class="one wide column">工时</th>
												<th class="one wide column">姓名</th>
												<th class="five wide column">值班说明</th>
												<th class="six wide column">待办事项</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="record in PadList403" class="{{record.todoState | padHighlight}}">
												<td>{{record.recordDate | limitTo : 10 | adjustrDate}}</td>
												<td>{{record.startTime | limitTo : 5}} - {{record.endTime | limitTo : 5}}</td>
												<td>{{record.hournums | number : 2}}</td>
												<td>{{record.assistname}}</td>
												<td>{{record.remarks | isEmpty}}</td>
												<td>{{record.todosth | isEmpty}}</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="row centered">
									<div class="ui button basic animated fade grey" tabindex="0" id="addMore403">
										<div class="visible content">加载更多</div>
										<div class="inverted hidden content"><i class="icon plus"></i> more</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="pad4" ng-show="nowPad == 4">
						<div class="progress-row">
							<div class="ui one column grid">
								<div class="row fluid">
									<div class="five wide column fluid">
										<div class="ui top-title fluid">
											<h1 class="fluid">奖惩记录本</h1>
										</div>
									</div>
									<div class="seven wide column fluid">

									</div>
									<div class="four wide column">
										<div class="ui button basic animated fade black fluid" tabindex="0">
											<div class="visible content">{{checkBeginDate404 | date: 'yyyy年MM月dd日'}}</div>
											<div class="inverted hidden content" ng-click="setBeginDayShow()">
												<i class="calendar outline icon"></i> 更改起始日期
											</div>
										</div>
									</div>
								</div>
								<div class="column">
									<table class="ui table selectable complex">
										<thead>
											<tr>
												<th class="two wide column">日期</th>
												<th class="one wide column">变动工时</th>
												<th class="one wide column">姓名</th>
												<th class="five wide column">奖惩说明</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="record in PadList404" >
												<td>{{record.recordDate | limitTo : 10 | adjustrDate}}</td>
												<td>{{record.hournums | number : 2}}</td>
												<td>{{record.assistname}}</td>
												<td>{{record.remarks | isEmpty}}</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="row centered">
									<div class="ui button basic animated fade grey" tabindex="0" id="addMore404">
										<div class="visible content">加载更多</div>
										<div class="inverted hidden content"><i class="icon plus"></i> more</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="pad5" ng-show="nowPad == 5">
						<div class="progress-row">

							<div class="ui one column grid">
								
								<div class="row fluid">
									<div class="five wide column fluid">
										<div class="ui top-title fluid">
											<h1 class="fluid">通讯录</h1>
										</div>
									</div>
								</div>
								<div class="column">
									
									<table class="ui table selectable complex">
										<thead>
											<tr>
												<th class="one wide column">#</th>
												<th class="one wide column">姓名</th>
												<th class="two wide column">学号</th>
												<th class="three wide column">手机</th>
												<th class="two wide column">短号</th>
												<th class="three wide column">邮箱</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="row in contacts">
												<td>{{$index + 1}}</td>
												<td>{{row.name   | isEmpty}}</td>
												<td>{{row.sid    | isEmpty}}</td>
												<td>{{row.phone  | isEmpty}}</td>
												<td>{{row.cornet | isEmpty}}</td>
												<td>{{row.email  | isEmpty}}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

					<div class="ui modal small" id="newDutyModal">
						<div class="header">
							新增{{room}}签到记录，{{nowDate}}
						</div>
						<div class="content">
							<div class="ui grid middle aligned">
								<div class="row">
									<div class="column four wide">
										<div class="ui left icon input fluid">
											<input type="time" placeholder="起始时间..." id="newRecord-beginTime">
											<i class="calendar icon"></i>
										</div>
									</div>
									<div class="column one wide">
										<i class="pointing right icon"></i> 至
									</div>
									<div class="column four wide">
										<div class="ui left icon input fluid">
											<input type="time" placeholder="结束时间..." id="newRecord-endTime">
											<i class="calendar icon"></i>
										</div>
									</div>
									<div class="column three wide">
										<div class="ui toggle checkbox">
											<input type="checkbox" id="newRecord-overWork">
											<label>加班</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="column three wide">
										值班说明：
									</div>
									<div class="column ten wide">
										<div class="ui form">
											<div class="field">
												<textarea type="text" placeholder="人活着有什么意义..." rows="3" id="newRecord-note"></textarea>
											</div>
										</div>

									</div>
								</div>
								<div class="row">
									<div class="column three wide">
										待办事项：
									</div>
									<div class="column ten wide">
										<div class="ui form">
											<div class="field">
												<textarea type="text" placeholder="哪些电脑又炸了...？" rows="3" id="newRecord-needTo"></textarea>
											</div>
										</div>
									</div>
									<div class="column three wide">
										<div class="ui toggle checkbox">
											<input type="checkbox" id="newRecord-important">
											<label>严重</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="actions">
							<div class="ui approve green inverted button" ng-click="sendNewRecord()">提交</div>
						</div>
					</div>

					<div class="ui modal small" id="setBeginDayModal">
						<div class="header">
							客官，现在是{{checkBeginDateShow | date: 'yyyy年MM月dd日'}}，要翻到哪页呢？
						</div>
						<div class="content">
							<div class="ui grid middle aligned">
								<div class="row">
									<div class="column six wide">
										<div class="ui left icon input fluid">
											<input id="pad_date"type="date" ng-model="checkBeginDate" min="2016-05-01" max="2099-05-01">
											<i class="calendar icon"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="actions">
							<div class="ui approve green inverted button" ng-click="setBeginDay()">确定</div>
						</div>
					</div>

					<div class="ui modal small" id="fixTaskModal">
						<div class="header">
							问题解决了呀？
						</div>
						<div class="content">
							<div class="ui grid middle aligned">
								<div class="row">
									<div class="column two wide">
										备注：
									</div>
									<div class="column ten wide">
										<div class="ui left icon input fluid">
											<input type="text" placeholder="留下点经验介绍呗..." id="fix-note">
											<i class="tag icon"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="actions">
							<div class="ui approve green inverted button" ng-click="fixNeedTo()"><i class="alarm slash outline icon"></i>取消首页提醒</div>
						</div>
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
