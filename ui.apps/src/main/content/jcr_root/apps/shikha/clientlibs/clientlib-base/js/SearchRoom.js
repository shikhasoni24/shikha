$(document).ready(function(){
    $("#searchroom").on('click',function(e){debugger;

		e.preventDefault();
                                            var cname= $("#cityname").val();
        $.ajax({
            data:{city:cname},
            url:"/bin/hotel/searchRoom",
            type:"GET",
            datatype:"JSON",
            success:function(data){
                var room=data.availableRooms;
                                console.log(room);
				                console.log(data.availableRooms);

                for(var i=0;i<room.length;i++){


						                console.log(room[i]);

					$("#roomsearchresult").append(room[i].roomName);
                }

            },
            error:function(e){
				console.log(e);
            }

        })


    })



})