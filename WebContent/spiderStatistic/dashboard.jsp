<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<%-- <%-- <%@include file="header.jsp"%>


	<div id="content">

		<%@include file="nav.jsp"%>
		<div class="container">
			<div class="row">
				<div class="col-lg-8">
					<div class="panel panel-default" style="margin-top:30px">
						<div class="panel-heading">
							每个ip对应的页面数<span name="panel_toggle" class="caret" style="float: right"></span>

						</div>

						<div id="page_ip" name="table_div" tableName="page" tableField="ip" startDay="2015-10-12"
							periodSize=7 margin=></div>

					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							每个数据源对应的页面数<span name="panel_toggle" class="caret" style="float: right"></span>
						</div>

						<div id="page_source" name="table_div" tableName="page" tableField="source_id"
							startDay="2015-10-12" periodSize=7></div>

					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							每个游戏对应的页面数 <span name="panel_toggle" class="caret" style="float: right"></span>
							</button>
						</div>

						<div id="page_game" style="display: none;" name="table_div" tableName="page"
							tableField="game_id" startDay="2015-10-12" periodSize=7></div>

					</div>
				</div>

			</div>
		</div>
	</div>

	<%@include file="footer.jsp"%> --%> 







	
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Bootstrap Case</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <h2>Spider Monitor Dashboard</h2>
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="dashboard.jsp">页面监控</a></li>
    <li><a data-toggle="tab" href="comment.jsp">评论监控</a></li>
    <li><a data-toggle="tab" href="rank.jsp">排行榜监控</a></li>
    
  </ul>
  
  

  <div class="tab-content">
    <div id="home" class="tab-pane fade in active">
      <h3>HOME</h3>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
    </div>
    <div id="menu1" class="tab-pane fade">
      <h3>Menu 1</h3>
      <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
    </div>
    <div id="menu2" class="tab-pane fade">
      <h3>Menu 2</h3>
      <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
    </div>
    <div id="menu3" class="tab-pane fade">
      <h3>Menu 3</h3>
      <p>Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
    </div>
  </div>
</div>

</body>
</html>
	