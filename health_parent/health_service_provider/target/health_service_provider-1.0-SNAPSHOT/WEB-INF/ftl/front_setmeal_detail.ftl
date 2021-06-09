<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0,user-scalable=no,minimal-ui">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../img/asset-favico.ico">
    <title>预约详情</title>
    <link rel="stylesheet" href="../../css/page-health-orderDetail.css" />
    <script src="../../plugins/vue/vue.js"></script>
    <script src="../../plugins/vue/axios-0.18.0.js"></script>
    <script src="../../plugins/healthmobile.js"></script>
</head>
<body data-spy="scroll" data-target="#myNavbar" data-offset="150">
<div id="app" class="app">
    <!-- 页面头部 -->
    <div class="top-header">
        <span class="f-left"><i class="icon-back" onclick="history.go(-1)"></i></span>
        <span class="center">传智健康</span>
        <span class="f-right"><i class="icon-more"></i></span>
    </div>
    <!-- 页面内容 -->
    <div class="contentBox">
        <div class="card">
            <div class="project-img">
                <#if (setmeal.img)??>
                <img src="http://qn2iy2j70.hn-bkt.clouddn.com/${setmeal.img}"
                     width="100%" height="100%" />
                </#if>
            </div>
            <div class="project-text">
                <#if (setmeal.name)??>
                <h4 class="tit">${setmeal.name}</h4>
                </#if>
                <#if (setmeal.remark)??>
                <p class="subtit">${setmeal.remark}</p>
                </#if>
                <p class="keywords">
                    <#if (setmeal.sex)??>
                    <span>
                        <#if setmeal.sex == '0'>
                            性别不限
                        <#else>
                            <#if setmeal.sex == '1'>
                                男
                            <#else>
                                女
                            </#if>
                        </#if>
                    </span>
                    </#if>
                    <#if (setmeal.age)??>
                    <span>${setmeal.age}</span>
                    </#if>
                </p>
            </div>
        </div>
        <div class="table-listbox">
            <div class="box-title">
                <i class="icon-zhen"><span class="path1"></span><span class="path2"></span></i>
                <span>套餐详情</span>
            </div>
            <div class="box-table">
                <div class="table-title">
                    <div class="tit-item flex2">项目名称</div>
                    <div class="tit-item  flex3">项目内容</div>
                    <div class="tit-item  flex3">项目解读</div>
                </div>
                <div class="table-content">
                    <ul class="table-list">
                        <#if (setmeal.checkGroups)??>
                        <#list setmeal.checkGroups as checkgroup>
                            <li class="table-item">
                                <#if (checkgroup.name)??>
                                <div class="item flex2">${checkgroup.name}</div>
                                </#if>
                                <div class="item flex3">
                                    <#if (checkgroup.checkItems)??>
                                    <#list checkgroup.checkItems as checkitem>
                                        <#if (checkitem.name)??>
                                        <label>
                                            ${checkitem.name}
                                        </label>
                                        </#if>
                                    </#list>
                                    </#if>
                                </div>
                                 <#if (checkgroup.remark)??>
                                <div class="item flex3">${checkgroup.remark}</div>
                                </#if>
                            </li>
                        </#list>
                        </#if>
                    </ul>
                </div>
                <div class="box-button">
                    <a @click="toOrderInfo()" class="order-btn">立即预约</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var vue = new Vue({
        el:'#app',
        methods:{
            toOrderInfo(){
                window.location.href = "../orderInfo.html?id=${setmeal.id}";
            }
        }
    });
</script>
</body>