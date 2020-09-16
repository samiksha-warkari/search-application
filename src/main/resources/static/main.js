$(document).ready(function() {
	$("#search-form").submit(function(event) {
		// stop submit the form, we will post it manually.
		event.preventDefault();
		var input = $("#input-text").val();
		if (input == "") {
			alert("Please enter text")
		} else {
			search();
		}

	});
});

function search() {
	var input = $("#input-text").val();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "http://localhost:8080/idexx/search?text=" + input,
		dataType : 'json',
		success : function(data) {
			console.log(" Data :: " + data);
			if (data === undefined || data === null) {
				alert("Data not found");
			} else {
				var json = JSON.parse(JSON.stringify(data));
				$("#data tr").remove();
				json.forEach(function(object) {
					var authors = "-";
					if (object.authors != null) {
						authors = object.authors;
					}
					$("#data").append(
							"<tr>" + "<td>" + object.title + "</td>" + "<td>"
									+ authors + "</td>" + "<td>" + object.kind
									+ "</td>" + +"</tr>")
				});
			}

		},
		error : function(e) {
			var json = JSON.parse(e.responseText);
			$('#feedback').html(json.message);

			console.log("ERROR : ", e);

		}
	});

}