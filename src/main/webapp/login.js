require(["dojo/dom", "dojo/on", "dojo/request", "dojo/dom-form"],
    function(dom, on, request, domForm){
        // Results will be displayed in resultDiv

        var form = dom.byId('formNode');

        // Attach the onsubmit event handler of the form
        on(form, "submit", function(evt){

            // prevent the page from navigating after submit
            evt.stopPropagation();
            evt.preventDefault();

            // Post the data to the server
            var promise = request.post("login", {
                // Send the username and password
                data: domForm.toObject("formNode"),
                // Wait 2 seconds for a response
                timeout: 2000
            });

            // Use promise.response.then, NOT promise.then
            promise.response.then(function(response){

                // get the message from the data property
                var message = response.data;

                // Access the 'Auth-Token' header
                var token = response.getHeader('Auth-Token');

                dom.byId('svrMessage').innerHTML = message;
                dom.byId('svrToken').innerHTML = token;
            });
        });
    }
);
