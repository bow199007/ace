var _colNames = ['', '编码', '产品编码', '问题描述', '原因分析', '改善措施', '责任人', '完成日期',		'任务状态', ''];var _colModel = function() {	return [			{				name : 'myac',				editable : false,				width : 80,				fixed : true,				sortable : false,				frozen : false,				resize : false,				formatter : 'actions',				formatoptions : {					keys : false				//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}				}			},			{				name : 'id',				editable : false,				hidden : true,				width : 100,				editoptions : {					size : "20",					maxlength : "50"				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"				},				editrules : {					required : false				}			},			{				name : 'productId',				editable : true,				width : 100,				editoptions : {					size : "20",					maxlength : "50"				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'></span>"				},				editrules : {					required : false				}			},			{				name : 'probDiscri',				editable : true,				width : 100,				editoptions : {					size : "20",					maxlength : "50",					readOnly : true				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"				},				editrules : {					required : true				}			},			{				name : 'probAnsys',				editable : true,				edittype : "textarea",				width : 100,				editoptions : {					size : "20",					maxlength : "200",					style : "width:90%;height:50px",					colspan : true				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"				},				editrules : {					required : true				}			},			{				name : 'actions',				editable : true,				edittype : "textarea",				width : 100,				editoptions : {					size : "20",					maxlength : "200",					style : "width:90%;height:50px",					colspan : true				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"				},				editrules : {					required : true				}			},			{				name : 'liable',				editable : true,				width : 100,				edittype : "combogrid",				sorttype : "int",				dataoptions : {					panelWidth : 400,					idField : 'USER_ID',					textField : 'NAME',					url : portalPath + '/system/selectUsers.do',					mode : 'remote',					fitColumns : true,					method : 'get',					columns : [[{						field : 'USER_ID',						title : '用户编号',						width : 100					}, {						field : 'NAME',						title : '姓名',						width : 100					}, {						field : 'DEPARTMENT_NAME',						title : '部门',						width : 100					}]]				},				editoptions : {					style : 'height:25px'				},				renderer : function(value, cur) {					return $.jgrid.getAccessor(cur, 'userName');				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"				},				editrules : {					required : true				}			},			{				name : 'lastModifyDate',				editable : true,				width : 100,				editoptions : {					size : "20",					maxlength : "50"				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'></span>"				},				editrules : {					required : false				},				edittype : "datebox",				editoptions : {					style : 'height:25px'				},				renderer : function(value) {					return value.substr(0, 10);				}			},			{				name : 'status',				editable : true,				width : 100,				editoptions : {					size : "20",					maxlength : "50"				},				formoptions : {					elmprefix : "",					elmsuffix : "<span style='color:red;font-size:16px;font-weight:800'>*</span>"				},				editrules : {					required : true				},				edittype : "select",				renderer : function(value) {					return rsd(value, "92");				},				editoptions : {					value : odparse("92")				}			}, {				name : 'userName',				editable : false,				hidden : true,				width : 100			}];}function aceSwitch(cellvalue, options, cell) {	console.log('aceSwitch');	setTimeout(function() {		$(cell).find('input[type=checkbox]').addClass(				'ace ace-switch ace-switch-5').after(				'<span class="lbl"></span>');	}, 0);}// enable datepickerfunction pickDate(cellvalue, options, cell) {	setTimeout(function() {		$(cell).find('input[type=text]').datepicker({			format : 'yyyy-mm-dd',			autoclose : true		});	}, 0);}function renderBtn(cur) {	var id = $.jgrid.getAccessor(cur, 'id');	var title = $.jgrid.getAccessor(cur, 'name');	var html = [];	html.push('<a target="_blank" href="');	html.push('javascript:preview(\'' + id + '\',\'' + title + '\')');	html.push('"');	html.push('><span class="badge badge-info">编辑</span></a>');	return html.join(' ');}