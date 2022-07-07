 $(window).load(function() {debugger;
 // executes when complete page is fully loaded, including all frames, objects and images
  var store=$.parseJSON(localStorage.getItem("userdata"));
 // var d=store.userId;
    if(store==null){
        var s="window.location.href='http://localhost:4502/content/shikha/welcome.html'";
			$("#redirection").append("<button onclick="+s+">login</button>");
        $("#logoutsubmit").hide();
         $("#seeBookedRooms").hide();
         $("#downloadreport").hide();
         $("#downloadexcelreport").hide();
         $("#cancelroomsubmit").hide();
    }
    else
    {
$("#redirection").append("Welcome "+store.userName);
}
}); 