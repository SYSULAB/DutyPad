// pad记录是否要高亮。
app.filter('padHighlight', function() {
	return function(isImportant) {
		if (isImportant==2) {
			return "warning";
		} else {
			return "";
		}
	}
});

// 填充空字符串
app.filter('isEmpty', function() {
	return function(str) {
		if(str==null||str==undefined){
			return "";
		}
		else if (str.length) {
			return str;
		} else {
			return "";
		}
	}
})

// 设置行颜色，0表示无色，1表示绿色，-1表示红色
// 用于管理员查看个人签到记录里的 奖惩记录
app.filter('setColor', function() {
	return function(color) {
		if (color == 1) {
			return "positive";
		} else if (color == -1) {
			return "error";
		} else {
			return "";
		}
	}
})

//值班本子的日期
app.filter('adjustrDate',function(){
	return function(date){
		return date.replace("-","/").replace("-","/");
	}
})

app.filter('replaceBR', function() {
 	return function(str) {
 		return str.replace(/\r\n/g, "<br/>");
 	}
});

app.controller('PadController', function($scope) {
	$scope.perWorth = 12; // 每小时12元
	$scope.offset123Step = 7; // B401,B402,B403每次加载多少天
	$scope.offset4Step =30;// 奖惩每次加载多少天
	$scope.nowPad = 0; // 现在是第几个选项卡
	$scope.nowRoom = "B400"; // 正在看哪个本子

	// 读取本人当月工时
	$scope.nowTimes = 0;
	$scope.accTimes = 0;
	var padGetMyHour=function(){
		$.get("/DutyPad/pad/getMyHour", "", function(data) {
			if(checkError(data)){
				return;
			}
			$scope.nowTimes = data.workhour;
			$scope.accTimes = data.savehour;
			$scope.$apply();
			$("#my-progress").progress({
				value: $scope.nowTimes,
				total: Math.max(40, $scope.nowTimes),
				label: 'ratio',
				text: {
					ratio: '{value} / {total}'
				}
			})
		}, "json").fail(failFor500);
	}
	padGetMyHour();
	
	
	

	// 读取待办事项
	$scope.todoList = [];
	var padGetTodoList=function(){
		$.get("/DutyPad/pad/getTodoList", "", function(data) {
			if(checkError(data)){
				return;
			}
			$scope.todoList = data;
			$scope.$apply();
		}, "json").fail(failFor500);
	}
	padGetTodoList();
	

	// 解决了待办事项 , id需要获取。
	$scope.nowFixing = -1; // 正在解决哪个待办事项
	$scope.showFixModal = function(id) {
		$scope.nowFixing = id;
		$("#fixTaskModal").modal("show");
	}
	$scope.fixNeedTo = function() { // 确定解决
		var params = {
			"id": $scope.nowFixing,
			"text": $("#fix-note").val()
		}
		var str = $.param(params);
		$.post("/DutyPad/pad/solveTodo", str, function(data) {
			if(checkError(data)){
				return;
			}
			swal({
				title: "成功解决",
				text: "人品值 +1",
				type: "success",
				confirmButtonText: "确定"
			});
			$.get("/DutyPad/pad/getTodoList", "", function(data) {
				if(checkError(data)){
					return;
				}
				$scope.todoList = data;
				$scope.$apply();
			}, "json").fail(failFor500);
		}, "json").fail(failFor500);
	}

	// 查看本子

	// 加载本子 每个本子（401~404）都有不同的变量储存，防止重复加载
	$scope.PadList401 = []; // 储存已经加载到的列表
	$scope.checkBeginDate401 = new Date(); // 加载本子的起始日期
	$scope.padBeginNumber401 = 0; // 已经加载了多少天的记录了
	$('#addMore401').click(function() {
		
		var btn = $(this);
		btn.addClass("loading");
		var params = {
			"beginDate": $scope.checkBeginDate401,
			"room": "B401",
			"beginNumber": $scope.padBeginNumber401
		}
		var str = $.param(params);
		$.post("/DutyPad/pad/getSigninRecord", str, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.padBeginNumber401 += $scope.offset123Step;
			$scope.PadList401 = $scope.PadList401.concat(data);
			$scope.$apply();
			btn.removeClass("loading");
		}, "json").fail(failFor500);
	}); // 401 end
	
	
	$scope.PadList402 = []; // 储存已经加载到的列表
	$scope.checkBeginDate402 = new Date(); // 加载本子的起始日期
	$scope.padBeginNumber402 = 0; // 已经加载了多少天的记录了
	$('#addMore402').click(function() {
		var btn = $(this);
		btn.addClass("loading");
		var params = {
			"beginDate": $scope.checkBeginDate402,
			"room": "B402",
			"beginNumber": $scope.padBeginNumber402
		}
		var str = $.param(params);
		$.post("/DutyPad/pad/getSigninRecord", str, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.padBeginNumber402 += $scope.offset123Step;
			$scope.PadList402 = $scope.PadList402.concat(data);
			$scope.$apply();
			btn.removeClass("loading");
		}, "json").fail(failFor500);
	}); // 402 end
	
	$scope.PadList403 = []; // 储存已经加载到的列表
	$scope.checkBeginDate403 = new Date(); // 加载本子的起始日期
	$scope.padBeginNumber403 = 0; // 已经加载了多少天的记录了
	$('#addMore403').click(function() {
		var btn = $(this);
		btn.addClass("loading");
		var params = {
			"beginDate": $scope.checkBeginDate403,
			"room": "B403",
			"beginNumber": $scope.padBeginNumber403
		}
		var str = $.param(params);
		$.post("/DutyPad/pad/getSigninRecord", str, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.padBeginNumber403 += $scope.offset123Step;
			$scope.PadList403 = $scope.PadList403.concat(data);
			$scope.$apply();
			btn.removeClass("loading");
		}, "json").fail(failFor500);
	}); // 403 end

	// 奖惩记录
	$scope.PadList404 = []; // 储存已经加载到的列表
	$scope.checkBeginDate404 = new Date(); // 加载本子的起始日期
	$scope.padBeginNumber404 = 0; // 已经加载了多少天的记录了
	$('#addMore404').click(function() {
		var btn = $(this);
		btn.addClass("loading");
		var params = {
			"beginDate": $scope.checkBeginDate404,
			"room": "B404",
			"beginNumber": $scope.padBeginNumber404
		}
		var str = $.param(params);
		$.post("/DutyPad/pad/getRPRecord", str, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.padBeginNumber404 += $scope.offset4Step;
			$scope.PadList404 = $scope.PadList404.concat(data);
			$scope.$apply();
			btn.removeClass("loading");
		}, "json").fail(failFor500);
	});

	// 设置起始日期
	$scope.setBeginDayShow = function() { // 重设开始的日期，需要清空原有的。
		if ($scope.nowPad == 1) {
			$scope.checkBeginDateShow = $scope.checkBeginDate401;
		}
		if ($scope.nowPad == 2) {
			$scope.checkBeginDateShow = $scope.checkBeginDate402;
		}
		if ($scope.nowPad == 3) {
			$scope.checkBeginDateShow = $scope.checkBeginDate403;
		}
		$scope.checkBeginDate = $scope.checkBeginDateShow;
		$("#setBeginDayModal").modal('show');
	}
	$scope.setBeginDay = function(auto) {
		if(!auto){
			if($("#pad_date").val()==""){
				swal({
					title: "日期有点问题",
					text: "年，月，日都不能为空哦",
					type: "info",
					confirmButtonText: "好"
				});
				return;
			}
		}
		
		if ($scope.nowPad == 1) {
			
			$scope.checkBeginDate401 = new Date($scope.checkBeginDate);
			$scope.PadList401 = [];
			$scope.padBeginNumber401 = 0;
			$('#addMore401').click(); // 重新加载401的签到记录
		}
		if ($scope.nowPad == 2) {
			$scope.checkBeginDate402 = new Date($scope.checkBeginDate);
			$scope.PadList402 = [];
			$scope.padBeginNumber402 = 0;
			$('#addMore402').click(); // 重新加载402的签到记录
		}
		if ($scope.nowPad == 3) {
			$scope.checkBeginDate403 = new Date($scope.checkBeginDate);
			$scope.PadList403 = [];
			$scope.padBeginNumber403 = 0;
			$('#addMore403').click(); // 重新加载403的签到记录
		}
		if ($scope.nowPad == 4) {
			$scope.checkBeginDate404 = new Date($scope.checkBeginDate);
			$scope.PadList404 = [];
			$scope.padBeginNumber404 = 0;
			$('#addMore404').click(); // 重新加载奖惩记录
		}
		padGetMyHour();
		padGetTodoList();
		
	}

	$scope.changePad = function(NO) { // 切换选项卡
		$scope.nowPad = NO;
		if (NO == 1 && $scope.PadList401.length == 0) { // 第一次切换到本子会自动加载一次
			$("#addMore401").click();
		} else if (NO == 2 && $scope.PadList402.length == 0) {
			$("#addMore402").click();
		} else if (NO == 3 && $scope.PadList403.length == 0) {
			$("#addMore403").click();
		} else if (NO == 4 && $scope.PadList404.length == 0) {
			$("#addMore404").click();
		}
	}

	// 新的签到
	$scope.room = "B400"; // 签到房间号。
	$scope.newRecordShow = function(room) {
		$scope.room = room;
		$("#newRecord-beginTime").val('00:00');
		$("#newRecord-endTime").val('00:00');
		$("#newRecord-overWork").attr("checked",false);
		$("#newRecord-note").val('');
		$("#newRecord-needTo").val('');
		$("#newRecord-important").attr("checked",false);
		$("#newDutyModal").modal('show');
	}
	$scope.sendNewRecord = function() {
		// 检查时间 开始
		if($("#newRecord-beginTime").val()==""||$("#newRecord-endTime").val()==""){
			swal({
				title: "时间有问题",
				text: "两个时间都不能为空哦",
				type: "info",
				confirmButtonText: "恩"
			});
			return;
		}
		var begT = $("#newRecord-beginTime").val();
		var endT = $("#newRecord-endTime").val();

		var stt = new Date("May 1, 2015 " + begT);
		stt = stt.getTime();

		var endt = new Date("May 1, 2015 " + endT);
		endt = endt.getTime();

		if (stt >= endt) {
			swal({
				title: "时间有点不对劲噢~",
				text: "再试试呗？",
				type: "error",
				confirmButtonText: "确定"
			});
			return;
		}
		// 检查时间 结束
		var params = {
			"startTime":$("#newRecord-beginTime").val(),
			"endTime": $("#newRecord-endTime").val(),
			"extraWork": $("#newRecord-overWork").is(':checked'),
			"remarks": $("#newRecord-note").val(),
			"todosth": $("#newRecord-needTo").val(),
			"todoState": $("#newRecord-important").is(':checked')?2:0,
			"classroom": $scope.room,
			"recordType":1
		};
		var str = $.param(params);
		
		
		
		
		
		
		
		
		$.post("/DutyPad/pad/newWorkrecord", str, function(data) {
			if(checkError(data)){
				
				return;
			}
			swal({
				title: "签到成功",
				text: "记得关灯关窗关电脑噢！",
				type: "success",
				confirmButtonText: "确定"
			});
			$scope.checkBeginDate = new Date();
			$scope.setBeginDay(true);
		}, "json").fail(failFor500);
	}
	$scope.nowDate = getNowDateStr();

	// 查看本子
	
	// 通讯录
	$scope.contacts = [];
	$.get("/DutyPad/pad/getContacts", "", function(data){
		if(checkError(data)){
			return;
		}
		$scope.contacts = data;
		$scope.$apply();
	}, "json").fail(failFor500);

	$scope.Math = window.Math;
});
////// 首页结束
///////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////
////// 工时页开始！
app.controller('TimesController', function($scope, $filter) {
	// 显示第几页
	$scope.nowPad = 0;
	$scope.changePad = function(NO) { // 切换选项卡
		$scope.nowPad = NO;
	}

	$scope.offset123Step=7;
	
	// 个人工时
	$scope.perWorth = 12;
	$scope.nowTimes = 0;
	$scope.accTimes = 0;
	$.get("/DutyPad/times/getMyHour", "", function(data) {
		if(checkError(data)){
			return;
		}
		$scope.nowTimes = data.workhour;
		$scope.accTimes = data.savehour;
		$scope.$apply();
		$("#my-progress").progress({
			value: $scope.nowTimes,
			total: Math.max(40, $scope.nowTimes),
			label: 'ratio',
			text: {
				ratio: '{value} / {total}'
			}
		})
	}, "json").fail(failFor500);

	// 查看个人工时记录
	$scope.PadList = []; // 储存已经加载到的列表
	$scope.checkBeginDate = new Date(); // 加载本子的起始日期
	$scope.padBeginNumber = 0; // 已经加载了多少天的记录了
	$('#addMore').click(function() {
		var btn = $(this);
		btn.addClass("loading");
		var params = {
			"beginDate": $scope.checkBeginDate,
			"beginNumber": $scope.padBeginNumber
		}
		var str = $.param(params);
		$.post("/DutyPad/times/getMyRecord", str, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.padBeginNumber += $scope.offset123Step;
			$scope.PadList = $scope.PadList.concat(data);
			$scope.$apply();
			btn.removeClass("loading");
		}, "json").fail(failFor500);
	});
	$('#addMore').click();

	// 更改起始日期
	$scope.setBeginDayShow = function() { // 重设开始的日期，需要清空原有的。
		$scope.checkBeginDateShow = $scope.checkBeginDate;
		$scope.checkBeginDateModify = $scope.checkBeginDateShow;
		$("#setBeginDayModal").modal('show');
	}
	$scope.setBeginDay = function(auto) {
		if(!auto){
			if($("#times_date").val()==""){
				swal({
					title: "日期有点问题",
					text: "年，月，日都不能为空哦",
					type: "info",
					confirmButtonText: "好"
				});
				return;
			}
		}
		
		$scope.checkBeginDate = new Date($scope.checkBeginDateModify);
		$scope.PadList = [];
		$scope.padBeginNumber = 0;
		$('#addMore').click(); // 重新加载401的签到记录
		
	}

	// 查看本月工时
	$scope.MonthList = [];
	$.get("/DutyPad/times/getThismonthHour", "", function(data) {
		if(checkError(data)){
			return;
		}
		$scope.MonthList = data;
		$scope.$apply();
		$(".month-row").each(function(n, el) {
			var pro = $($(el).find(".indicating.progress"));
			var times = pro.attr('data-value');
			pro.progress({
				value: times,
				total: Math.max(40, times),
				label: 'ratio',
				text: {
					ratio: '{value} / {total}'
				}
			});
			//			console.log(times);
		});
	}, "json").fail(failFor500);

	// 查看历史工时
	$scope.historyList = {
		"0": "请选择表格"
	}; // 所有的死表列表
	$scope.nowHistoryTable = []; // 目前在查看的死表
	$scope.checkHistory = ""; // 绑定为select的值
	$.get("/DutyPad/times/salaryIndexList", "", function(data) { // 获取列表
		if(checkError(data)){
			return;
		}
		$scope.historyList = data;
		$scope.$apply();
	}, "json").fail(failFor500);

	$scope.getHistoryTable = function() {
		var params = {
			"id": $scope.checkHistory
		}
		var str = $.param(params);
		/*$.get("http://127.0.0.1:5000/test", str, function(data) {
			checkTimeout(data);
			$scope.nowHistoryTable = [{
				"name": "李一正",
				"sid": "14348888",
				"times": 47,
				"last": 24,
				"willGet": 40,
				"willLast": 31,
				"note": "帮忙搬东西 +3\r\nFFC比赛加班 +12\r\n无故缺勤 -2\r\n维修小分队 +40\r\n",
				"noteAll": 30
			}, {
				"name": "李二正",
				"sid": "14342288",
				"times": 23,
				"last": 121,
				"willGet": 40,
				"willLast": 104,
				"note": "FFC比赛加班 +12",
				"noteAll": 12
			}]
			$scope.$apply();
			$(".popup-hovor").popup({
				hoverable: true,
				position: 'left center'
			});
		}, "json").fail(failFor500);
		*/
		
		$.get("/DutyPad/times/salaryIndexRecord", str, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.nowHistoryTable = data;
			$scope.$apply();
			$(".popup-hovor").popup({
				hoverable: true,
				position: 'left center'
			});
		}, "json").fail(failFor500);
	}

	$scope.Math = window.Math;
});
/////////////////////////////////////////////

