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
		$('#btnmemberregister').on('click', function () {
            _this.mregister();
        });
		$('#btnmetainsert').on('click', function () {
            _this.minsert();
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
			location.href="/failurl?"+data;
        });
    },mregister : function () {
    	console.log("member-register activated");
		var data={
			id:$('#id').val(),
			password:$('#password').val(),
			name:$('#name').val(),
			team:$('#team').val()
			}
		console.log(data);
		//location.href=url;
		var url="/insertMember";
		$.ajax({
            type:'POST',
            url:url,
            dataType:"text",
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(data),
        })
        .done(function() {
        	console.log("mregister complete");
            //location.reload();
			alert("Welcome!");
			location.href="./";
        })
        .fail(function (error) {
        	console.log("mregister fail");
        	console.log(JSON.stringify(error));
			alert("something wrong... contact -> 01024299420")
        });
    },minsert : function () {
    	console.log("meta-insert activated");
		var data={
			id:$('#id').val(),
			name_eng:$('#name_eng').val(),
			name_kor:$('#name_kor').val(),
			explanation:$('#explanation').val()
			}
		console.log(data);
		//location.href=url;
		var url="/insertMeta";
		$.ajax({
            type:'POST',
            url:url,
            dataType:"text",
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(data),
        })
        .done(function() {
        	console.log("minsert complete");
            //location.reload();
			alert("register meta data!");
			location.href="./";
        })
        .fail(function (error) {
        	console.log("register meta data fail");
        	console.log(JSON.stringify(error));
			alert("something wrong... contact -> 01024299420")
        });
    }

};


main.init();
