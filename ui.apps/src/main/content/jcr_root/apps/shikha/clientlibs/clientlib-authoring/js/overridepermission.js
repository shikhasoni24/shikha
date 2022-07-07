$(document).on("ready",function(e){
    e.preventDefault();
   // alert("hello");
    var pathname=String(window.location);
 //  let searchParams = new URLSearchParams(window.location.search);
  // let param = searchParams.get('item');
    var isRFGSelected=false;
	var regionList=[];
	//alert(pathname);
    	//alert(param);
	//if($("section[data-cls='overridePermission']").val()==""){
  	//alert("lino no 5");
	//if(!pathname.includes("editor")){

$.ajax({
    data:{
    pathname: pathname},
	type:"GET",
    datatype:"json",
    url:"/bin/data/custompermissions",
    success:function(data){
    var store=$.parseJSON(data);
        console.log(data);
        var userdatas=store.userdata;
        var rfgCompaniesIncludedList=store.rfgCompaniesIncludedList;
        var rfgCompaniesExcludedList=store.rfgCompaniesExcludedList;
        console.log("rfgCompaniesIncludedList" +rfgCompaniesIncludedList);
        console.log("rfgCompaniesExcludedList" +rfgCompaniesExcludedList);
		var isRFGSelected=store.isRFGSelected;
		$("section[data-cls='overridePermission']").append('<div class="coral-Form-fieldwrapper" id="regions" style="display: inline-block;">');
        var rfg = new Coral.Checkbox();
        rfg.id = 'rfg'
        rfg.label.innerHTML='<b>RFG</b>';
        rfg.value = 'rfg';
        rfg.name = 'regions';
        var rfgIncludeSearch = new Coral.Search();
        rfgIncludeSearch.id="rfgIncludeSearch";
        rfgIncludeSearch.name="rfgIncludeSearch";
        rfgIncludeSearch.placeholder="Search Company";
        rfgIncludeSearch.style.width="100%";
        var rfgExcludeSearch = new Coral.Search();
        rfgExcludeSearch.id="rfgExcludeSearch";
        rfgExcludeSearch.name="rfgExcludeSearch";
        rfgExcludeSearch.placeholder="Search Company";
        rfgExcludeSearch.style.width="100%";
        var rfgExcludeButton = new Coral.Button();
        rfgExcludeButton.id = 'rfgExcludeButton';
        rfgExcludeButton.label.innerHTML = '>';
        rfgExcludeButton.value = 'rfgExcludeButton';
        var rfgIncludeButton = new Coral.Button();
        rfgIncludeButton.id = 'rfgIncludeButton';
        rfgIncludeButton.label.innerHTML = '<';
        rfgIncludeButton.value = 'rfgIncludeButton';
        var rfgExcludeAllButton = new Coral.Button();
        rfgExcludeAllButton.id = 'rfgExcludeAllButton';
        rfgExcludeAllButton.label.innerHTML = '>>';
        rfgExcludeAllButton.value = 'rfgExcludeAllButton';
        var rfgIncludeAllButton = new Coral.Button();
        rfgIncludeAllButton.id = 'rfgIncludeAllButton';
        rfgIncludeAllButton.label.innerHTML = '<<';
        rfgIncludeAllButton.value = 'rfgIncludeAllButton';
        if(isRFGSelected) {
                            regionList.push(rfg.value);
							rfg.checked =  true;
						}
		$("#regions").append("<div id='rfgSection' style='display: inline-block;width:100%'>");
        $("#rfgSection").append(rfg);
        $("#rfgSection").append("<div id='rfgCompany'>");
		$("#rfgCompany").append("<div id='rfgCompanyInclude' style='display: inline-block;width:45%'>Included Companies");
        $("#rfgCompanyInclude").append("<div id='rfgInclude'>");
        $("#rfgInclude").append(rfgIncludeSearch);
        $("#rfgInclude").append('<div id="rfgCompanyIncludeSearch">');
        $("#rfgCompanyIncludeSearch").append('<coral-selectlist multiple id="rfgCompanyIncludeSelect" name="./includeSelect" style="height:170px">');
        $("#rfgCompany").append("<div id='rfgButton' style='display: inline-block;width:8%;padding-left:10px;vertical-align:bottom;align:middle'>");
        $("#rfgButton").append(rfgExcludeButton);
        $("#rfgButton").append(rfgIncludeButton);
        $("#rfgButton").append(rfgExcludeAllButton);
        $("#rfgButton").append(rfgIncludeAllButton);
        $("#rfgCompany").append("<div id='rfgCompanyExclude' style='display: inline-block;width:45%'>Excluded Companies");
        $("#rfgCompanyExclude").append("<div id='rfgExclude'>");
        $("#rfgExclude").append(rfgExcludeSearch);
        $("#rfgExclude").append('<div id="rfgCompanyExcludeSearch">');
		$("#rfgCompanyExcludeSearch").append('<coral-selectlist multiple id="rfgCompanyExcludeSelect" name="./excludeSelect" style="height:170px">');
        for(var i=0;i<userdatas.length;i++){ debugger;
            if(rfgCompaniesIncludedList.includes("all")){
             $("#rfgCompanyIncludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');

            }else if(rfgCompaniesExcludedList.includes("all")){
             $("#rfgCompanyExcludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');

            }
            else{
                if(rfgCompaniesExcludedList.length!=0){
             if(rfgCompaniesExcludedList.includes(userdatas[i].userId.toString())){
             $("#rfgCompanyExcludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');

            }
           else {
             $("#rfgCompanyIncludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');

           }}
                if(rfgCompaniesIncludedList.length!=0){
             if(rfgCompaniesIncludedList.includes(userdatas[i].userId.toString())){
             $("#rfgCompanyIncludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');
				}
           else {
             $("#rfgCompanyExcludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');
			 }}}}
	   $("#rfgExcludeButton").on("click", function() {
							var selectlist = document.querySelector('#rfgCompanyIncludeSelect');
							var selectedItems = selectlist.selectedItems;
							selectedItems.forEach(function(item) {
							item.selected = false;
							$("#rfgCompanyExcludeSelect").append(item.content);
							});
				            });
		$("#rfgIncludeButton").on("click", function() {
							var selectlist = document.querySelector('#rfgCompanyExcludeSelect');
							var selectedItems = selectlist.selectedItems;
							selectedItems.forEach(function(item) {
							item.selected = false;
							$("#rfgCompanyIncludeSelect").append(item.content);
							});
						});
		$("#rfgExcludeAllButton").on("click", function() {
							document.querySelector('#rfgCompanyExcludeSelect').items.clear();
    for (var i = 0; i < userdatas.length; i++) {
        $("#rfgCompanyExcludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');
    }
    document.querySelector('#rfgCompanyIncludeSelect').items.clear();
});

        $("#rfgIncludeAllButton").on("click", function() {
							document.querySelector('#rfgCompanyIncludeSelect').items.clear();
    for (var i = 0; i < userdatas.length; i++) {
        $("#rfgCompanyIncludeSelect").append('<coral-selectlist-item value="'+userdatas[i].userId+'">'+userdatas[i].emailId+'</coral-selectlist-item>');
    }
    document.querySelector('#rfgCompanyExcludeSelect').items.clear();
});
        $("#rfgIncludeSearch").on("keyup", function() {
							var value = document.querySelector('[name="rfgIncludeSearch"]').value;
							if(value.length == 0) {
								var selectlist = document.querySelector('#rfgCompanyIncludeSelect');
								var selectedItems = selectlist.items.getAll();
								selectedItems.forEach(function(item) {
									item.style.display = "block";
									item.selected = false;
								});
							}
            if(value.length >= 2) {
								var selectlist = document.querySelector('#rfgCompanyIncludeSelect');
								var selectedItems = selectlist.items.getAll();
								selectedItems.forEach(function(item) {
									if (item.textContent.toLowerCase().includes(value.toLowerCase())) {
										item.style.display = "block";
									} else {
										item.style.display = "none";
									}
									item.selected = false;
								});
							}
						});
        $("#rfgIncludeSearch button").on("click",function(){
								var selectlist = document.querySelector('#rfgCompanyIncludeSelect');
								var selectedItems = selectlist.items.getAll();
								selectedItems.forEach(function(item) {
									item.style.display = "block";
									item.selected = false;
								});
                         });
        $("#rfgExcludeSearch").on("keyup", function() {
							var value = document.querySelector('[name="rfgExcludeSearch"]').value;
							if(value.length == 0) {
								var selectlist = document.querySelector('#rfgCompanyExcludeSelect');
								var selectedItems = selectlist.items.getAll();
								selectedItems.forEach(function(item) {
									item.style.display = "block";
									item.selected = false;
								});
							}
			if(value.length >= 2) {
								var selectlist = document.querySelector('#rfgCompanyExcludeSelect');
								var selectedItems = selectlist.items.getAll();
								selectedItems.forEach(function(item) {
									if (item.textContent.toLowerCase().includes(value.toLowerCase())) {
										item.style.display = "block";
									} 
                                    else 
                                    {
										item.style.display = "none";
									}
									item.selected = false;
								});
							}
						});
        $("#rfgExcludeSearch button").on("click",function(){
								var selectlist = document.querySelector('#rfgCompanyExcludeSelect');
								var selectedItems = selectlist.items.getAll();
								selectedItems.forEach(function(item) {
									item.style.display = "block";
									item.selected = false;
								});
					 });

        $("#rfg").on("change", function() {debugger;
            var selected = document.querySelector('#rfg').checked;
            var includeSearch = document.querySelector('#rfgIncludeSearch');
                                           if(selected){
                                              regionList.push(rfg.value);}
                                               else
                                               {
                                               regionList.splice(regionList.indexOf(rfg.value),1);
                                           	   }
        	if(selected) {
							   includeSearch.disabled = false;
							 } else {
							   includeSearch.disabled = true;

							 }
             var includeSelectList = document.querySelector('#rfgCompanyIncludeSelect');
							 var includeSelectItems = includeSelectList.items.getAll();
							 includeSelectItems.forEach(function(item) {
								 if(selected) {
									item.disabled = false;
								 } else {
									item.disabled = true;
								 }
							 });
            var excludeSearch = document.querySelector('#rfgExcludeSearch');
							 if(selected) {
								 excludeSearch.disabled = false;
							 } else {
								 excludeSearch.disabled = true;
							 }
            var excludeSelectList = document.querySelector('#rfgCompanyExcludeSelect');
							 var excludeSelectItems = excludeSelectList.items.getAll();
							 excludeSelectItems.forEach(function(item) {
								 if(selected) {
									item.disabled = false;
								 } else {
									 item.disabled = true;
								 }
							 });
             var includeButton = document.querySelector('#rfgIncludeButton');
                             if(selected) {
                             	includeButton.disabled = false;
                             } else {
                                includeButton.disabled = true;
                             }
                             var excludeButton = document.querySelector('#rfgExcludeButton');
                             if(selected) {
                             	excludeButton.disabled = false;
                             } else {
                                excludeButton.disabled = true;
                             }
            var includeAllButton = document.querySelector('#rfgIncludeAllButton');
                             if(selected) {
                             	includeAllButton.disabled = false;
                             } else {
                                includeAllButton.disabled = true;
                             }
            var excludeAllButton = document.querySelector('#rfgExcludeAllButton');
                             if(selected) {
                             	excludeAllButton.disabled = false;
                             } else {
                                excludeAllButton.disabled = true;
                             }
})
        	$("#shell-propertiespage-doneactivator").on("click", function(e) { debugger;//using shell-propertiespage becasue of multiple time save problem
                   //appending here because of preopoulating issue

                $("section[data-cls='overridePermission']").append('<input type="hidden" value="" data-validation="" aria-invalid="false"  name="./rfgCompaniesIncludedList">');
					   $("section[data-cls='overridePermission']").append('<input type="hidden" value="" data-validation="" aria-invalid="false"  name="./rfgCompaniesExcludedList">');
				$("section[data-cls='overridePermission']").append('<input type="hidden" value="" data-validation="" aria-invalid="false"  name="./regions">');
 if(regionList.length==0)
                    $("input[name='./regions']").val("AllUnchecked");
                    else
                    $("input[name='./regions']").val(regionList);
                var rfgselected = document.querySelector('#rfg').checked;
                if(rfgselected){
							var includeSelectList = document.querySelector('#rfgCompanyIncludeSelect');
							var includeSelectItems = includeSelectList.items.getAll();
							var rfgIncludedList=[];
							var rfgExcludedList=[];
							includeSelectItems.forEach(function(item) {
								rfgIncludedList.push(item.value);
                            });
                    var excludeSelectList = document.querySelector('#rfgCompanyExcludeSelect');
							var excludeSelectItems = excludeSelectList.items.getAll();
							excludeSelectItems.forEach(function(item) {
								rfgExcludedList.push(item.value);
							});

                    if(rfgIncludedList.length < rfgExcludedList.length){
					if(rfgIncludedList.length==0){
									$("input[name='./rfgCompaniesIncludedList']").val("");
									$("input[name='./rfgCompaniesExcludedList']").val("ALL");
							   } 
                        else 
                       		   {
									$("input[name='./rfgCompaniesIncludedList']").val(rfgIncludedList);
									$("input[name='./rfgCompaniesExcludedList']").val("");
                               }

                    }
                    else 
                    			{
								if(rfgExcludedList.length==0)
                                {
									$("input[name='./rfgCompaniesExcludedList']").val("");
									$("input[name='./rfgCompaniesIncludedList']").val("ALL");
                                } 
                        	else 
                            	{
									$("input[name='./rfgCompaniesExcludedList']").val(rfgExcludedList);
									$("input[name='./rfgCompaniesIncludedList']").val("");
								}
                            }
					  }
                else {
						$("input[name='./rfgCompaniesExcludedList']").val("");
						$("input[name='./rfgCompaniesIncludedList']").val("");
                    }
							});

					},
    error:function(e)
    {
        console.log(e);
		}
	})
	//}
	//}         
});
