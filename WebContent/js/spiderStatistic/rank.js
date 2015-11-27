  
function loadPage(){
	$(document).ready(function(){
		var table_url = "http://localhost:8080/spider_monitor/table";
		
		var ajaxRenderDiv = function (table_div){
			table_div = $(table_div);
			$.getJSON(table_url, {startDay: table_div.attr("startDay").replace(/-/g,""), periodSize: table_div.attr("periodSize"), tableName: table_div.attr("tableName"), tableField: table_div.attr("tableField")}).done(function (data){
				console.log(table_url + " page success");
				console.log("data is: " + data);
				var column = generateColumn(table_div.attr("tableField"), table_div.attr("startDay"), table_div.attr("periodSize"));
				var table = generateTable(column, data);
				console.log("after generate table, " + table.attr("border"));
				table_div.append(table);
			})
		}		


		
		var divs = $('.table_div');
		divs.each(ajaxRenderDiv);
		
		function renderDivs(){
			var divs = $('.table_div');
			var divs = $("[name='table_div']");
			for(var i=0;i<divs.size();i++){
				ajaxRenderDiv(divs[i]);
			}
		}
		
		renderDivs();
		
		var eles = $("[name='panel_toggle']");
		
		for(var i=0;i<eles.size();i++){
			var ele = $(eles[i]);
			ele.on('click', (function(element_to_toggle){
				return function(){
					element_to_toggle.toggle();
				}
			})(ele.parent().next()));
		}
		
		$('#myTabs a').click(function (e) {
			  e.preventDefault()
			  $(this).tab('show')
			})
		
//		eles.on('click', (function(element_to_toggle){
//			return function(){
//				element_to_toggle.toggle();
//			}
//		})(eles.parent().next()));
		
	});
}


function generateColumn(tableField, startDay, periodSize){
	var column = [];
	column.push(tableField);
	for(i=-periodSize;i<=0;i++){
		var theDate = new Date(startDay);
		theDate.setDate(theDate.getDate() + i);
		column.push(theDate.toISOString().slice(0,10).replace(/-/g,""));
	}
	
	for(var i in column){
		console.log(column[i]);
	}
	
	return column;
}


function generateTable(column, data){
		console.log("generate Table");
//		var div = $('#rankTable');
    	var table = $("<TABLE/>");
    	table.addClass('table table-striped');
//    	table.css("width", "auto");
   
    	
    	
    	//header
    	var tableHeader = $("<THEAD/>")
    	table.append(tableHeader);
    	var tr = $("<TR/>");
    	tableHeader.append(tr);
    	for(var i=0;i<column.length;i++){
    		var th = $("<TH/>");
			th.append(column[i]);
			tr.append(th);
    	}
    	
    	
    	//body
    	var text;
    	var tableBody = $("<TBODY/>");
    	table.append(tableBody);
    	for(var i=0;i<data.length;i++){
    		var tr = $("<TR/>");
//    		tr.addClass("success");
    		tableBody.append(tr);
    		for(var j=0;j<column.length;j++){
    			var td = $("<TD/>");
    			
    			if(data[i].hasOwnProperty(column[j]))
    				
    				text = data[i][column[j]];
    			
    			else
    				text = 0;
    			td.append(text);
    			tr.append(td);
    		}
    	}
    	return table;
    	// div.append(table);
}





loadPage();


