<%-- 제품 목록 페이지 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/WEB-INF/views/includes/header.jsp" />
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>상품 목록 | Uniplon</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <script src="${pageContext.request.contextPath}/resources/js/common.js" defer></script>
</head>
<body>
<div class="container">
  <h2>상품 목록</h2>
  <div class="product-grid">
    <c:forEach var="item" items="${items}">
      <a href="${pageContext.request.contextPath}/item/detail?item_id=${item.item_id}">
        <div class="product-card">
          <img src="${pageContext.request.contextPath}/resources/images/${item.image_url}" alt="${item.item_name}" />
          <!--src="${pageContext.request.contextPath}/resources/images/${item.image_url} 이렇게 되어있는 거 위에 코드로 수정함 by서원-->
          <div class="product-name">${item.item_name}</div>
          <div class="product-price">₩${item.item_price}</div>
        </div>
      </a>
    </c:forEach>
  </div>
</div>
</body>
<c:import url="/WEB-INF/views/includes/footer.jsp" />
</html>
