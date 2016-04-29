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
		<title>值班详情 - 本子</title>
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
			<a class="item" href="pad">
				<i class="book icon"></i> 本子
			</a>
			<a class="item">
				<i class="tasks layout icon green"></i> 工时
			</a>
			<a class="item" id="manage">
				<i class="settings icon"></i> 管理
			</a>
			<a class="item bottomed" href="logout">
				<i class="sign out icon"></i> 注销
			</a>
		</div>

		<div ng-controller="TimesController" class="times-ctrler pusher" id="container">

			<div class="showSidebar" id="SideBar">
				<div class="ui button animated fade white" tabindex="0">
					<div class="visible content"><i class="sidebar icon button"></i></div>
					<div class="hidden content">菜单</div>
				</div>
			</div>

			<div class="ui container piled segment" id="Main">
				<div class="ui top attached tabular menu">
					<span class="item"><i class="tasks layout icon"></i></span>
					<a class="green item active" ng-click="changePad(0)">我的工时详情</a>
					<a class="green item" ng-click="changePad(1)">本月工时</a>
					<a class="green item" ng-click="changePad(2)">历史工时</a>
				</div>
				<div class="pad0" ng-show="nowPad==0">
					<div class="ui one column grid">
						<div class="row fluid">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">我的本月工时</h1>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="sixteen wide column">
								<div class="ui progress-row">
									<div class="ui large indicating progress" data-value="{{nowTimes}}" data-total="{{Math.max(40, nowTimes)}}" id="my-progress">
										<div class="bar">
											<div class="progress"></div>
										</div>
										<div class="label">本月工时：{{nowTimes}}， 累积工时：{{accTimes}}，总值：{{(nowTimes+accTimes)*perWorth | currency : '￥'}}</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">我的记录</h1>
								</div>
							</div>
							<div class="seven wide column">

							</div>
							<div class="four wide column">
								<div class="ui button basic animated fade black huge fluid" tabindex="0" ng-click="setBeginDayShow()">
									<div class="visible content">{{checkBeginDate | date: 'yyyy年MM月dd日'}}</div>
									<div class="inverted hidden content">
										<i class="calendar outline icon"></i> 更改起始日期
									</div>
								</div>
							</div>
						</div>
						<div class="column">
							<table class="ui table selectable complex">
								<thead>
									<tr>
										<th>日期</th>
										<th>时间</th>
										<th>工时</th>
										<th>教室</th>
										<th>值班说明(奖惩说明)</th>
										<th>待办事项</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="record in PadList" class="{{record.todoState | padHighlight}}">
										<td>{{record.recordDate | limitTo : 10 | adjustrDate}}</td>
										<td>{{record.startTime | limitTo : 5}} - {{record.endTime | limitTo : 5}}</td>
										<td>{{record.hournums | number : 2}}</td>
										<td>{{record.classroom}}</td>
										<td>{{record.remarks | isEmpty}}</td>
										<td>{{record.todosth | isEmpty}}</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="row centered">
							<div class="ui button basic animated fade grey" tabindex="0" id="addMore">
								<div class="visible content">加载更多</div>
								<div class="inverted hidden content"><i class="icon plus"></i> more</div>
							</div>
						</div>
					</div>
				</div>
				<div class="pad1" ng-show="nowPad==1">
					<div class="ui column grid middle aligned">
						<div class="row fluid">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">本月工时</h1>
								</div>
							</div>
						</div>
						<div class="row fluid">
							<div class="column">
								<table class="ui table selectable complex">
									<thead>
										<tr>
											<th class="one wide column">#</th>
											<th class="two wide column">姓名</th>
											<th class="nine wide column">本月进度</th>
											<th class="two wide column">积累工时</th>
											<th class="two wide column">总工时</th>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="row in MonthList" class="month-row">
											<td>{{$index + 1}}</td>
											<td>{{row.name}}</td>
											<td>
												<div class="ui small indicating progress" data-value="{{row.workhour|number:2}}" data-total="Math.max(40, row.workhour)">
													<div class="bar">
														<div class="progress"></div>
													</div>
												</div>
											</td>
											<td>{{row.savehour|number:2}}</td>
											<td>{{row.savehour + row.workhour|number:2}}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

				<div class="pad2" ng-show="nowPad==2">
					<div class="ui column grid middle aligned">
						<div class="row fluid">
							<div class="five wide column fluid">
								<div class="ui top-title fluid">
									<h1 class="fluid">历史工时</h1>
								</div>
							</div>
							<div class="seven wide column"></div>
							<div class="four wide column fluid">
								<select class="ui search dropdown" ng-model="checkHistory" ng-options="key as value for (key , value) in historyList" ng-change="getHistoryTable()"></select>
							</div>
						</div>
						<div class="row fluid">
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
										<tr ng-repeat="row in nowHistoryTable">
											<td>{{$index + 1}}</td>
											<td>{{row.name}}</td>
											<td>{{row.sid}}</td>
											<td>{{row.earnhour|number:2}}</td>
											<td>{{row.oldsave|number:2}}</td>
											<td>{{row.payhour|number:2}}</td>
											<td>{{row.newsave|number:2}}</td>
											<td data-html="<h6 class='ui header'>总奖惩：{{row.rpall | number:2}}</h6>{{row.remarks | replaceBR}}" class="ui popup-hovor">
												<a><i class="list icon"></i> 详情</a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
				<div class="ui modal small" id="setBeginDayModal">
					<div class="header">
						客官，现在是{{checkBeginDateShow | date : 'yyyy-MM-dd'}}，要翻到哪页呢？
					</div>
					<div class="content">
						<div class="ui grid middle aligned">
							<div class="row">
								<div class="column six wide">
									<div class="ui left icon input fluid">
										<input id="times_date"type="date" ng-model="checkBeginDateModify" min="2016-05-01" max="2099-05-01">
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
