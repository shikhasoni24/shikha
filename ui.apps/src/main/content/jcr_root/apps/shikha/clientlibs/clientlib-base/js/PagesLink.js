$(document).ready(function(){
   $("#hotel").on("change",function(e){ debugger;
		e.preventDefault();
        var selected=$(this).val();
        alert(selected);
        $.ajax({
            url:"/bin/shikha/searchpage",
            datatype:"json",
            type:"GET",
            data:{selectedvalue:selected},
            success:function(data){
                var v=JSON.parse(data);
                var x=JSON.parse(v.pagepath);
				console.log(x);
                console.log(v);
                var s="";
                for(var i=0;i<x.length;i++){
				     s+="<a href="+x[i]+">view</a>";
                    }
                $("#hotelareares").html(s);

            },
            error:function(e){
			console.log(e);
            }
        })
    })
})