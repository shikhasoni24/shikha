$(document).ready(function(){
		 $("#signupsubmit").click(function(e)
                             
                   {debugger;
                       e.preventDefault();
                                 
                       var u=$("#username").val();
					var p=$("#signuppassword").val();
                    var m=$("#signupmailid").val();
                                 var paswd=  /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{7,15}$/;


//checking validation for all the fields simultaneously on clicking on signupsubmit//


                    if(u.length<6||!p.match(paswd))
                    {
                        
                        $("#signupmessage").html("");//to remove success method message
                        if(u.length<6){
                            if(p.match(paswd)) 
                                $("#passwordvalidation").remove(); //when only password validation is correct, remove passwordvalidation div
                            if($("#usernamevalidation").length) //to stop recreation of same div again and again
                                
                                $("#usernamevalidation").remove();
                            
                            
                            $("#username").after("<div id='usernamevalidation'></div>");
                            
                            $("#usernamevalidation").html("Username should contain more than or equal to 6 characters");						
                        }

                        if(!p.match(paswd)) {
                            if(u.length>=6)
                                $("#usernamevalidation").remove();//when only username validation is correct, remove usernamevalidation div
                            
                            if($("#passwordvalidation").length){//to stop recreation of same div again and again
                                $("#passwordvalidation").remove();
                                
                            }
                            $("#signuppassword").after("<div id='passwordvalidation'></div>");
                            
                            $("#passwordvalidation").html("The password should be between 6 to 20 characters which contain at least one numeric digit, one uppercase and one lowercase letter");					
                            
                            
                        }}
                    
                    else {
                        $("#usernamevalidation").remove();
                        $("#passwordvalidation").remove();

                        
                        $.ajax({
                            
                            data:{ susername:u,   //bhej rhe hai servlet me as a parameter through ajax call
                                  spassword:p,
                                  smailid:m
                                 },
                            type:"GET",
                            
                            url:"/bin/hotel/signup",
                            success:function(data){ //jo data aa rha hai backend se
                                
                                console.log("data" +data);
                                $("#signupmessage").html(data);
                                
                            },
                            error:function(e){
                                console.log("error"  +e);
                            }
                        });
                    } 
                   })
     
     
})