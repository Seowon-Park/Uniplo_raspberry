package com.barcode.uniplo.controller;

import com.barcode.uniplo.domain.UserDto;
import com.barcode.uniplo.service.CartService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.barcode.uniplo.domain.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    public static final String imageUrl = "";

    // 장바구니 페이지
    @GetMapping
    public String viewCart(HttpSession session, Model model) throws Exception {
        UserDto authUser = (UserDto) session.getAttribute("authUser");
        if (authUser == null || authUser.getUser_id() == null) {
            // 로그인 안 된 상태 → 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        String userId = authUser.getUser_id().toString();

        List<CartDto> cartList = cartService.getItems(userId);
        model.addAttribute("cartList", cartList);
        int totalPrice = 0;
        for (CartDto dto : cartList) {
            int itemPrice = dto.getCart_item_price();
            int cnt = dto.getCart_item_cnt();
            totalPrice += itemPrice * cnt;
        }
        model.addAttribute("TotalPrice", totalPrice);

        return "cart/cartList";
    }


    // 장바구니에 추가
    @PostMapping("/add")
    public String addCartItem(@ModelAttribute CartDto cartDto, HttpSession session, HttpServletRequest request) throws Exception {
        UserDto authUser = (UserDto) session.getAttribute("authUser");

        if (authUser == null || authUser.getUser_id() == null) {
            return "redirect:/login/login";
        }

        cartDto.setUser_id(authUser.getUser_id().toString());
        cartService.addToCart(cartDto);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // 장바구니 항목 삭제
    @PostMapping("/delete")
    public String deleteCartItem(@ModelAttribute CartDto cartDto, HttpSession session) throws Exception {
        UserDto authUser = (UserDto) session.getAttribute("authUser");

        cartDto.setUser_id(authUser.getUser_id().toString());

        cartService.deleteItem(cartDto);
        return "redirect:/cart";
    }

    // 장바구니 수량 수정
    @PostMapping("/update")
    public String updateCnt(@RequestParam("item_id") int item_id,
                            @RequestParam("item_color_code") String item_color_code,
                            @RequestParam("item_size_code") String item_size_code,
                            @RequestParam("num") int num,
                            HttpSession session) throws Exception {
        UserDto authUser = (UserDto) session.getAttribute("authUser");

        String userId = authUser.getUser_id().toString();

        CartDto cartDto = new CartDto();
        cartDto.setUser_id(userId);
        cartDto.setItem_id(Integer.toString(item_id));
        cartDto.setItem_color_code(item_color_code);
        cartDto.setItem_size_code(item_size_code);

        CartDto existingCart = cartService.getCartItem(cartDto);
        int currentCnt = existingCart.getCart_item_cnt();
        int newCnt = currentCnt + num;

        if (newCnt < 1) {
            newCnt = 1;
        }
        cartDto.setCart_item_cnt(newCnt);
        cartService.updateCnt(cartDto);

        return "redirect:/cart";
    }

}
