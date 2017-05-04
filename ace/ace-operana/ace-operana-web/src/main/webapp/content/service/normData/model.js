var _colNames =['编码','会议编码','议题编码','指标编码','产品编号','年度','上年度指标','wkt1','wkt2','wkt3','wkt4','wkt5','wkt6','wkt7','wkt8','wkt9','wkt10','wkt11','wkt12','wkt13','wkt14','wkt15','wkt16','wkt17','wkt18','wkt19','wkt20','wkt21','wkt22','wkt23','wkt24','wkt25','wkt26','wkt27','wkt28','wkt29','wkt30','wkt31','wkt32','wkt33','wkt34','wkt35','wkt36','wkt37','wkt38','wkt39','wkt40','wkt41','wkt42','wkt43','wkt44','wkt45','wkt46','wkt47','wkt48','wkt49','wkt50','wkt51','wkt52','wkt53','wkc1','wkc2','wkc3','wkc4','wkc5','wkc6','wkc7','wkc8','wkc9','wkc10','wkc11','wkc12','wkc13','wkc14','wkc15','wkc16','wkc17','wkc18','wkc19','wkc20','wkc21','wkc22','wkc23','wkc24','wkc25','wkc26','wkc27','wkc28','wkc29','wkc30','wkc31','wkc32','wkc33','wkc34','wkc35','wkc36','wkc37','wkc38','wkc39','wkc40','wkc41','wkc42','wkc43','wkc44','wkc45','wkc46','wkc47','wkc48','wkc49','wkc50','wkc51','wkc52','wkc53','创建人编码','创建人姓名','入库日期','最后更新人编码','最后更新人姓名','最后更新时间','操作'];
var _colModel = function() {
	return [
	{name : 'id',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'meetingId',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'topicId',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'normId',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'productId',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'year',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'lastYear',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"},formoptions : {elmprefix : "",elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"},editrules : {required : true}},{name : 'wkt1',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt2',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt3',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt4',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt5',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt6',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt7',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt8',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt9',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt10',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt11',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt12',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt13',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt14',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt15',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt16',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt17',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt18',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt19',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt20',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt21',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt22',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt23',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt24',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt25',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt26',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt27',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt28',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt29',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt30',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt31',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt32',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt33',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt34',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt35',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt36',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt37',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt38',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt39',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt40',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt41',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt42',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt43',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt44',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt45',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt46',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt47',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt48',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt49',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt50',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt51',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt52',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkt53',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc1',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc2',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc3',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc4',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc5',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc6',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc7',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc8',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc9',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc10',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc11',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc12',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc13',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc14',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc15',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc16',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc17',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc18',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc19',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc20',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc21',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc22',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc23',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc24',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc25',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc26',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc27',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc28',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc29',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc30',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc31',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc32',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc33',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc34',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc35',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc36',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc37',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc38',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc39',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc40',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc41',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc42',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc43',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc44',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc45',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc46',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc47',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc48',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc49',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc50',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc51',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc52',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'wkc53',editable : true,width : 100,editoptions : {size : "20",maxlength : "50"}},{name : 'createUserId',hidden : true,editable : false,width : 100},{name : 'createUserName',hidden : true,editable : false,width : 100},{name : 'createDate',hidden : true,editable : false,width : 100},{name : 'lastModifyUserId',hidden : true,editable : false,width : 100},{name : 'lastModifyUserName',hidden : true,editable : false,width : 100},{name : 'lastModifyDate',hidden : true,editable : false,width : 100},
	 {
                name: 'opt',
                width: 100,
                hidden:false,
                editable: false,
                sortable:false,
                renderer:function(value, cur){
                    return renderBtn(cur);
                }
            }
	];
}
function aceSwitch(cellvalue, options, cell) {
	console.log('aceSwitch');
	setTimeout(function() {
		$(cell).find('input[type=checkbox]').addClass(
				'ace ace-switch ace-switch-5').after(
				'<span class="lbl"></span>');
	}, 0);
}
// enable datepicker
function pickDate(cellvalue, options, cell) {
	setTimeout(function() {
		$(cell).find('input[type=text]').datepicker({
			format : 'yyyy-mm-dd',
			autoclose : true
		});
	}, 0);
}
function renderBtn(cur) {
	var id = $.jgrid.getAccessor(cur, 'id');
	var title = $.jgrid.getAccessor(cur, 'name');
	var html = [];
	html.push('<a target="_blank" href="');
	html.push('javascript:preview(\'' + id + '\',\'' + title + '\')');
	html.push('"');
	html.push('><span class="badge badge-info">查看</span></a>');
	return html.join(' ');
}