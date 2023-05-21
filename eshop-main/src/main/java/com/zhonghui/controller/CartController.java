package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.service.CartService;
import com.zhonghui.vo.ReqCart;
import com.zhonghui.vo.ReqCartOrder;
import com.zhonghui.vo.ResqCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult addProductToCart(@Valid @RequestBody ResqCart resqCart){
        return cartService.addProductToCart(resqCart);
    }

    @GetMapping("/cart")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult queryCart(){
        return cartService.queryCart();
    }

    @DeleteMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult deleteCartById(@PathVariable("id")Integer id){
        return cartService.deleteCartById(id);
    }
    @PutMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult updateCartById(@PathVariable("id")Integer id, @Valid @RequestBody ReqCart reqCart){
        return cartService.updateCartById(id,reqCart);
    }

    @PostMapping("/cart/orders")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult createOrder(@Valid @RequestBody ReqCartOrder reqCartOrder){
        return cartService.createOrder(reqCartOrder);
    }
}
