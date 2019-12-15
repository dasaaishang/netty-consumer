package com.xtwy.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.xtwy.netty.handler.param.ServerRequest;
import com.xtwy.netty.util.Response;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter implements ChannelHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//ctx.channel().writeAndFlush("is OK\r\n");
		ServerRequest request = JSONObject.parseObject(msg.toString(), ServerRequest.class);
		Response response = new Response();
		response.setId(request.getId());
		response.setResult("is ok");
		String responseJson = JSONObject.toJSONString(response);
		ctx.channel().writeAndFlush(responseJson);
		ctx.channel().writeAndFlush("\r\n");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent event= (IdleStateEvent)evt;
			if(event.state().equals(IdleState.READER_IDLE)) {
				System.out.println("读空闲===");
				ctx.channel().close();
			}else if(event.state().equals(IdleState.WRITER_IDLE)) {
				System.out.println("写空闲===");
			}else if(event.state().equals(IdleState.ALL_IDLE)) {
				System.out.println("读写空闲===");
				ctx.channel().writeAndFlush("ping\r\n");
			}
		}
	}

}
