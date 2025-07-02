<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:import url="/WEB-INF/views/includes/header.jsp"/>

<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>${item.item_name} | Uniplo</title>

  <!-- 공통 스타일 -->
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resources/css/style.css"/>

  <!-- 페이지 전용 스타일 -->
  <style>
    /* ─── 상품 상세 ─── */
    .product-detail img  {max-width:380px;width:100%;height:auto;margin-bottom:20px}
    .product-detail h2   {margin:10px 0 6px;font-size:24px}
    .product-detail p    {margin:4px 0;font-size:14px}

    /* ─── 장바구니 영역 ─── */
    .cart-area   {margin-top:24px;display:flex;align-items:center;gap:12px}
    .qty-btn     {width:32px;height:32px;font-size:18px;border:none;background:#f5f5f5;cursor:pointer}
    .qty-input   {width:48px;text-align:center;border:none;background:#f5f5f5;font-size:16px}
    .cart-btn    {background:#000;color:#fff;border:none;padding:10px 24px;font-size:14px;cursor:pointer}
    .cart-btn:hover{background:#333}

    /* ─── 알림 메시지 ─── */
    .notice      {margin-top:12px;color:#0a0;font-size:14px}
  </style>

  <!-- 수량 조절 -->
  <script>
    function plus(){
      const q=document.getElementById('cnt');
      q.value=parseInt(q.value)+1;
    }
    function minus(){
      const q=document.getElementById('cnt');
      if(parseInt(q.value)>1) q.value=parseInt(q.value)-1;
    }
  </script>
</head>

<body>
<div class="container">
  <div class="product-detail">

    <!-- 상품 이미지 -->
    <img src="${pageContext.request.contextPath}/resources/images/${item.image_url}"
    <!--src="${pageContext.request.contextPath}/resources/images/${item.image_url} 이렇게 되어있는 거 위에 코드로 수정함 by서원-->
         alt="${item.item_name}"/>

    <!-- 상품 정보 -->
    <h2>${item.item_name}</h2>
    <p>가격&nbsp;:&nbsp;₩${item.item_price}</p>
    <p>소재&nbsp;:&nbsp;${item.item_material}</p>
    <p>제조일&nbsp;:&nbsp;${item.item_manufacturing_year_month}</p>
    <p>카테고리&nbsp;:&nbsp;${item.item_cate_code}/${item.item_cate_detail_code}</p>
    <p>성별&nbsp;:&nbsp;${item.gender_code}</p>

    <!-- ─── 장바구니 폼 ─── -->
    <form action="${pageContext.request.contextPath}/cart/add" method="post">
      <!-- 컨트롤러용 숨은 값 -->
      <input type="hidden" name="itemPageId"      value="${item.item_id}" />

      <!-- CartDto 필드와 1:1 대응 -->
      <input type="hidden" name="item_id"         value="${item.item_id}"/>
      <input type="hidden" name="item_color_code" value=""/>
      <input type="hidden" name="item_size_code"  value=""/>

      <div class="cart-area">
        <button type="button" class="qty-btn" onclick="minus()">–</button>
        <input  id="cnt" name="cart_item_cnt" class="qty-input" type="text" value="1" readonly/>
        <button type="button" class="qty-btn" onclick="plus()">＋</button>

        <button type="submit" class="cart-btn">장바구니에 담기</button>
      </div>
    </form>

    <!-- ─── 성공 안내 메시지 (FlashAttribute) ─── -->
    <c:if test="${addedToCart}">
      <div class="notice">장바구니에 추가되었습니다.</div>
    </c:if>

  </div>
</div>
</body>

<c:import url="/WEB-INF/views/includes/footer.jsp"/>
</html>
