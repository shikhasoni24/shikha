$(document).ready(function(){
    $("#zoomresponse").click(function(e){
        e.preventDefault();
         var store=$.parseJSON(localStorage.getItem("userdata"));
         var usermailid=store.emailId;

        $.ajax({
            type:"GET",
            datatype:"JSON",
            url:"/bin/data/ourzoommeeting",
            data:{mailid:usermailid},

            success:function(data){
                console.log("data" +data);

                var responsedata=$.parseJSON(data);
                var iszoomuser=responsedata.isZoomUser;
                 console.log(responsedata.invite);
                console.log(iszoomuser);

              /*  console.log(data.availableRooms);
				var ar=data.availableRooms;
                var str="<h1>Please book rooms from the available options</h1>";
                for(var i=0;i<ar.length;i++){
						str+="<input type='checkbox' id='"+ar[i].roomNo+"' value='"+ar[i].roomNo+"'> <label>"+ar[i].roomName+"</label><br>";
                }*/
                if(iszoomuser){
                var v=responsedata.invite.split("\r\n");
                    $("#zoomlink").html("<a style='color:red' href="+v[6]+" target='_blank'>"+v[6]+"</a><br>"+v[8]+"<br>"+v[9]);}
                else
                {
                    $("#zoomlink").html("<h4 style='color:red'> You are not a registered zoom user. Please contact admin to get the zoom link</h4>");
                }

            },
            error:function(e){
                console.log("error"+e);
            }            
        })
    });
    })