  $(function () {
			
			/**
            $.post("GetData.do", null, function (data) {

                var total = data;
                PageClick("GetData.do",1, total, 3);
            });
            **/

            PageClick = function (formcode, url, pageIndex, total, spanInterval, pageSize) {
            	if (pageSize==null) pageSize=20;
                $.ajax({
                    url: url,
                    data: { "formcode":formcode, "PageIndex": pageIndex,"PageSize": pageSize },
                    type: "post",
                    //dataType: "json",
                    success: function (data) {
                   		if (data=='|__|') return;
                   		$('#'+formcode).clearGridData();
                   		var dd = data.split('|__|');
                   		var d0 = dd[0];
                   		var d1 = dd[1];
                   		var datas = d0.split('|~~|');
                   		var ids = d1.split('|~~|');
                    	for(var i=0; i<datas.length; i++){
                    		var datas_config = eval("({"+datas[i]+"})");
                    		$("#" + formcode).jqGrid('addRowData',ids[i], datas_config);
                    	}
                    	
                        //索引从1开始
                        //将当前页索引转为int类型
                        var intPageIndex = parseInt(pageIndex);

						
                        //创建分页
                        //将总记录数结果 得到 总页码数
                        var pageS = total;
                        if (pageS % pageSize == 0) pageS = pageS / 10;
                        else pageS = parseInt(total / pageSize) + 1;
                        var $pager = $("#pager");

                        //清楚分页div中的内容
                        $("#pager span").remove();
                        $("#pager a").remove();
						
                        //添加第一页
                        if (intPageIndex == 1)
                            $pager.append("<span class='disabled'>第一页</span>");
                        else {
                            var first = $("<a href='javascript:void(0)' first='" + 1 + "'>第一页</a>").click(function () {
                                PageClick(formcode,url,$(this).attr('first'), total, spanInterval, pageSize);
                                return false;
                            });
                            $pager.append(first);
                        }


                        //添加上一页
                        if (intPageIndex == 1)
                            $pager.append("<span class='disabled'>上一页</span>");
                        else {
                            var pre = $("<a href='javascript:void(0)' pre='" + (intPageIndex - 1) + "'>上一页</a>").click(function () {
                                PageClick(formcode,url,$(this).attr('pre'), total, spanInterval, pageSize);
                                return false;
                            });
                            $pager.append(pre);
                        }

                        //设置分页的格式  这里可以根据需求完成自己想要的结果
                        var interval = parseInt(spanInterval); //设置间隔
                        var start = Math.max(1, intPageIndex - interval); //设置起始页
                        var end = Math.min(intPageIndex + interval, pageS)//设置末页

                        if (intPageIndex < interval + 1) {
                            end = (2 * interval + 1) > pageS ? pageS : (2 * interval + 1);
                        }

                        if ((intPageIndex + interval) > pageS) {
                            start = (pageS - 2 * interval) < 1 ? 1 : (pageS - 2 * interval);

                        }


                        //生成页码
                        for (var j = start; j < end + 1; j++) {
                            if (j == intPageIndex) {
                                var spanSelectd = $("<span class='current'>" + j + "</span>");
                                $pager.append(spanSelectd);
                            } //if 
                            else {
                                var a = $("<a href='javascript:void(0)'>" + j + "</a>").click(function () {
                                    PageClick(formcode,url,$(this).text(), total, spanInterval, pageSize);
                                    return false;
                                });
                                $pager.append(a);
                            } //else
                        } //for

                        //上一页
                        if (intPageIndex >= pageS) {
                            $pager.append("<span class='disabled'>下一页</span>");

                        }
                        else {

                            var next = $("<a href='javascript:void(0)' next='" + (intPageIndex + 1) + "'>下一页</a>").click(function () {
                                PageClick(formcode,url,$(this).attr("next"), total, spanInterval, pageSize);
                                return false;
                            });
                            $pager.append(next);
                        }

                        //最后一页
                        if (intPageIndex == pageS) {
                            $pager.append("<span class='disabled'>最后一页</span>");

                        }
                        else {
                            var last = $("<a href='javascript:void(0)' last='" + pageS + "'>最后一页</a>").click(function () {
                                PageClick(formcode,url,$(this).attr("last"), total, spanInterval, pageSize);
                                return false;
                            });
                            $pager.append(last);
                        }
						$pager.append("<span>总共 "+total+" 条记录</span>");

                    } //sucess

                }); //ajax

            }; //function

        });   //ready