var app = angular.module('DutyPad', ['ng']);

$(document).ready(function() {
	$("input[name='username']").focus();
	// login begin
	$("#login-btn").click(function(event) {
		var usr = $("input[name='username']").val(),
			pwd = $("input[name='password']").val();
		if(!usr) {
			swal({
				title: "啊噢",
				text: "学号不能为空",
				type: "warning",
				confirmButtonText: "好"
			},
			function(){
				$("input[name='username']").focus();
			});
			return false;
		} else if(!pwd) {
			swal({
				title: "啊噢",
				text: "密码不能为空",
				type: "warning",
				confirmButtonText: "好"
			},
			function(){
				$("input[name='password']").focus();
			});
			return false;
		}
		var param = {
			"username": usr,
			"password": pwd
		}
		var str = $.param(param);
		
		$.ajax({
			url: "/DutyPad/login",
			type : "POST",
        	dataType : "json",
        	contentType : 'application/x-www-form-urlencoded; charset=utf-8',
        	cache : false,
        	data : str,
        	success : function(data) {
        		if(data.code){
    				swal({
    					title: "啊噢",
    					text: data.error,
    					type: "error",
    					confirmButtonText: "好"
    				});
    				return false;
    			} else {
    				location.href = "/DutyPad/pad";
    			}
            },
            error:function(){
            	failFor500();
            }
            
            
		});
		
		
		
		
	})
	
	$("#manage").click(function(){
		 $.ajax({
				url: "/DutyPad/manage/Entry",
				type : "POST",
		      	contentType : 'application/x-www-form-urlencoded; charset=utf-8',
		      	cache : false,
		      	dataType : "json",
		      	success : function(data) {
	        		if(checkError(data)){
	        			return ;
	        		} else {
	    				location.href = "/DutyPad/manage";
	    			}
	            },
	            error:function(){
	            	failFor500();
	            }
		      	
		      	
		      	
		      	
		      	
		 });

	});

});


var getNowDateStr = function() {
	var d = new Date();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	return month + "月" + day + "日";
}

var getUserCheckDate = function(d) {
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	return year + "-" + month + "-" + day;
}

$("#SideBar").click(function() {
	$('.ui.labeled.icon.sidebar').not('.styled').sidebar('toggle');
});

$("#SideBar").mouseover(function() {
	$('.ui.labeled.icon.sidebar').not('.styled').sidebar('show');
});


//global functions
var failFor500 = function() {
	swal({
		title: "啊哦",
		text: "后台出了点小问题，请联系一下技术人员~",
		type: "error",
		confirmButtonText: "好"
	});
};


var checkError = function(data) {
	if(data.code) {
		if(data.errorstatus === "redirectToLogin") {
			swal({
				title: "啊噢",
				text: "登陆过期了，请重新登陆",
				type: "error",
				confirmButtonText: "好"
			}, function() {
				location.href="/DutyPad";
			});
			return true;
		}

		if(data.errorstatus === "sqlError") {
			swal({
				title: "操作失败",
				text: "应该是数据库出BUG了",
				type: "error",
				confirmButtonText: "好"
			});
			return true;
		}

		if(data.errorstatus === "NoAccess") {
			swal({
				title: "啊噢",
				text: "权限不足，禁止访问",
				type: "error",
				confirmButtonText: "好"
			});
			return true;
		}
		
		swal({
				title: "啊噢",
				text: "不知道什么问题，但一定你是出错了",
				type: "error",
				confirmButtonText: "好"
			});
		return true;
	}
	return false;
}
