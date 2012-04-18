$(function() {
	
	//	http://quasipartikel.at/multiselect/
	$("select[multiple=yes]").multiselect();
	
	//	http://trentrichardson.com/examples/timepicker/
	$(".crud_date input").datetimepicker({
		"dateFormat": "yy-mm-dd"
	});
	$("input.date").datepicker({
		"dateFormat": "yy-mm-dd"
	});
	
	$("datagrid input").bind("keydown", function(event) {
		var keycode = (event.keyCode ? event.keyCode : (event.which ? event.which : event.charCode));
		if (keycode == 13) {
			$("#crudSearchSubmit").click();
			return false;
		} else
			return true;
	});
	
	$(".confirm").live("click", function (event) {
		var message = $(this).attr("data-confirm");
		if(message == undefined)
			message = "Delete this record?";
		var e = jQuery.Event(event);
		if (!confirm(message)) {
			e.stopImmediatePropagation();
			e.preventDefault();
		}
	});	
	
	$('#crudListTable input').keyup(function(e) {
		if(e.keyCode == 13) {
			$('#crudSearchSubmit').click();
		}
	});	
	$('#crudListTable select').change(function(e) {
		$('#crudSearchSubmit').click();
	});	
});