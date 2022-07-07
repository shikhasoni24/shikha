$(document).ready(function(){
     var store=$.parseJSON(localStorage.getItem("userdata"));
    $("#submitUserProfile").on("click",function(e){debugger;
			e.preventDefault();

             var s1="<br><label> User Id </label>"+store.userId+"<br>";
             s1+="<label> User Name </label>"+store.userName+"<br>";
             s1+="<button id='changepassword'> Change Password </button><br>";
             s1+="<div id='appendpassword'> </div><br>";
             $("#userprofile").html(s1);                                  

    })

    $("#userprofile").on("click","#changepassword",function(e){
	e.preventDefault();
			var s2="<br><label> Old Password </label> <input type='text' id='oldpassword'><br>";
        s2+="<label>Enter New Password </label> <input type='text' id='newpassword'><br>";
          s2+="<label>Enter New Password Again </label> <input type='text' id='newpasswordagain'><br>";
         s2+="<button id='confirmchangepassword'> Submit </button><br>";
			$("#appendpassword").html(s2);

    })

       $("#userprofile").on("click","#appendpassword #confirmchangepassword",function(e){ debugger;
          	e.preventDefault();
 			var usernamevariable=store.userName;
           var enteroldpassword=$("#oldpassword").val();
           var enternewpassword=$("#newpassword").val();
		 	var enternewpasswordagain=$("#newpasswordagain").val();
           if(enternewpassword==enternewpasswordagain){
		$.ajax({
            data:{
                  unv:usernamevariable,
                  eop:enteroldpassword,
                  enp:enternewpassword

            },
            datatype:"",
            type:"GET",
            url:"/bin/hotel/changepassword",
            success:function(data){
			alert("Your password has been changed successfully");

            },
            error:function(e){
			console.log("error "+e);
        	}
      		  })}
           else{
				alert("New password is not matching with new password again");
           }


       })})