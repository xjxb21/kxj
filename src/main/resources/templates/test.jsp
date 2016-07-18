<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <title>主页面</title>
    <link rel="stylesheet" type="text/css" media="screen" href="themes/redmond/jquery-ui-1.8.2.custom.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="themes/ui.jqgrid.css"/>

    <script src="js/jquery.min.js" type="text/javascript"></script>
    <script src="js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
    <script src="js/jquery.layout.js" type="text/javascript"></script>
    <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
    <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="js/jquery.tablednd.js" type="text/javascript"></script>
    <script src="js/jquery.contextmenu.js" type="text/javascript"></script>
    <script src="js/ui.multiselect.js" type="text/javascript"></script>
    <script src="js/ajaxfileupload.js" type="text/javascript"></script>
    <script type="text/javascript">
        function showPicture(cellvalue, options, rowObject) {

            return "<img src='http://127.0.0.1:8080/jqgrid/" + rowObject.id + "/" + cellvalue + "' height='50' width='50' />";
        }

        function UploadImage(response, postdata) {
            var data = $.parseJSON(response.responseText);
            if (data.success == true) {
                if ($("#img_url").val() != "") {
                    ajaxFileUpload(data.id);
                }
            }

            return [data.success, data.message, data.id];

        }

        function ajaxFileUpload(id) {
            $("#loading")
                    .ajaxStart(function () {
                        $(this).show();
                    })
                    .ajaxComplete(function () {
                        $(this).hide();
                    });

            $.ajaxFileUpload
            (
                    {
                        url: 'upload',
                        secureuri: false,
                        fileElementId: 'img_url',
                        dataType: 'json',
                        data: {id: '[id]=' + id},
                        success: function (data, status) {

                            if (typeof (data.success) != 'undefined') {
                                if (data.success == true) {
                                    return;
                                } else {
                                    alert(data.message);
                                }
                            }
                            else {
                                return alert('Failed to upload logo!');
                            }
                        },
                        error: function (data, status, e) {
                            alert(data);
                            alert(status);
                            alert(e);
                            return alert('未知错误');
                        }
                    }
            )
        }
    </script>
</head>
<body>
<br/>
<br/>
<table id="dataGrid">
    <tbody>
    <tr>
        <td/>
    </tr>
    </tbody>
</table>
<div id="pager"></div>


</body>
</html>
<script type="text/javascript">
    var lastsel;
    var cols;
    jQuery(document).ready(function () {


        jQuery("#dataGrid").jqGrid({
            url: 'query',
            datatype: "json",//数据类型   服务端返回的值是json类型
            height: 250,//高度
            width: 1000,
            colNames: ['编号', '名称', '奖品数量', '创建时间', '中奖概率', '状态', '图片'],//列名
            colModel: [
                {
                    name: 'id',
                    index: 'id',
                    align: 'right',
                    width: 60,
                    sorttype: "int",
                    editable: false,
                    hidden: false,
                    editrules: {edithidden: false}
                },
                {name: 'name', index: 'name', width: 120, editable: true},
                {name: 'num', index: 'num', sorttype: "int", width: 130, editable: true},
                {name: 'add_time', index: 'add_time', width: 150, sorttype: "date", editable: true},
                {name: 'rate', index: 'rate', width: 150, editable: true},
                {name: 'status', index: 'status', width: 80, sorttype: "int", editable: true},
                {
                    name: 'img_url',
                    index: 'img_url',
                    width: 50,
                    search: false,
                    formatter: showPicture,
                    editable: true,
                    edittype: 'file',
                    editoptions: {enctype: "multipart/form-data"},
                    formoptions: {elmsuffix: '*'}
                }

            ],
            rowNum: 10,//默认显示行数
            rowList: [10, 20, 50],
            jsonReader: {
                root: 'rows',
                total: "total", // json中代表页码总数的数据
                records: "records", // json中代表数据行总数的数据
                repeatitems: false
            },
            pager: '#pager',//分页
            sortname: 'id',//排序字段
            sortorder: "desc",//排序方式
            caption: "jqgrid测试", //列表标题
            viewrecords: true,


            editurl: "query?cols=" + cols


        });
        jQuery("#dataGrid").jqGrid('navGrid', '#pager',
                {
                    edit: true,
                    add: true,
                    del: true,
                    search: false,
                    refresh: true,
                    view: true,
                    addtext: '添加',
                    edittext: ' 修改',
                    deltext: '删除',
                    viewtext: '放大'


                },

                {height: 280, reloadAfterSubmit: true, url: 'edit', afterSubmit: UploadImage, closeAfterEdit: true}, //编辑
                {
                    addCaption: "添 加 记 录",
                    height: 280, reloadAfterSubmit: true,
                    url: 'add', afterSubmit: UploadImage,
                    closeAfterAdd: true
                },    //添加
                {reloadAfterSubmit: true, url: 'del'}    //删除

        );


    });


</script>