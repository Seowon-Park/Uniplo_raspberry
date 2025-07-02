package com.barcode.uniplo.controller;

import com.barcode.uniplo.domain.UserDto;
import com.barcode.uniplo.service.OrderService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/cart/order")
    public String orderFromCart(HttpSession session) throws Exception {
        UserDto user = (UserDto) session.getAttribute("authUser");
        if (user == null || user.getUser_id() == null) {
            // 로그인 안 된 경우 적절한 페이지로 리다이렉트하거나 예외 처리 필요
            return "redirect:/login/login";
        }
        String userId = user.getUser_id().toString();
        orderService.makeOrder(userId);
        return "/cart/complete";
    }
}
