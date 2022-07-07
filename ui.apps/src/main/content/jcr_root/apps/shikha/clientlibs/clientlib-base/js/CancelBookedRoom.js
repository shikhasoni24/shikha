
$(document).ready(function(){
    $("#cancelroomsubmit").click(function(e){
        e.preventDefault();


         var store=$.parseJSON(localStorage.getItem("userdata"));
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

				s1+="<input type='checkbox' id='"+data.room[j].roomNo+"' value='"+data.room[j].roomNo+"'> <label>"+data.room[j].roomName+"</label><br>";

                }
                $("#cancelroom").html(s1);
                  $("#cancelbookedroom").css("display","block");
            },


            error:function(e){
			console.log("error is "+e);
            }

    })
})

     $("#cancelbookedroom").click(function(e){debugger;
                                        e.preventDefault();
                                        var store=$.parseJSON(localStorage.getItem("userdata"));
                                        var d=store.userId;
                                        console.log(d); 
                                        var selectedroom=[];
                                        $('#cancelroom input:checked').each(function () {
                                            selectedroom.push($(this).attr('id'));
                                        })
                                        console.log(selectedroom);
                                        $.ajax({
                                            data:{
                                                selectedrooms:selectedroom,
                                                loggedInUserId:d
                                            },
                                            type:"GET",
                                            url:"/bin/hotel/cancelroom",
                                            success:function(data){
                                                
                                                $("#cancelRoomsResult").html("<p>Congratulations "+selectedroom.length+" rooms have been cancelled.</p>");
                                            },
                                            error:function(e){
                                                console.log("error"  +e);
                                            }
                                        })
                                       })
})