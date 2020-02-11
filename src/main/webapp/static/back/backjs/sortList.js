
$(function () {

    var tableInitParams = {
        'tableId':'sortTable',
        'tableType':'sort',
        'detailView':true,
        'idField':'id',
        'columns':[
            {
                checkbox: true
            }, {
                field: 'id',
                title: 'id',
                sortable: true,
                width: 60,
                align:'center'
            }, {
                field: 'name',
                title: '名字'
            },
        ]
    }
    initTable(tableInitParams);
    initToolbar(tableInitParams);
})

