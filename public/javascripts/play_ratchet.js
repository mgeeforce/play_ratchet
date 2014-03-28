var goBack = function () {
	window.history.back();
}
		
var deleteDetail = function (id) {
	console.log('deleteDetail called for id '+id);
	var url =  '/api/v1/details/'+id;
	var success_url = jsRoutes.controllers.Parents.getParent(id).url;
	console.log(success_url);
	$.ajax({
		type: 'DELETE',
		url: url,
		success: function(data) {
			console.log("in success");
			location.reload(true);
		}
	})
}
