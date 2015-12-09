  
function loadPage(){
	$(document).ready(function(){
		var table_url = "http://localhost:8080/spider_monitor/table";
		
		var ajaxRenderDiv = function (table_div){
			table_div = $(table_div);
			$.getJSON(table_url, {startDay: table_div.attr("startDay").replace(/-/g,""), periodSize: table_div.attr("periodSize"), tableName: table_div.attr("tableName"), tableField: table_div.attr("tableField"), fieldNum: table_div.attr("fieldNum")}).done(function (data){
				console.log(table_url + " page success");
				// console.log("data is: " + data);
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
	
	console.log('generate column')
	for(var i in column){
		console.log(column[i]);
	}
	
	return column;
}


function generateTable(column, jsonData){
		console.log("generate Table");
		var data = jsonData['data'];
		var anomaly_list = jsonData['anomaly']
		var id_name_dicts = jsonData['id_name_dicts']
		var alignNum = Object.keys(id_name_dicts).length;
		
		var anomaly_set = new Set();
		
		console.log('anomaly set')
		console.log("number of anomaly set: " + Object.keys(anomaly_set).length);
		for(idx in anomaly_list){
			anomaly_set.add(anomaly_list[idx]);
			
		}
		
		
			
		// var div = $('#rankTable');
    	var table = $("<TABLE/>");
    	table.addClass('table table-striped');
    	table.css("width", "100%;overflow: auto;");
   
    	// header
    	var tableHeader = $("<THEAD/>")
    	table.append(tableHeader);
    	var tr = $("<TR/>");
    	tableHeader.append(tr);
    	for(var i=0;i<column.length;i++){
    		var th = $("<TH/>");
			th.append(column[i]);
			tr.append(th);
    	}
    	
    	// body
    	var text;
    	var tableBody = $("<TBODY/>");
    	table.append(tableBody);
    	for(var i=0;i<data.length;i++){
    		var tr = $("<TR/>");
    		// tr.addClass("success");
    		tableBody.append(tr);
    		
    		for(var j=0;j<column.length;j++){
    			var td = $("<TD/>");
    			var text = '0';
    			if(data[i].hasOwnProperty(column[j])){
    				if(data[i][column[j]])
    					text = data[i][column[j]];
    				
    			}
    			td.append(text);
    			tr.append(td);
    		}
    		
    		//translate first column
    		var id = tr.children().first().text();
    		var fields = id.split(":");
//    		console.log("fields:" + fields);
//    		for(var j in fields){
//    			console.log(j + " " + fields[j])
//    		}
    		
//    		if(alignNum>0){
//    			if(id_name_dicts)
//    				var new_text = "";
//    			for(var j in fields){
//    				var new_field = fields[j];
//    				if(id_name_dicts[j].hasOwnProperty(new_field))
//    					new_field = id_name_dicts[j][new_field];
//    				new_text += "\t" + new_field;
//    			}
//    			tr.children().first().text(new_text);
//    		}
    		
    		if(alignNum>0){
    			var new_text = "";
    			var new_field1 = fields[0];
    			
    			if(id_name_dicts[0].hasOwnProperty(new_field1))
    				new_field1 = id_name_dicts[0][new_field1];
    			new_text += new_field1;
    			var new_field2 = "";
    			if(alignNum>1){
    				new_field2 = fields[1];
    				if(id_name_dicts[1].hasOwnProperty(new_field2)){
    					new_field2 = id_name_dicts[1][new_field2];
    				}
    				new_field2 = "   (" + new_field2 + ")";
    			}
    			new_text += new_field2;
    			tr.children().first().text(new_text);	
    		}
    		
    			
    		
    		//console.log("id is:" + id);
//    		if(id_name_dicts.hasOwnProperty(id)){
//    			tr.children().first().text(id_name_dicts[id]);
//    		}
    		
    		//render last column
    		if(anomaly_set.has(id)){
    			text_rendered = render_alert(tr.children().last().text())
    			tr.children().last().html(text_rendered);
    		}
    		
    	}
    	return table;
    	// div.append(table);
}

function render_alert(text){
	return text.fontcolor("red");
}



loadPage();


