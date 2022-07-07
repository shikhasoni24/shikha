$(document).ready(function(){
    $("#downloadreport").click(function(e){debugger;
       e.preventDefault();
 var store=$.parseJSON(localStorage.getItem("userdata"));

var xhr = new XMLHttpRequest();
var data = "firstName=" + encodeURIComponent(store.userId)
        + "&lastName=" + encodeURIComponent(store.userName);
xhr.open("POST","/bin/hotel/DownloadPDF",true);
xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
xhr.responseType="blob";
xhr.send(data);
xhr.onload=function(e){
          var blob=xhr.response;
          console.log(blob);
          var link=document.createElement('a');
          link.href=window.URL.createObjectURL(blob);
          link.download="details.pdf";
 		  link.click();  
        }

    })

})