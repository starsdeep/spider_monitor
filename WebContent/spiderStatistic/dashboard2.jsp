<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>


<div class="container">
	<h2>Spider Monitor Dashboard</h2>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#panel_page">页面监控</a></li>
		<li><a data-toggle="tab" href="#panel_comment">评论监控</a></li>
		<li><a data-toggle="tab" href="#panel_rank">排行榜监控</a></li>

	</ul>



	<div class="tab-content" style="background-color: #FFF;">
	
		<div id="panel_page" class="tab-pane fade in active">
			<%@include file="page.jsp"%>
		</div>

		<div id="panel_comment" class="tab-pane fade">
			<%@include file="comment.jsp"%>

		</div>
		<div id="panel_rank" class="tab-pane fade">
			<%@include file="rank.jsp"%>
		</div>

	</div>
</div>




<%@include file="footer.jsp"%>