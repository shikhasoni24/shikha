$(document).ready(function(){
    $("#seeBookedRooms").click(function(e){
        e.preventDefault();


         var store=$.parseJSON(localStorage.getItem("userdata"));//because localstorage stores everything as a string (we have set userstorage in login.js) and this file we want it as a json so we have used parsejson
                                        var d=store.userId;
                                        console.log(d); 

        $.ajax({

            data:{d:d},
            url:"/bin/hotel/seeUserBookedRooms",
            type:"GET",
            datatype:"json",
            success:function(data){
                console.log(data.room);
                 var s1="";
                for(var j=0;j<data.room.length;j++){
                    
					s1+="<h3>"+data.room[j].roomName+"</h3>";

                }
                $("#viewbookedrooms").html(s1);

            },
            error:function(e){
			console.log("error is "+e);
            }

    })
})
})