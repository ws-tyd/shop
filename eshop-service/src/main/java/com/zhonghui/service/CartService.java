package com.zhonghui.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.model.Cart;
import com.zhonghui.vo.ReqCart;
import com.zhonghui.vo.ReqCartOrder;
import com.zhonghui.vo.ResqCart;

public interface CartService extends IService<Cart> {
    FwResult addProductToCart(ResqCart resqCart);

    FwResult queryCart();

    FwResult deleteCartById(Integer id);

    FwResult updateCartById(Integer id, ReqCart reqCart);

    FwResult createOrder(ReqCartOrder reqCartOrder);

}
