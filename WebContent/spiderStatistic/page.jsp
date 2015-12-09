<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="row" style="margin:auto">
		<div class="col-lg-9" style="margin:auto;">

			<div class="panel panel-default" style="margin-top: 30px">
				<div class="panel-heading">
					每个ip对应的页面数<span name="panel_toggle" class="caret" style="float: right"></span>

				</div>

				<div id="page_ip" name="table_div" tableName="page" tableField="ip" startDay="2015-10-12"
					periodSize=7 fieldNum=1></div>

			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					每个数据源对应的页面数<span name="panel_toggle" class="caret" style="float: right"></span>
				</div>

				<div id="page_source" name="table_div" tableName="page" tableField="source_id"
					startDay="2015-10-12" periodSize=7 fieldNum=1></div>

			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					每个游戏对应的页面数 <span name="panel_toggle" class="caret" style="float: right"></span>
					</button>
				</div>

				
					
				<div id="page_game" style="display: none;" name="table_div" tableName="page"
					tableField="game_id, source_id" startDay="2015-10-12" periodSize=7 fieldNum=2></div>	
					
			</div>
		</div>
	</div>
