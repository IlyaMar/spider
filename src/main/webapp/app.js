require(["dojo/_base/declare", "dojo/on", "dojo/dom", "dojo/dom-construct", "dstore/Rest", "dojo/store/Memory", "dstore/Cache", "dojo/store/Observable", "dgrid/OnDemandGrid", "dgrid/Keyboard", "dgrid/Selection", "dojo/domReady!"],

function(declare, on, dom, domConstruct, Rest, Memory, Cache, Observable, OnDemandGrid, Keyboard, Selection) {
    masterStore = new Rest({
        target: "rest/accounts/"
    });
    //masterStore = new Observable(masterStore);	
    /*var cachedStore = Cache.create(masterStore, {
        cachingStore: new Memory()
    });*/
    
    on(dom.byId("add"), "click", function(){
		console.log('add click');
    	masterStore.add({
            login: dom.byId("newLogin").value,
            password: dom.byId("newPassword").value
        });
    });
    
    
    var CustomGrid = declare([ OnDemandGrid, Keyboard, Selection ]);
    
    grid = new CustomGrid({
        collection: masterStore,
        columns: [
            { label: 'Login', field: 'login', sortable: true },
            { label: 'Password', field: 'password' },
            { label: 'Last result', field: 'last_result', sortable: true },
            { label: 'Last date', field: 'last_date'},
            { label: 'Next date', field: 'next_date'}
        ],
        // for Selection; only select a single row at a time
        selectionMode: 'single',
        // for Keyboard; allow only row-level keyboard navigation
        cellNavigation: false
    }, 'grid');
    
    
	on(dom.byId("remove"), 'click', function () {
		for (var i in grid.selection) {
			console.log('removing ' + i);
			masterStore.remove(i);
		}
	});

    
}

);



 