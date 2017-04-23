
$(function(){
	$.getJSON("/table" , function(jd){
		$.each(jd , function (i , field){
				appendAllTableColumn($('table') , field);
		});
	});
})

$(function() {
	$('button').click( function  (){
		if ( $('#todoTextarea').val() === ''  ){
			alert('you must determine your task');
		}else {

			var text = $('#todoTextarea').val();
			$('#todoTextarea').val("");
			
			$.get('/submitingNewTodo?todoValue=' + text,
				function(data , status){
			
					appendTableColumn($('table'), data);

				})
		}

	})

})

function appendTableColumn(table, rowData) {

	var obj = JSON.parse(rowData);
  	var lastRow = $('<tr/>').appendTo(table.find('tbody'));
    lastRow.append($('<td/>').text(obj.TODO_ID));
    lastRow.append($('<td/>').text(obj.Todo));
    lastRow.append($('<td/>').text(obj.IsDone));
    lastRow.attr("id", "dataCol");
    lastRow.attr("onClick", "markAsDone(" + obj.TODO_ID+ ")");

  
   
  return lastRow;
}
function appendAllTableColumn ( table, rowData){
  	var lastRow = $('<tr/>').appendTo(table.find('tbody'));
  	var id = rowData.TODO_ID;
     lastRow.append($('<td/>').text(rowData.TODO_ID));
     lastRow.append($('<td/>').text(rowData.Todo));
     lastRow.append($('<td/>').text(rowData.IsDone));
     if ( rowData.IsDone === "1")
     {
     	var val1 =$("table").find('tr:eq(' + 
			rowData.TODO_ID + ")");
     	val1.css("background-color", "#7FFF00");
     }
     lastRow.attr("id", "dataCol");
     lastRow.attr("onClick", "markAsDone(" + id + ")");

     return lastRow;


}
function markAsDone (TODO_ID) {
	
	var val1 =$("table").find('tr:eq(' + 
		 TODO_ID + ")");
	
	$.get("/markasdone?id=" + TODO_ID,function(data , status){
			if ( status === "success"){
				   val1.css("background-color", "#7FFF00");
				}


	
	} );




}
