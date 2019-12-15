package com.xtwy.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.xtwy.netty.client.DefaultFuture;
import com.xtwy.netty.util.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if("ping".equals(msg.toString())) {
			ctx.channel().writeAndFlush("ping\r\n");
			return;
		}
		//ctx.channel().attr(AttributeKey.valueOf("ssss")).set(msg);
		Response response = JSONObject.parseObject(msg.toString(), Response.class);
		DefaultFuture.receive(response);
		
		//ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		
		
	}
	

}
