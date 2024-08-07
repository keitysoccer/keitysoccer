package com.app.menta.logging;

import com.app.menta.logging.wrapper.RequestWrapper;
import com.app.menta.logging.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LoggingFilter
 */
@Component
@Slf4j
public class RestApiLoggingFilter extends OncePerRequestFilter implements Filter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)
            throws IOException, ServletException {

        // リクエストとレスポンスのラッパーを使用して変換
        RequestWrapper req = new RequestWrapper(request);
        ResponseWrapper res = new ResponseWrapper(response);
        long time = System.currentTimeMillis();
        // シーケンスに開始時間をセット
        MDC.put("sequence",String.valueOf(time));

        // リクエストのURLとログを出力
        log.info(req.getRequestURI());
        log.info(req.getRequestBody());
        // doFilterでControllerへ
        filterChain.doFilter(req, res);
        // レスポンスを出力
        log.info(res.getResponseBody());

    }
}
