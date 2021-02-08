<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="sessionScope.user==null">
    location.href=${pageContext.request.contextPath }/login.jsp
</c:if>
<%@include file="/jsp/common/head.jsp"%>
    <div class="right">
        <img class="wColck" src="${pageContext.request.contextPath }/images/clock.jpg" alt=""/>
        <div class="wFont">
            <h2>${user.username }</h2>
            <p>欢迎来到超市订单管理系统!</p>
        </div>
    </div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