/////////////////////////////////////////////
///////// 管理页面
app.controller('ManageController', function($scope) {
	// 显示第几页
	
	$scope.nowPad = 0;
	$scope.changePad = function(NO) { // 切换选项卡
		$scope.nowPad = NO;
	}

	// 查看个人记录
	$scope.nowWho = "0";
	$scope.nameList = {
		"0": "请先选择助理"
	};
	$scope.personalRecord = [];
	
	var manageGetAssistList=function(){
		$.get("/DutyPad/manage/getAssistList", "", function(data) {
			if(checkError(data)){
				return;
			}
			$scope.nameList = data;
			$scope.$apply();
		}, "json").fail(failFor500);
	};
	manageGetAssistList();

	
	$scope.selectPerson = function() {
			var params = {
				"id": $scope.nowWho
			}
			var str = $.param(params);
			$.get("/DutyPad/manage/getAssistRecord", str, function(data) {
				if(checkError(data)){
					return;
				}
				$scope.personalRecord = data;
				$scope.$apply();
			}, "json").fail(failFor500);
	}
	
	
	

	// check duty record
	$scope.NewRecord = function() {
		if($scope.nowWho == "0") {
			swal({
				title: "请先选择助理",
				type : "info",
				confirmButtonText : "好"
			});
			return false;
		};
		$("#RPrecord-hournums").val('');
		$("#RPrecord-rp").attr("checked",false);
		$("#RPrecord-remark").val('');
		$("#setNewRecord").modal('show');
	}
	
	//发送奖惩记录
	//这里注意在swal背后，如果加了json返回必须是json，不加的返回可以不是json
	$scope.setNewRPRecord = function() {
		if($("#RPrecord-hournums").val()<=0){
			swal({
				title: "嗯......",
				text: "工时数不能小于0，要惩罚就点旁边吧",
				type: "error",
				confirmButtonText: "确定"
			});
			return;
		}
		var params = {
				"hournums":$("#RPrecord-hournums").val(),
				"rewardPunish":$("#RPrecord-rp").is(':checked')?-1:1,
				"remarks":$("#RPrecord-remark").val(),
				"affectedid":$scope.nowWho,
				"recordType":2
		};
		
		var str = $.param(params);
		$.post("/DutyPad/manage/newRPRecord", str, function(data) {
			if(checkError(data)){
				return;
			}
			swal({
				title: "搞定了",
				text: "成功给"+$scope.nameList[$scope.nowWho]+"添加了一条奖惩记录",
				type: "success",
				confirmButtonText: "确定"
			});
			$scope.selectPerson();
			manageGetThemonthHour();
		}, "json").fail(failFor500);
	}
	
	
	// 获取本月工时表
	$scope.thisMonthAll = [];
	var manageGetThemonthHour=function(){
		$.get("/DutyPad/manage/getThemonthHour", null, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.thisMonthAll = data;
			$scope.$apply();
			$(".popup-hovor").popup({
				 hoverable: true,
				 position: 'left center'
			});
		}, "json").fail(failFor500);
	};
	manageGetThemonthHour();

	// 显示确定死表的框
	$scope.NewDeadTable = function() {
		$("#setNewDeadTable").modal('show');
	}

	// 发送 确定新建死表
	$scope.setNewHistory = function() {
		var params = {
			"title": $("#setHis-title").val()
		}
		var str = $.param(params);
		$.post("/DutyPad/manage/gaiChuo", str, function(data) {
			if(checkError(data)){
				return;
			}
			swal({
				title: "统计成功",
				text: "大伙都等着发工资呐",
				type: "success",
				confirmButtonText: "确定"
			});
			$scope.thisMonthAll = [];
			manageGetThemonthHour();
			$scope.selectPerson();
		}, "json").fail(failFor500);
	}

	// 助理管理
	$scope.newUser = {};
	$scope.newUser_check = function() {
		//新增助理只限制学号，密码，姓名，和身份必须有
		if ($scope.newUser==null||!$scope.newUser.sid) {
			swal({
				title: "等等",
				text: "助理学号没填呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		if (!$scope.newUser.password) {
			swal({
				title: "等等",
				text: "助理密码没填呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		if (!$scope.newUser.name) {
			swal({
				title: "等等",
				text: "助理姓名没填呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		if (!$scope.newUser.power) {
			swal({
				title: "等等",
				text: "助理权限没选呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		// 开始构造
		var params = {
				"sid": $scope.newUser.sid,
				"name": $scope.newUser.name,
				"password": $scope.newUser.password,
				"email": $scope.newUser.email,
				"phone": $scope.newUser.phone,
				"cornet": $scope.newUser.cornet,
				"bankcard": $scope.newUser.bankcard,
				"power": $scope.newUser.power
		}
		var str = $.param(params);
		$.post("/DutyPad/manage/newdAssist", str, function(data) {
			if(checkError(data)){
				return;
			}
			swal({
				title: "新增成功",
				text: "快教TA用本系统吧~",
				type: "success",
				confirmButtonText: "确定"
			});
			manageGetAssistList();
			manageGetThemonthHour();
			//清空，但不清空权限
			$scope.newUser.sid="";
			$scope.newUser.name="";
			$scope.newUser.password="";
			$scope.newUser.email="";
			$scope.newUser.phone="";
			$scope.newUser.cornet="";
		}, "json").fail(failFor500);
	};

	// 查看、修改用户信息
	$scope.User = {};
	
	
	
	
	$scope.User_nowWho = "0"; // 下拉框选择了哪个助理
	auth = ["无", "助理", "后勤组", "老大"]; // 下标对应着不同的权限数字
	$scope.User_selectPerson = function() {
		var params = {
			"id": $scope.User_nowWho
		}
		var str = $.param(params);
		$.get("/DutyPad/manage/getSelectedAssist", str, function(data) {
			if(checkError(data)){
				return;
			}
			$scope.User=data;
			//$scope.User.power = data.power==null?0:data.power; // 权限（身份）
			$("#User-select").dropdown('set selected', auth[$scope.User.power]);
			
			$scope.$apply();
		}, "json").fail(failFor500);
	};
	
	$scope.User_check = function() {
		//修改助理只限制学号，密码，姓名，和身份必须有
		if ($scope.User_nowWho == "0") {
			swal({
				title: "请先选择一位助理",
				type : "info",
				confirmButtonText: "好"
			})
			return false;
		}

		$scope.User.power=$("#md_power").val();
		if ($scope.User==null||!$scope.User.sid) {
			swal({
				title: "等等",
				text: "助理学号没填呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		if (!$scope.User.password) {
			swal({
				title: "等等",
				text: "助理密码没填呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		if (!$scope.User.name) {
			swal({
				title: "等等",
				text: "助理姓名没填呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		if (!$scope.User.power) {
			swal({
				title: "等等",
				text: "助理权限没选呢",
				type: "info",
				confirmButtonText: "好吧"
			});
			return false;
		};
		// 开始构造
		var params = {
			"sid": $scope.User.sid,
			"name": $scope.User.name,
			"password": $scope.User.password,
			"email": $scope.User.email,
			"phone": $scope.User.phone,
			"cornet": $scope.User.cornet,
			"bankcard": $scope.User.bankcard,
			"power": $scope.User.power,
			"id":$scope.User_nowWho
		}
		var str = $.param(params);
		$.post("/DutyPad/manage/modifySelectedAssist", str, function(data) {
			if(checkError(data)){
				return;
			}
			swal({
				title: "修改成功",
				text: $scope.nameList[$scope.User_nowWho]+"的信息被你改了哦！",
				type: "success",
				confirmButtonText: "确定"
			});

			manageGetAssistList();
			manageGetThemonthHour();
			$("#selectUser3").next().next().next().text($scope.User.name);
			$("#selectUser1").next().next().next().text($scope.User.name);
		}, "json").fail(failFor500);
	}

	$scope.User_delete = function() {
		if ($scope.User_nowWho == "0") {
			swal({
				title: "请先选择一位助理",
				type : "info",
				confirmButtonText: "好"
			})
			return false;
		}
		swal({
			title: "警告",
			text: "真的要删除 "+$scope.User.name+" 吗？",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: "#DD6B55",
			confirmButtonText: "删掉吧",
			cancelButtonText: "再想想",
			closeOnConfirm: false,
			closeOnCancel: false
		}, function(isConfirm) {
			if (isConfirm) {
				var params = {
						"id":$scope.User_nowWho
					}
					var str = $.param(params);
				$.post("/DutyPad/manage/deleteSelectedAssist", str, function(data) {
					if(checkError(data)){
						return;
					}
					swal("删除啦", "这个助理消失啦", "success");
					$scope.User=null;
					$scope.User_nowWho ="0";
					manageGetAssistList();
					manageGetThemonthHour();
					$scope.selectPerson();
					$("#selectUser3").next().next().next().text("");
					$("#selectUser1").next().next().next().text("");
					$("#User-select").find(".text").text("");
				}, "json").fail(failFor500);
				
			} else {
				swal("取消啦", "取消成功！", "error");
			}
		});
	}

	$scope.HisTableTitle = "2016年3月工时统计表";
});