$(document).ready(function(){
    $("#bookroomsubmit").click(function(e){
        e.preventDefault();
        $.ajax({
            type:"GET",
            datatype:"JSON",
            url:"/bin/hotel/SeeAllRoom",
            
            success:function(data){
                console.log("data" +data);
                console.log(data.availableRooms);
				var ar=data.availableRooms;
                var str="<h1>Please book rooms from the available options</h1>";
                for(var i=0;i<ar.length;i++){
						str+="<input type='checkbox' id='"+ar[i].roomNo+"' value='"+ar[i].roomNo+"'> <label>"+ar[i].roomName+"</label><br>";
                }
				$("#bookroom").html(str);
                $("#confirmroom").css("display","block");
            },
            error:function(e){
                console.log("error"+e);
            }            
        })
    });
    

$("#confirmroom").click(function(e){debugger;
                                        e.preventDefault();
                                        var selectedroom=[];
                                        var store=$.parseJSON(localStorage.getItem("userdata"));

                                      if(store==null)
                                        {
 											$('#bookroom input:checked').each(function () {
                                            selectedroom.push($(this).attr('id'));
										})

                           sessionStorage.setItem("selectedRooms",selectedroom);

                                            window.location.href="http://localhost:4502/content/shikha/welcome.html";

                                        }
                                        else{
                                        var d=store.userId;
										console.log(d); 

                                        $('#bookroom input:checked').each(function () {
                                            selectedroom.push($(this).attr('id'));
                                        })
                                        console.log(selectedroom);
                                        $.ajax({
                                            data:{
                                                selectedrooms:selectedroom,
                                                loggedInUserId:d
                                            },
                                            type:"GET",
                                            url:"/bin/hotel/bookRoom",
                                            success:function(data){
                                                
                                                $("#bookedRoomsResult").html("<p>Congratulations "+selectedroom.length+" rooms have been booked.</p>");
                                            },
                                            error:function(e){
                                                console.log("error"  +e);
                                            }
                                        })}
                                        }) 
})