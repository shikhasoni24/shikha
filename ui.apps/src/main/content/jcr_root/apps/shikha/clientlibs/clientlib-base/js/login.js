$(document).ready(function(){
	var otp="";
    $("#getotp").click(function(e){debugger;
  		e.preventDefault();
			//var mailid=$("#emailfield").val();
         var u=$("#loginusername").val();
         var p=$("#loginpassword").val();
              $.ajax({
                  data:{loginuserid:u,loginpassword:p},
                  type:"GET",
                  url:"/bin/hotel/matchotp",
                  datatype:"json",
                  success:function(data){
					  console.log("data"  +data.otp);
                      otp=data.otp;
                      $(".otpmatch").css("display","block");
                      $("#loginsubmit").css("display","block");
              },
              error:function(e){
              console.log("error"  +e);}
        })
    })

$("#loginsubmit").click(function(e){debugger;
            e.preventDefault();
var u=$("#loginusername").val();
         var p=$("#loginpassword").val();
         var userotp=$("#enterotp").val();
         var userselectedrooms=sessionStorage.getItem("selectedRooms");
                                        console.log(typeof userselectedrooms);

            if(userotp!=otp){
		        alert("Invalid otp");
                           }
                  else{
          $.ajax({

        data:{ lusername:u,
              lpassword:p,
              userselrooms:userselectedrooms
             },
        type:"GET",
              datatype:"json",
        url:"/bin/hotel/login",
        success:function(data){
            var a=data.user; //json format
            var b=data.resultingpage;  
            localStorage.clear();

          localStorage.setItem("userdata", a);//localstorage stores everything as a string in key value pair

			console.log("a is "+a);
            console.log("b is "+b);
            console.log(typeof data);
			console.log("data" +data);

            if(!b.includes("localhost"))
            $("#loginmessage").html(data);
            else{

                window.location.href = b; 
              alert("<p>"+data.message+"</p>");}//both 24 and 25 works similarly

            //location.replace(data);

            sessionStorage.clear();

        },
        error:function(e){
              console.log("error"  +e);
            $("#loginmessage").html(e);
        }
          });}

    })

    $("#forgotpassword").click(function(e){debugger;
        e.preventDefault();
        var s1="<br><h1>Reset Your Password</h1><label> Enter Username </label> <input type='text' id='forgotusername'> <br><br><label> Enter new Password </label>"+ 
        "<input type='text' id='forgotnewpassword'> <br><br> <button id='resetpassword'> Reset Password</button>";
                    $("#loginmessage").html(s1);
     $("#forgotusername").after("<div id='forgotusernamevalidation'></div>");
     $("#forgotnewpassword").after("<div id='forgotpasswordvalidation'></div>");
})


    $("#loginmessage").on("click","#resetpassword",function(e){debugger; //button created dynamically so to perform some event on that button, first we have to
        //mention its div then "click" then "button id",if we will directly perform click event on that button without mentioning div id then it won't work
	   e.preventDefault();
       var fusername=$("#forgotusername").val();
       var fpassword=$("#forgotnewpassword").val();
       var paswd=/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{7,15}$/;
                                 if(fusername.length<6)
                                 {
									 $("#forgotusernamevalidation").html("Username should contain more than or equal to 6 characters");						
                                 }
										if(!fpassword.match(paswd)) {
                                     $("#forgotpasswordvalidation").html("<div>The password should be between 6 to 20 characters which contain at least one numeric digit, one uppercase and one lowercase letter</div>");                                 }
                       else {
        $.ajax({
			data:{fusername:fusername,
                  fpassword:fpassword},
            type:"GET",
            url:"/bin/hotel/forgotpassword",
              success:function(data){
              console.log("data" +data);
				$("#loginmessage").html(data);

            },
            error:function(e){
                console.log("error",e);
                alert(e);
            }  
        })}
    })
})