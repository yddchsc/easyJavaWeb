<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>住院不再难——病床安排网</title>
	<link rel="stylesheet" href="css/style.css">
	<link href="css/styles.css" rel="stylesheet" type="text/css" />
	<!-- For-Mobile-Apps-and-Meta-Tags -->
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#menu2 li a").wrapInner( '<span class="out"></span>' );

			$("#menu2 li a").each(function() {
				$( '<span class="over" style="width:192.7px">' +  $(this).text() + '</span>' ).appendTo( this );
			});

			$("#menu2 li a").hover(function() {
		$(".out",	this).stop().animate({'top':	'48px'},	300); // move down - hide
		$(".over",	this).stop().animate({'top':	'0px'},		300); // move down - show

	}, function() {
		$(".out",	this).stop().animate({'top':	'0px'},		300); // move up - show
		$(".over",	this).stop().animate({'top':	'-48px'},	300); // move up - hide
	});

		});
	</script>
	<!-- //For-Mobile-Apps-and-Meta-Tags -->
	<style type="text/css">
		ul.notice li{
			color:#ffffff;
			margin:10px 0 0 20px;
			list-style-type:disc;
		}
		ul.other li{
			color:#ffffff;
			margin:10px 0 0 20px;
			font-size: 20px;
		}
		#menu2{
			margin: 0;
			width: 1349px;
		}
		#menu2 a{
			margin: 0 auto;
			width: 192.7px;
		}
	</style>
</head>
<body>
	<div id="menu2" class="menu">
		<ul>
			<li><a href="#">首 页</a></li>
			<li><a href="#">媒体报道</a></li>
			<li><a href="#">学术活动</a></li>
			<li><a href="#">招聘信息</a></li>
			<li><a href="#">快速通道</a></li>
			<li><a href="#">爱心专栏</a></li>
			<li><a href="#">联系我们</a></li>
		</ul>
		<div class="cls"></div>
	</div>	
	<h1>住院不再难——病床安排网</h1>
	<div class="container">
		<div class="left w3l">
			<h3>快速查询</h3>
			<div class="register agileits">
				<form action="<%=request.getContextPath()%>/searchServlet" method="post">	
					<input type="submit" name="submit" value="点击查询">
				</form>
			</div>
			<h3>发送邮件</h3>
			<div class="send-mail">
				<form action="#" method="post">
					<input type="text" class="name" name="username" placeholder="您的姓名" required="">
					<input type="text" class="email" name="email" placeholder="您的邮箱" required="">
					<textarea name="your message" placeholder="想说的话"></textarea>
					<input type="submit">
				</form>
			</div>
			<h3 style="margin:20px auto">联系我们</h3>
			<div class="socialicons w3">
				<ul>
					<li><a class="facebook" href="#"></a></li>
					<li><a class="twitter" href="#"></a></li>
					<li><a class="google" href="#"></a></li>
					<li><a class="pinterest" href="#"></a></li>
					<li><a class="linkedin" href="#"></a></li>
				</ul>
			</div>
		</div>
		<div class="right">
			<h3>站内消息</h3>
			<div class="send-mail">
				<ul class="notice">
					<li>关于调整本市部分医疗服务价格</li>
					<li>春节期间住院部调查</li>
					<li>关于筹建“患者之声”广播电台的公告</li>
					<li>2016年管理部门的调整</li>
				</ul>
				<ul class="other">
					<li>*媒体报道*</li>
					<li>*学术活动*</li>
					<li>*招聘信息*</li>
					<li>*快速通道*</li>
					<li>*爱心专栏*</li>
					<li>*联系我们*</li>
				</ul>
			</div>

			<h3 style="margin:20px auto">登录</h3>
			<div class="sign-in">
				<form action="#" method="post">
					<input type="text" class="name" name="username" placeholder="您的姓名" required="">
					<input type="password" class="password" name="password" placeholder="密码" required="">
					<ul>
						<li>
							<input type="checkbox" id="brand1" value="">
							<label for="brand1"><span></span>Remember me</label>
						</li>
					</ul>
					<input type="submit" value="Sign In">
					<div class="clear"></div>
				</form>
			</div>
			<h3>注册</h3>
			<div class="register agileits">
				<form action="#" method="post">
					<input type="text" class="name" name="username" placeholder="您的姓名" required="">
					<input type="password" class="password" name="password" placeholder="密码" required="">
					<input type="password" class="password" name="confirm password" placeholder="确认密码" required="">
					<input type="text" class="email" name="email" placeholder="您的邮箱" required="">
					<input type="text" class="location" name="location" placeholder="您的地址" required="">
					<input type="submit" value="Sign Up">
				</form>
			</div>
		</div>
		<div class="clear"></div>
		
	</div>
	<div class="footer agile">
		<p> All Rights Reserved | Design by <a href="http://w3layouts.com">W3layouts</a></p>
	</div>
</body>
</html>
