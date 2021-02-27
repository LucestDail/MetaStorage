/**
 * 
 */


main = {
    init : function () {
    	console.log("init activated");
        var _this = this;
        $('#btnmetasearch').on('click', function () {
            _this.msearch();
        });
    },
    msearch : function () {
    	console.log("meta-search activated");
		var data="id="+$('#searchtarget').val()
		//location.href=url;
		var url="/getMetaDetail";
		$.ajax({
            type: 'GET',
            url:url,
            dataType: "text",
            contentType:'application/json; charset=utf-8',
            data:data,
        })
        .done(function() {
        	console.log("msearch complete");
            //location.reload();
			url += "?"+data;
			location.href=url;
        })
        .fail(function (error) {
        	console.log("msearch fail");
        	console.log(JSON.stringify(error));
			location.href="/failurl";
        });
    }

};


main.init();
