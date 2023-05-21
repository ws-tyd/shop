package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.mapper.*;
import com.zhonghui.model.*;
import com.zhonghui.service.CartService;
import com.zhonghui.utils.SecuritUserUtil;
import com.zhonghui.vo.CartPro;
import com.zhonghui.vo.ReqCart;
import com.zhonghui.vo.ReqCartOrder;
import com.zhonghui.vo.ResqCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {


    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserAddressMapper addressMapper;

    @Autowired
    private OrderStatusRecordMapper orderStatusRecordMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public FwResult addProductToCart(ResqCart resqCart) {
        synchronized (this){
            Product target = productMapper.selectById(resqCart.getProId());
            if (target==null){
                throw new BadRequestException(404,"没有这个商品，添加购物车失败");
            }else {
                User user = new SecuritUserUtil().getUser().getUser();
                Cart cart = new Cart(target, user);
                cart.setQuantity(resqCart.getProCount());
                cart.setProductTotalPrice(cart.getProductUnitPrice()*cart.getQuantity());
                cartMapper.insert(cart);
                QueryWrapper<Cart> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id",user.getId());
                List<Cart> carts = cartMapper.selectList(wrapper);
                return FwResult.ok(carts);
            }
        }
    }

    @Override
    public FwResult queryCart() {
        User user = new SecuritUserUtil().getUser().getUser();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        List<Cart> carts = cartMapper.selectList(wrapper);
        return FwResult.ok(carts);
    }

    @Override
    public FwResult deleteCartById(Integer id) {
        User user = new SecuritUserUtil().getUser().getUser();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.eq("id",id);
        Cart cart = cartMapper.selectOne(wrapper);
        if (cart==null){
            throw new BadRequestException(404,"购物车中不存在该商品");
        }else {
            cartMapper.delete(wrapper);
            return FwResult.ok();
        }
    }

    @Override
    public FwResult updateCartById(Integer id, ReqCart reqCart) {
        User user = new SecuritUserUtil().getUser().getUser();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.eq("id",id);
        Cart target = cartMapper.selectOne(wrapper);
        if (target==null){
            throw new BadRequestException(404,"购物车中不存在该商品");
        }else {
            target.setUpdateTime(LocalDateTime.now());
            target.setUpdateUser(user.getId());
            target.setQuantity(reqCart.getQuantity());
            target.setSelected(reqCart.getSelected());
            cartMapper.updateById(target);
            return FwResult.ok(cartMapper.selectById(id));
        }
    }

    @Override
    public FwResult createOrder(ReqCartOrder reqCartOrder) {
        synchronized (this){
            User user = new SecuritUserUtil().getUser().getUser();
            List<CartPro> cartPros = reqCartOrder.getCartPros();
            UserAddress userAddress = addressMapper.selectById(reqCartOrder.getAddressId());
            if (userAddress==null){
                throw new BadRequestException(404,"地址不存在");
            }
            long time = new Date().getTime();
            LocalDateTime now = LocalDateTime.now();
            String orderNo=time+"0";
            OrderInfo orderInfo = new OrderInfo(userAddress);
            for (CartPro cartPro : cartPros) {
                Product product = productMapper.selectById(cartPro.getProductId());
                if (product==null){
                    throw new BadRequestException(404,"商品不存在");
                }else if (product.getStock()<cartPro.getCount()){
                    throw new BadRequestException(404,product.getName()+"库存不足");
                }
                OrderStatusRecord orderStatusRecord = new OrderStatusRecord();
                orderStatusRecord.setCreateTime(now);
                orderStatusRecord.setOrderNo(orderNo);
                orderStatusRecord.setOrderDetailNo(orderNo);
                orderStatusRecord.setProductId(cartPro.getProductId());
                orderStatusRecord.setProductName(product.getName());
                orderStatusRecord.setProductCount(cartPro.getCount());
                orderStatusRecord.setProductPrice(product.getPrice());
                orderStatusRecordMapper.insert(orderStatusRecord);
                product.setStock(product.getStock()-cartPro.getCount());
                productMapper.updateById(product);
            }
            double cartTotal = getCartTotal(user.getId());
//            if (user.getBalance().doubleValue()<cartTotal){
//                throw new BadRequestException(400,"余额不足");
//            }
//            user.setBalance(user.getBalance().subtract(BigDecimal.valueOf(cartTotal)));
//            userMapper.updateById(user);
            orderInfo.setOrderNo(orderNo);
            orderInfo.setPayment(cartTotal);
            orderInfoMapper.insert(orderInfo);
            return FwResult.ok();
        }
    }

    private double getCartTotal(Integer id) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<Cart> carts = cartMapper.selectList(wrapper);
        double total=0;
        for (Cart cart : carts) {
            if (cart.getSelected()){
                total+=cart.getProductUnitPrice()*cart.getQuantity();
            }
        }
        return total;
    }

}
