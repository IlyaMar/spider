require(["dojo/on", "dojo/dom", "dojo/dom-construct", "dojo/store/JsonRest", "dojo/store/Memory", "dojo/store/Cache", "dojo/store/Observable"],

function(on, dom, domConstruct, JsonRest, Memory, Cache, Observable) {
    masterStore = new JsonRest({
        target: "rest/targets/"
    });
    masterStore = new Observable(masterStore);
    cacheStore = new Memory({});
    inventoryStore = new Cache(masterStore, cacheStore);
	
    results = masterStore.query();
    viewResults(results);
    
    
    on(dom.byId("add"), "click", function(){
		console.log('add click');
    	masterStore.add({
            login: "egor",
            password: '238_YINwe'
        });
    });
    
    function viewResults(results) {
        var container = dom.byId("container");
        var rows = [];

        results.forEach(insertRow);

        results.observe(function(item, removedIndex, insertedIndex){
    		console.log('observe ' + item);
            // this will be called any time a item is added, removed, and updated
            if(removedIndex > -1){
                removeRow(removedIndex);
            }
            if(insertedIndex > -1){
                insertRow(item, insertedIndex);
            }
        }, true); // we can indicate to be notified of object updates as well

        function insertRow(item, i){
    		console.log('insertRow ' + item);
            var row = domConstruct.create("div", {
                innerHTML: item.login + " password: " + item.password
            });
            rows.splice(i, 0, container.insertBefore(row, rows[i] || null));
        }

        function removeRow(i){
            domConstruct.destroy(rows.splice(i, 1)[0]);
        }
    }
    
}

);



 