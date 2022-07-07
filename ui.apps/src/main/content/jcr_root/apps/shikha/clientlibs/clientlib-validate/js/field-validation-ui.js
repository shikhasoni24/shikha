$(document).on("foundation-contentloaded", function (e) { debugger;
    var $dialog = $(e.target);
    if ($dialog && $dialog.length === 1) {
        var pagename = $dialog.find("[data-validation=mandatory-text]");
        if (pagename) {
            var api = $(pagename).adaptTo("foundation-validation");
            if (api) {
                api.checkValidity();
                api.updateUI();
            }
        }
    }
});
